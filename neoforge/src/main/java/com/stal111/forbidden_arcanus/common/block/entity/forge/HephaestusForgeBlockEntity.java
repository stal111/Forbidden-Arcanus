package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.common.block.entity.TickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.ValidRitualIndicatorController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.HephaestusForgeState;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.CollectBloodTickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.ProgressRitualTickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.UpdateBlockStateTickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.EnhancerResourceHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.EssenceInputResourceHandler;
import com.stal111.forbidden_arcanus.common.block.entity.transfer.SingleItemResourceHandler;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.MultiEssenceStorage;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends BlockEntity implements EssenceAccess, ItemOwner, BlockEntityAgeAccess, MenuProvider {

    public static final EnumMap<EssenceType, Integer> SLOT_FROM_ESSENCE_TYPE_MAP = Util.make(new EnumMap<>(EssenceType.class), map -> {
        map.put(EssenceType.AUREAL, 0);
        map.put(EssenceType.SOULS, 1);
        map.put(EssenceType.BLOOD, 2);
        map.put(EssenceType.EXPERIENCE, 3);
    });

    public static final int UPDATE_RITUAL_INDICATOR = 1;
    public static final int UPDATE_MAGIC_CIRCLE = 2;
    public static final int UPDATE_RITUAL_DURATION = 3;

    private final ContainerData hephaestusForgeData;
    private final RitualManager ritualManager;
    private final ValidRitualIndicatorController indicatorController = new ValidRitualIndicatorController(UPDATE_RITUAL_INDICATOR);
    private final MagicCircleController magicCircleController = new MagicCircleController(UPDATE_MAGIC_CIRCLE);

    private MultiEssenceStorage essenceStorage;

    private final List<TickEffect> tickEffects = new ArrayList<>();

    private ForgeDataCache dataCache;
    private HephaestusForgeLevel forgeLevel;

    private int displayCounter;
    public int clientRitualDuration;
    private ItemStack clientMainItem = ItemStack.EMPTY;

    private final SingleItemResourceHandler mainSlotInventory = new SingleItemResourceHandler(stack -> {
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }

        this.setChanged();
    });
    private final EnhancerResourceHandler enhancerInventory = new EnhancerResourceHandler(4);
    private final EssenceInputResourceHandler essenceInputInventory = new EssenceInputResourceHandler(List.of(EssenceType.values()));

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state);
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.AUREAL);
                    case 1 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.SOULS);
                    case 2 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.BLOOD);
                    case 3 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.EXPERIENCE);
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.AUREAL, value);
                    case 1 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.SOULS, value);
                    case 2 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.BLOOD, value);
                    case 3 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.EXPERIENCE, value);
                    default -> {
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        this.forgeLevel = state.getValueOrElse(ModBlockStateProperties.FORGE_TIER, HephaestusForgeLevel.ONE);
        this.dataCache = new ForgeDataCache(new ArrayList<>());
        this.ritualManager = new RitualManager(this.indicatorController, this.magicCircleController, this.mainSlotInventory, this.dataCache);

        this.essenceStorage = MultiEssenceStorage.empty(this.forgeLevel.getMaxEssences());

        this.tickEffects.add(CollectBloodTickEffect.create(this));
        this.tickEffects.add(new UpdateBlockStateTickEffect());
        this.tickEffects.add(new ProgressRitualTickEffect(this.ritualManager, this.dataCache));
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);

        if (level instanceof ServerLevel serverLevel) {
            this.ritualManager.setup(serverLevel, this.getBlockPos());
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.indicatorController.tick();
        blockEntity.magicCircleController.tick();

        blockEntity.displayCounter++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        for (EssenceType type : EssenceType.values()) {
            int slot = SLOT_FROM_ESSENCE_TYPE_MAP.get(type);
            ItemStack stack = ItemUtil.getStack(blockEntity.essenceInputInventory, slot);

            if (stack.isEmpty()) {
                continue;
            }

            blockEntity.getInput(level, stack, type).ifPresent(input -> {
                blockEntity.fillWith(type, stack, input, slot);

                blockEntity.setChanged();
            });
        }

        if (level instanceof ServerLevel serverLevel) {
            for (TickEffect effect : blockEntity.tickEffects) {
                if (serverLevel.getGameTime() % effect.getTickInterval() == 0) {
                    effect.tick(serverLevel, pos, state);
                }
            }
        }
    }

    @Override
    public boolean triggerEvent(int id, int value) {
        if (id == UPDATE_RITUAL_INDICATOR) {
            this.indicatorController.updateIndicator(value == 1);

            return true;
        } else if (id == UPDATE_MAGIC_CIRCLE) {
            this.magicCircleController.handleEvent(this.level, this.getBlockPos(), value);

            return true;
        } else if (id == UPDATE_RITUAL_DURATION) {
            this.clientRitualDuration = value;

            return true;
        }

        return super.triggerEvent(id, value);
    }

    //TODO
