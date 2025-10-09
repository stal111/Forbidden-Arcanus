package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesContainer;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.ValidRitualIndicator;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends BaseContainerBlockEntity implements EssencesContainer, BlockEntityAgeAccess {

    public static final int MAIN_SLOT = 4;

    public static final EnumMap<EssenceType, Integer> SLOT_FROM_ESSENCE_TYPE_MAP = Util.make(new EnumMap<>(EssenceType.class), map -> {
        map.put(EssenceType.AUREAL, 5);
        map.put(EssenceType.SOULS, 6);
        map.put(EssenceType.BLOOD, 7);
        map.put(EssenceType.EXPERIENCE, 8);
    });

    public static final int UPDATE_RITUAL_INDICATOR = 1;
    public static final int UPDATE_MAGIC_CIRCLE = 2;
    public static final int UPDATE_RITUAL_DURATION = 3;

    private final ContainerData hephaestusForgeData;
    private final EssenceManager essenceManager;
    private final RitualManager ritualManager;
    private final MagicCircleController magicCircleController = new MagicCircleController(UPDATE_MAGIC_CIRCLE);

    private ForgeDataCache dataCache;
    private HephaestusForgeLevel forgeLevel = HephaestusForgeLevel.ONE;

    private ValidRitualIndicator validRitualIndicator;
    private int displayCounter;
    private int clientRitualDuration;
    private ItemStack clientMainItem = ItemStack.EMPTY;

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state
//                , 9, (slot, stack) -> {
//            //TODO
////            if (HephaestusForgeMenu.ENHANCERS_SLOTS.contains(slot)) {
////                return EnhancerHelper.getEnhancer(level.registryAccess(), stack).isPresent();
////            }
//            return true;
//        }
        );
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                return switch (index) {
                    case 0 -> manager.getEssence(EssenceType.AUREAL);
                    case 1 -> manager.getEssence(EssenceType.SOULS);
                    case 2 -> manager.getEssence(EssenceType.BLOOD);
                    case 3 -> manager.getEssence(EssenceType.EXPERIENCE);
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                switch (index) {
                    case 0 -> manager.setEssence(EssenceType.AUREAL, value);
                    case 1 -> manager.setEssence(EssenceType.SOULS, value);
                    case 2 -> manager.setEssence(EssenceType.BLOOD, value);
                    case 3 -> manager.setEssence(EssenceType.EXPERIENCE, value);
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };


        if (state.getBlock() instanceof HephaestusForgeBlock forgeBlock) {
            this.forgeLevel = forgeBlock.getLevel();
        }
        this.dataCache = new ForgeDataCache(new ArrayList<>(), ItemStack.EMPTY, List.of());
        this.ritualManager = new RitualManager(this.magicCircleController, this.forgeLevel.getAsInt(), this.dataCache);
        this.essenceManager = new EssenceManager(this.forgeLevel.getMaxEssences(), essencesDefinition -> this.ritualManager.updateValidRitual(essencesDefinition, this.level.registryAccess()));
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);

        if (level instanceof ServerLevel serverLevel) {
            this.ritualManager.setup(serverLevel, this.getBlockPos());
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.magicCircleController.tick();

        if (blockEntity.hasValidRitualIndicator()) {
            blockEntity.validRitualIndicator.tick();
        }
        blockEntity.displayCounter++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        for (EssenceType type : EssenceType.values()) {
            int slot = SLOT_FROM_ESSENCE_TYPE_MAP.get(type);
            ItemStack stack = blockEntity.getItem(slot);

            if (stack.isEmpty()) {
                continue;
            }

            blockEntity.getInput(level, stack, type).ifPresent(input -> {
                blockEntity.fillWith(type, stack, input, slot);

                blockEntity.setChanged();
            });

        }

        if (level.getGameTime() % 80 == 0) {
            ((HephaestusForgeBlock) state.getBlock()).updateState(state, level, pos);
        }

        if (level.getGameTime() % 20 == 0) {
            blockEntity.essenceManager.tick(level, pos);
        }

        blockEntity.ritualManager.tick().ifPresent(stack -> {
            blockEntity.setItem(MAIN_SLOT, stack);
        });
    }

    @Override
    public boolean triggerEvent(int id, int value) {
        if (id == UPDATE_RITUAL_INDICATOR) {
            this.updateValidRitualIndicator(value == 1);

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

        this.onDataChanged(this.level.registryAccess());
    }

    private Optional<HephaestusForgeInput> getInput(Level level, ItemStack stack, EssenceType essenceType) {
        if (this.essenceManager.isEssenceFull(essenceType)) {
            return Optional.empty();
        }

        return level.registryAccess().lookupOrThrow(FARegistries.FORGE_INPUT).listElements()
                .map(Holder.Reference::value)
                .filter(input -> input.canInput(essenceType, stack))
                .findFirst();
    }

    public void setForgeLevel(HephaestusForgeLevel level) {
        this.forgeLevel = level;

        this.ritualManager.setForgeTier(level.getAsInt());
        this.essenceManager.setMaxEssences(level.getMaxEssences());
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    public EssenceManager getEssenceManager() {
        return this.essenceManager;
    }

    public MagicCircleController getMagicCircleController() {
        return this.magicCircleController;
    }

    public boolean hasValidRitualIndicator() {
        return this.validRitualIndicator != null;
    }

    public ValidRitualIndicator getValidRitualIndicator() {
        return this.validRitualIndicator;
    }

    private void updateValidRitualIndicator(boolean showIndicator) {
        this.validRitualIndicator = showIndicator ? new ValidRitualIndicator(true) : null;
    }

    public void fillWith(EssenceType essenceType, ItemStack stack, HephaestusForgeInput input, int slot) {
        int value = input.getInputValue(stack, Objects.requireNonNull(this.getLevel()).getRandom()).amount();

        this.getEssenceManager().increaseEssence(essenceType, value);

        this.setItem(slot, input.finishInput(stack, value));
    }

    public RitualManager getRitualManager() {
        return this.ritualManager;
    }

    public int getClientRitualDuration() {
        return this.clientRitualDuration;
    }

    public ItemStack getClientMainItem() {
        return this.clientMainItem;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        //TODO
//        this.saveInventory(tag, lookupProvider);

        this.getRitualManager().save(output);
//        this.getEssenceManager().save(output);

//        output.put("data_cache", ForgeDataCache.CODEC.encodeStart(lookupProvider.createSerializationContext(NbtOps.INSTANCE), this.dataCache).getOrThrow());
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.getRitualManager().load(input);
        //TODO
//        this.getEssenceManager().load(tag);
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

        tag.store("main_item", ItemStack.CODEC, lookupProvider.createSerializationContext(NbtOps.INSTANCE), this.getItem(MAIN_SLOT));

        return tag;
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);

        this.updateValidRitualIndicator(input.getBooleanOr("display_valid_ritual_indicator", false));

        input.read("main_item", ItemStack.CODEC).ifPresent(stack -> this.clientMainItem = stack);
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        this.handleUpdateTag(valueInput);
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.forbidden_arcanus.hephaestus_forge");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return null;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {

    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return null;
    }

    //TODO
//    @Override
//    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext<HephaestusForgeBlockEntity, IItemHandler> creationContext) {
//        return new HephaestusForgeMenu(containerId, this.getItemStackHandler(), this.getHephaestusForgeData(), creationContext, this.forgeLevel);
//    }

    @Override
    public void setEssencesLimit(EssencesDefinition definition) {
        this.essenceManager.setMaxEssences(definition);
    }

    @Override
    public EssencesDefinition getEssences() {
        return this.essenceManager.getCurrentEssences();
    }

    @Override
    public void setEssences(EssencesDefinition definition) {
        definition.forEach(this.essenceManager::setEssence);
    }

    private void onDataChanged(HolderLookup.Provider lookupProvider) {
        this.ritualManager.onDataChanged(this.dataCache, this.essenceManager.getCurrentEssences(), lookupProvider);
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public int getAgeInTicks() {
        return this.displayCounter;
    }
}
