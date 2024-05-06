package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesContainer;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.ValidRitualIndicator;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.api.common.block.entity.neoforge.ValhelsiaContainerBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends ValhelsiaContainerBlockEntity<HephaestusForgeBlockEntity> implements EssencesContainer {

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

    private HephaestusForgeLevel forgeLevel = HephaestusForgeLevel.ONE;

    private ValidRitualIndicator validRitualIndicator;
    private int displayCounter;
    private int clientRitualDuration;
    private ItemStack clientMainItem = ItemStack.EMPTY;

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state, 9, (slot, stack) -> {
            if (HephaestusForgeMenu.ENHANCERS_SLOTS.contains(slot)) {
                return EnhancerCache.get(stack.getItem()).isPresent();
            }
            return true;
        });
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                return switch (index) {
                    case 0 -> manager.getAureal();
                    case 1 -> manager.getSouls();
                    case 2 -> manager.getBlood();
                    case 3 -> manager.getExperience();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                EssenceManager manager = HephaestusForgeBlockEntity.this.getEssenceManager();

                switch (index) {
                    case 0 -> manager.setAureal(value);
                    case 1 -> manager.setSouls(value);
                    case 2 -> manager.setBlood(value);
                    case 3 -> manager.setExperience(value);
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

        this.ritualManager = new RitualManager(new MainSlotAccessor(this), () -> Stream.of(this.getStack(0), this.getStack(1), this.getStack(2), this.getStack(3))
                .map(stack -> EnhancerCache.get(stack.getItem()))
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .toList(), this.magicCircleController, this.forgeLevel.getAsInt());
        this.essenceManager = new EssenceManager(this.forgeLevel.getMaxEssences(), this.ritualManager::updateValidRitual);
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
            ItemStack stack = blockEntity.getStack(slot);

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

        blockEntity.ritualManager.tick();
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

    @Override
    protected void onSlotChanged(int slot) {
        if (this.level == null) {
            return;
        }

        if (slot == MAIN_SLOT) {
            if (this.getStack(slot).isEmpty() && this.getRitualManager().isRitualActive()) {
                this.getRitualManager().failRitual();
            } else {
                this.getRitualManager().updateValidRitual(this.essenceManager.getCurrentEssences());
            }

            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        } else if (HephaestusForgeMenu.ENHANCERS_SLOTS.contains(slot)) {
            this.getRitualManager().updateValidRitual(this.essenceManager.getCurrentEssences());
        }
    }

    private Optional<HephaestusForgeInput> getInput(Level level, ItemStack stack, EssenceType essenceType) {
        if (this.essenceManager.isEssenceFull(essenceType)) {
            return Optional.empty();
        }

        return level.registryAccess().registryOrThrow(FARegistries.FORGE_INPUT).holders()
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

        this.setStack(slot, input.finishInput(stack, value));
    }

    public RitualManager getRitualManager() {
        return this.ritualManager;
    }

    public int getDisplayCounter() {
        return this.displayCounter;
    }

    public int getClientRitualDuration() {
        return this.clientRitualDuration;
    }

    public ItemStack getClientMainItem() {
        return this.clientMainItem;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);

        this.saveInventory(tag, lookupProvider);

        this.getRitualManager().save(tag);
        this.getEssenceManager().save(tag);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);

        this.loadInventory(tag, lookupProvider);

        this.getRitualManager().load(tag);
        this.getEssenceManager().load(tag);
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

        if (!this.getStack(MAIN_SLOT).isEmpty()) {
            tag.put("main_item", this.getStack(MAIN_SLOT).save(lookupProvider));
        }

        return tag;
    }

    @Override
    public void handleUpdateTag(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);

        this.updateValidRitualIndicator(tag.getBoolean("display_valid_ritual_indicator"));
        this.clientMainItem = tag.contains("main_item", 10) ? ItemStack.parseOptional(lookupProvider, tag.getCompound("main_item")) : ItemStack.EMPTY;
    }

    @Override
    public void onDataPacket(@NotNull Connection net, @NotNull ClientboundBlockEntityDataPacket packet, HolderLookup.@NotNull Provider lookupProvider) {
        this.handleUpdateTag(packet.getTag(), lookupProvider);
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.forbidden_arcanus.hephaestus_forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext<HephaestusForgeBlockEntity, IItemHandler> creationContext) {
        return new HephaestusForgeMenu(containerId, this.getItemStackHandler(), this.getHephaestusForgeData(), creationContext, this.forgeLevel);
    }

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

    private record MainSlotAccessor(HephaestusForgeBlockEntity blockEntity) implements RitualManager.MainIngredientAccessor {

        @Override
        public ItemStack get() {
            return this.blockEntity.getStack(MAIN_SLOT);
        }

        @Override
        public void set(ItemStack stack) {
            this.blockEntity.setStack(MAIN_SLOT, stack);
        }
    }
}