//    @Override
//    protected void onSlotChanged(int slot) {
//        if (this.level == null) {
//            return;
//        }
//
//        if (slot == MAIN_SLOT) {
//            this.dataCache = this.dataCache.setMainIngredient(this.getStack(MAIN_SLOT));
//
//            this.onDataChanged(this.level.registryAccess());
//
//            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
//        } else if (HephaestusForgeMenu.ENHANCERS_SLOTS.contains(slot)) {
//            List<Holder<EnhancerDefinition>> enhancers = HephaestusForgeMenu.ENHANCERS_SLOTS
//                    .intStream()
//                    .mapToObj(s -> EnhancerHelper.getEnhancerHolder(this.level.registryAccess(), this.getStack(s)).orElse(null))
//                    .filter(Objects::nonNull)
//                    .toList();
//
//            this.dataCache = this.dataCache.setEnhancers(enhancers);
//
//            this.onDataChanged(this.level.registryAccess());
//        }
//    }

    public void updatePedestalStack(BlockPos pos, ItemStack stack) {
        this.dataCache.setIngredient(pos, stack);

        this.onDataChanged();
    }

    private Optional<HephaestusForgeInput> getInput(Level level, ItemStack stack, EssenceType essenceType) {
        if (this.isEssenceFull(essenceType)) {
            return Optional.empty();
        }

        return level.registryAccess().lookupOrThrow(FARegistries.FORGE_INPUT).listElements()
                .map(Holder.Reference::value)
                .filter(input -> input.canInput(essenceType, stack))
                .findFirst();
    }

    public void setForgeLevel(HephaestusForgeLevel level) {
        this.forgeLevel = level;

        for (EssenceType type : EssenceType.values()) {
            this.setEssenceLimit(type, level.getMaxAmount(type));
        }
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    public ValidRitualIndicatorController getIndicatorController() {
        return this.indicatorController;
    }

    public MagicCircleController getMagicCircleController() {
        return this.magicCircleController;
    }

    public void fillWith(EssenceType essenceType, ItemStack stack, HephaestusForgeInput input, int slot) {
        int value = input.getInputValue(stack, Objects.requireNonNull(this.getLevel()).getRandom()).amount();

        this.addEssence(essenceType, value);

        ItemStack result = input.finishInput(stack, value);
        this.essenceInputInventory.set(slot, ItemResource.of(result), result.getCount());
    }

    public RitualManager getRitualManager() {
        return this.ritualManager;
    }

    public ItemStack getClientMainItem() {
        return this.clientMainItem;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        this.mainSlotInventory.serialize("main_item", output);
        this.enhancerInventory.serialize(output.child("enhancers"));
        this.essenceInputInventory.serialize(output.child("inputs"));

        this.getRitualManager().save(output);
        output.store("essences", MultiEssenceStorage.CODEC, this.essenceStorage);

//        output.put("data_cache", ForgeDataCache.CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this.dataCache).getOrThrow());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.mainSlotInventory.deserialize("main_item", input);
        this.enhancerInventory.deserialize(input.childOrEmpty("enhancers"));
        this.essenceInputInventory.deserialize(input.childOrEmpty("inputs"));

        this.getRitualManager().load(input);
        this.essenceStorage = input.read("essences", MultiEssenceStorage.CODEC).orElse(MultiEssenceStorage.empty(this.forgeLevel.getMaxEssences()));

        //TODO
//
//        if (tag.contains("data_cache")) {
//            ForgeDataCache.CODEC.parse(lookupProvider.createSerializationContext(NbtOps.INSTANCE), tag.get("data_cache")).result().ifPresent(forgeDataCache -> this.dataCache = forgeDataCache);
//
//            this.onDataChanged(lookupProvider);
//        }
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        CompoundTag tag = this.saveWithoutMetadata(lookupProvider);
        tag.putBoolean("display_valid_ritual_indicator", this.ritualManager.getValidRitual().isPresent());

        if (!this.mainSlotInventory.getStack().isEmpty()) {
            tag.store("main_item", ItemStack.CODEC, lookupProvider.createSerializationContext(NbtOps.INSTANCE), this.mainSlotInventory.getStack());
        }

        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);

        this.indicatorController.updateIndicator(input.getBooleanOr("display_valid_ritual_indicator", false));

        this.clientMainItem = input.read("main_item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        this.handleUpdateTag(valueInput);
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return Component.translatable("container.forbidden_arcanus.hephaestus_forge");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new HephaestusForgeMenu(containerId, this.mainSlotInventory, this.enhancerInventory, this.essenceInputInventory, this.getHephaestusForgeData(), ContainerLevelAccess.create(this.level, this.getBlockPos()), playerInventory, this.forgeLevel);
    }

    private void onDataChanged() {
        this.ritualManager.onDataChanged(this.dataCache, this.getCurrenState());
    }

    public HephaestusForgeState getCurrenState() {
        return new HephaestusForgeState(
                this.forgeLevel,
                this.mainSlotInventory.getStack(),
                this.dataCache.getIngredients(),
                this.enhancerInventory.getEnhancers(),
                this.essenceStorage.getSnapshot()
        );
    }

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return this.essenceStorage.getStorage(type);
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        this.essenceStorage = this.essenceStorage.updateEssence(type, updater);

        this.onDataChanged();
    }

    @Override
    public Level level() {
        return this.level;
    }

    @Override
    public Vec3 position() {
        return this.getBlockPos().getCenter();
    }

    @Override
    public float getVisualRotationYInDegrees() {
        return 180.0F - ItemEntity.getSpin(this.getAgeInTicks() + 0.5F, 0.0F) / (float) (Math.PI * 2) * 360.0F;
    }

    @Override
    public int getAgeInTicks() {
        return this.displayCounter;
    }
}
