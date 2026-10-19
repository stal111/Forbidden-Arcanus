package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.BlockEntityAgeAccess;
import com.stal111.forbidden_arcanus.common.block.entity.TickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.CollectBloodTickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.ProgressRitualTickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.tick.UpdateBlockStateTickEffect;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.Containers;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * Hephaestus Forge Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity
 *
 * @author stal111
 * @since 2021-06-18
 */
public class HephaestusForgeBlockEntity extends BlockEntity implements EssenceAccess, ItemOwner, BlockEntityAgeAccess, MenuProvider {

    private static final Component NAME = Component.translatable("container.forbidden_arcanus.hephaestus_forge");

    private final HephaestusForgeServerData serverData = new HephaestusForgeServerData();
    private final HephaestusForgeSharedData sharedData;
    private final HephaestusForgeClientData clientData = new HephaestusForgeClientData();

    private final ContainerData hephaestusForgeData;

    private final List<TickEffect> tickEffects = new ArrayList<>();

    public HephaestusForgeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.HEPHAESTUS_FORGE.get(), pos, state);
        this.hephaestusForgeData = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.AUREAL);
                    case 1 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.ECTOPLASM);
                    case 2 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.BLOOD);
                    case 3 -> HephaestusForgeBlockEntity.this.getEssenceAmount(EssenceType.EXPERIENCE);
                    case 4 -> HephaestusForgeBlockEntity.this.sharedData.getForgeLevel().ordinal();
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.AUREAL, value);
                    case 1 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.ECTOPLASM, value);
                    case 2 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.BLOOD, value);
                    case 3 -> HephaestusForgeBlockEntity.this.setEssenceAmount(EssenceType.EXPERIENCE, value);
                    case 4 -> HephaestusForgeBlockEntity.this.setForgeLevel(HephaestusForgeLevel.values()[value]);
                    default -> {
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }
                };
            }

            @Override
            public int getCount() {
                return 5;
            }
        };

        this.sharedData = new HephaestusForgeSharedData(state.getValueOrElse(ModBlockStateProperties.FORGE_TIER, HephaestusForgeLevel.ONE));

        this.tickEffects.add(CollectBloodTickEffect.create(this.sharedData));
        this.tickEffects.add(new UpdateBlockStateTickEffect());
        this.tickEffects.add(new ProgressRitualTickEffect(this.serverData, this.sharedData));
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);

        Containers.dropItemStack(this.level, pos.getX(), pos.getY(), pos.getZ(), this.sharedData.getMainSlotInventory().getStack());
        Containers.dropContents(this.level, pos, this.serverData.getEnhancerInventory().copyToList());
        Containers.dropContents(this.level, pos, this.serverData.getEssenceInputInventory().copyToList());
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.clientData.tick(blockEntity.hasValidOrActiveRitual());

        blockEntity.sharedData.getActiveRitualData().ifPresent(activeRitualData -> {
            activeRitualData.incrementTicks();

            RandomSource random = level.getRandom();

            if (random.nextDouble() < 0.25D) {
                double angle = random.nextDouble() * Math.PI * 2.0D;
                double radius = 1.2D + random.nextDouble() * 1.1D;
                double x = pos.getX() + 0.5D + Math.cos(angle) * radius;
                double z = pos.getZ() + 0.5D + Math.sin(angle) * radius;

                level.addParticle(ModParticles.AUREAL_MOTE.get(), x, pos.getY() + 0.15D, z, 0.0D, 0.02D + random.nextDouble() * 0.03D, 0.0D);
            }
        });
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, HephaestusForgeBlockEntity blockEntity) {
        blockEntity.serverData.tick(blockEntity.sharedData);

        if (level instanceof ServerLevel serverLevel) {
            for (TickEffect effect : blockEntity.tickEffects) {
                if (serverLevel.getGameTime() % effect.getTickInterval() == 0) {
                    effect.tick(serverLevel, pos, state);
                }
            }

            if (blockEntity.sharedData.isDirty || blockEntity.serverData.isDirty) {
                blockEntity.serverData.onDataChanged(serverLevel, pos, blockEntity.sharedData);
                blockEntity.setChanged();

                blockEntity.serverData.isDirty = false;
            }

            if (blockEntity.sharedData.isDirty) {
                serverLevel.sendBlockUpdated(pos, state, state, 2);

                blockEntity.sharedData.isDirty = false;
            }
        }
    }

    public HephaestusForgeClientData getClientData() {
        return this.clientData;
    }

    public HephaestusForgeSharedData getSharedData() {
        return this.sharedData;
    }

    public void updatePedestalStack(BlockPos pos, ItemStack stack) {
        this.serverData.updatePedestalStack(pos, stack);
    }

    public void setForgeLevel(HephaestusForgeLevel level) {
        this.sharedData.setForgeLevel(level);
    }

    public boolean canStartRitual() {
        return this.sharedData.getValidRitual().isPresent();
    }

    public boolean hasActiveRitual() {
        return this.sharedData.getActiveRitualData().isPresent();
    }

    public boolean hasValidOrActiveRitual() {
        return this.canStartRitual() || this.hasActiveRitual();
    }

    public boolean startRitual(ServerLevel level, ServerPlayer player) {
        return this.serverData.startRitual(level, this.getBlockPos(), player, this.sharedData);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        this.serverData.save(output);
        output.store(HephaestusForgeSharedData.CODEC, this.sharedData);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

        this.serverData.load(input);
        input.read(HephaestusForgeSharedData.CODEC).ifPresent(this.sharedData::set);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        return Util.make(new CompoundTag(), tag -> tag.store(HephaestusForgeSharedData.CODEC, lookupProvider.createSerializationContext(NbtOps.INSTANCE), this.sharedData));
    }

    @NotNull
    @Override
    public Component getDisplayName() {
        return NAME;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new HephaestusForgeMenu(containerId, this.sharedData.getMainSlotInventory(), this.serverData.getEnhancerInventory(), this.serverData.getEssenceInputInventory(), this.hephaestusForgeData, ContainerLevelAccess.create(this.level, this.getBlockPos()), playerInventory);
    }

    @Override
    public EssenceStorage getEssence(EssenceType type) {
        return this.sharedData.getEssence(type);
    }

    @Override
    public void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        this.sharedData.updateEssence(type, updater);
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
        return this.clientData.getAgeInTicks();
    }
}
