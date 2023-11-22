package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesContainer;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.RitualManager;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.ValidRitualIndicator;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateItemInSlotPacket;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import com.stal111.forbidden_arcanus.util.ValueNotifier;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.valhelsia.valhelsia_core.api.common.block.entity.MenuCreationContext;
import net.valhelsia.valhelsia_core.api.common.block.entity.forge.ValhelsiaContainerBlockEntity;
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private final ContainerData hephaestusForgeData;
    private final EssenceManager essenceManager;
    private final RitualManager ritualManager;

    private final ValueNotifier<HephaestusForgeLevel> forgeLevel;

    private MagicCircle magicCircle;
    private ValidRitualIndicator validRitualIndicator;
    private int displayCounter;

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

        HephaestusForgeLevel level = HephaestusForgeLevel.getFromIndex(state.getValue(HephaestusForgeBlock.TIER));

        this.ritualManager = new RitualManager(new MainSlotAccessor(this), () -> Stream.of(this.getStack(0), this.getStack(1), this.getStack(2), this.getStack(3))
                .map(stack -> EnhancerCache.get(stack.getItem()))
                .filter(Optional::isPresent)
                .map(Optional::orElseThrow)
                .toList(), level.getAsInt());
        this.essenceManager = new EssenceManager(level.getMaxEssences(), this.ritualManager::updateValidRitual);

        this.forgeLevel = ValueNotifier.of(level, forgeTier -> {
            this.ritualManager.setForgeTier(forgeTier.getAsInt());
            this.essenceManager.setMaxEssences(forgeTier.getMaxEssences());
        });
    }

    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);

        if (level instanceof ServerLevel serverLevel) {
            this.ritualManager.setup(serverLevel, this.getBlockPos());
        }
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        if (blockEntity.hasMagicCircle()) {
            blockEntity.magicCircle.tick();
        }
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

        blockEntity.ritualManager.tick(blockEntity.essenceManager.getCurrentEssences());
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

            NetworkHandler.sendToTrackingChunk(this.level.getChunkAt(this.worldPosition), new UpdateItemInSlotPacket(this.worldPosition, this.getStack(slot), slot));
        } else if (HephaestusForgeMenu.ENHANCERS_SLOTS.contains(slot)) {
            this.getRitualManager().updateValidRitual(this.essenceManager.getCurrentEssences());
        }
    }

    private Optional<HephaestusForgeInput> getInput(Level level, ItemStack stack, EssenceType essenceType) {
        if (this.essenceManager.isEssenceFull(essenceType)) {
            return Optional.empty();
        }

        return level.registryAccess().registryOrThrow(FARegistries.FORGE_INPUT).holders()
                .map(Holder::get)
                .filter(input -> input.canInput(essenceType, stack))
                .findFirst();
    }

    public void setForgeLevel(HephaestusForgeLevel level) {
        this.forgeLevel.set(level);
    }

    public ContainerData getHephaestusForgeData() {
        return this.hephaestusForgeData;
    }

    public EssenceManager getEssenceManager() {
        return this.essenceManager;
    }

    public boolean hasMagicCircle() {
        return this.magicCircle != null;
    }

    public MagicCircle getMagicCircle() {
        return this.magicCircle;
    }

    public void setMagicCircle(@NotNull MagicCircle magicCircle) {
        this.magicCircle = magicCircle;
    }

    public void removeMagicCircle() {
        this.magicCircle = null;
        this.validRitualIndicator = null;
    }

    public boolean hasValidRitualIndicator() {
        return this.validRitualIndicator != null;
    }

    public ValidRitualIndicator getValidRitualIndicator() {
        return this.validRitualIndicator;
    }

    public void createValidRitualIndicator(boolean playAnimation) {
        this.validRitualIndicator = new ValidRitualIndicator(playAnimation);
    }

    public void removeValidRitualIndicator() {
        this.validRitualIndicator = null;
    }

    public void fillWith(EssenceType essenceType, ItemStack stack, HephaestusForgeInput input, int slot) {
        int value = input.getInputValue(essenceType, stack, Objects.requireNonNull(this.getLevel()).getRandom());

        this.getEssenceManager().increaseEssence(essenceType, value);

        input.finishInput(essenceType, stack, this, slot, value);
    }

    public RitualManager getRitualManager() {
        return this.ritualManager;
    }

    public int getDisplayCounter() {
        return this.displayCounter;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        this.saveInventory(tag);

        tag.put("Ritual", this.getRitualManager().save(new CompoundTag()));
        tag.put("Essences", this.getEssenceManager().save(new CompoundTag()));
    }

    @Override
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);

        this.loadInventory(tag);

        this.getRitualManager().load(tag.getCompound("Ritual"));
        this.getEssenceManager().load(tag.getCompound("Essences"));
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @NotNull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Override
    public AABB getRenderBoundingBox() {
        AABB boundingBox = new AABB(this.getBlockPos()).expandTowards(0.0D, 1.0D, 0.0D);

        if (this.useExpandedRenderBoundingBox()) {
            boundingBox = boundingBox.inflate(2.5F, 0.0F, 2.5D);
        }
        return boundingBox;
    }

    public boolean useExpandedRenderBoundingBox() {
        return this.getRitualManager().isRitualActive() || this.hasValidRitualIndicator();
    }

    @NotNull
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.forbidden_arcanus.hephaestus_forge");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, @NotNull MenuCreationContext creationContext) {
        return new HephaestusForgeMenu(containerId, this.getItemStackHandler(), this.getHephaestusForgeData(), creationContext);
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

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return this.lazyItemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        this.lazyItemHandler = LazyOptional.of(this::getItemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();

        this.lazyItemHandler.invalidate();
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
