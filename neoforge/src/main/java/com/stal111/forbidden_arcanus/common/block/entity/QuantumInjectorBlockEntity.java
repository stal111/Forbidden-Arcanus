package com.stal111.forbidden_arcanus.common.block.entity;

import com.mojang.logging.LogUtils;
import com.stal111.forbidden_arcanus.client.particle.EssenceDropParticleOption;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssencePath;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorBlockEntity extends BlockEntity implements BlockEntityAgeAccess {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static final Map<EssenceType, Holder<PoiType>> ESSENCE_TYPE_TO_POI_TYPE = Map.of(
            EssenceType.AUREAL, ModPOITypes.AUREAL_UTREM_JAR,
            EssenceType.ECTOPLASM, ModPOITypes.ECTOPLASM_UTREM_JAR,
            EssenceType.BLOOD, ModPOITypes.BLOOD_UTREM_JAR,
            EssenceType.EXPERIENCE, ModPOITypes.EXPERIENCE_UTREM_JAR
    );

    private static final int TICKS_BETWEEN_ACTIONS = 5;
    private static final int TRANSFORM_ANIMATION_DURATION = 60;

    public final AnimationState transformAnimation = new AnimationState();
    public final AnimationState rotateAnimation = new AnimationState();

    private @Nullable EssencePath essencePath;

    private @Nullable HephaestusForgeBlockEntity forgeBlockEntity;
    private @Nullable EssenceStorageBlockEntity jarBlockEntity;

    private boolean playAnimation = false;
    private int ageInTicks;

    public QuantumInjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_INJECTOR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        blockEntity.transformAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && blockEntity.playAnimation, blockEntity.ageInTicks);
        blockEntity.rotateAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && !blockEntity.transformAnimation.isStarted(), blockEntity.ageInTicks);

        if (blockEntity.playAnimation && blockEntity.ageInTicks >= TRANSFORM_ANIMATION_DURATION) {
            blockEntity.playAnimation = false;
        }

        blockEntity.ageInTicks++;

        EssencePath essencePath = blockEntity.essencePath;
        if (essencePath != null && blockEntity.ageInTicks % 10 == 0) {
            Vector3fc start = essencePath.path().getFirst();

            level.addParticle(new EssenceDropParticleOption(essencePath), start.x(), start.y(), start.z(), 0, 0, 0);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        if (level.getGameTime() % TICKS_BETWEEN_ACTIONS != 0 || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        if (blockEntity.forgeBlockEntity == null || blockEntity.forgeBlockEntity.isRemoved()) {
            blockEntity.forgeBlockEntity = null;

            serverLevel.getPoiManager()
                    .findClosest(holder -> holder.value() == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY)
                    .flatMap(forgePos -> serverLevel.getBlockEntity(forgePos, ModBlockEntities.HEPHAESTUS_FORGE.get()))
                    .ifPresent(forgeBlockEntity -> blockEntity.forgeBlockEntity = forgeBlockEntity);
        }

        blockEntity.jarBlockEntity = null;

        if (blockEntity.forgeBlockEntity != null) {
            for (EssenceType type : EssenceType.values()) {
                if (blockEntity.jarBlockEntity != null) {
                    break;
                }

                if (!blockEntity.forgeBlockEntity.isEssenceFull(type)) {
                    serverLevel.getPoiManager()
                            .findClosest(holder -> holder.value() == ESSENCE_TYPE_TO_POI_TYPE.get(type).value(), pos, 8, PoiManager.Occupancy.ANY)
                            .flatMap(jarPos -> level.getBlockEntity(jarPos, ModBlockEntities.ESSENCE_UTREM_JAR.get()))
                            .ifPresent(jarBlockEntity -> blockEntity.jarBlockEntity = jarBlockEntity);
                }
            }
        }

        blockEntity.transferEssence(serverLevel, pos);
    }

    private void transferEssence(ServerLevel level, BlockPos pos) {
        if (this.forgeBlockEntity == null || this.jarBlockEntity == null || this.jarBlockEntity.getEssenceStorage().isEmpty()) {
            if (this.essencePath != null) {
                this.essencePath = null;

                level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
            }

            return;
        }

        EssenceType essenceType = this.jarBlockEntity.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);

        this.essencePath = EssencePath.create(essenceType, this.jarBlockEntity.getBlockPos(), this.forgeBlockEntity.getBlockPos(), level.getRandom());

        //Better amount handling & also update the jar when we drain it :3
        int toTransfer = Math.min(5, this.jarBlockEntity.getEssenceStorage().amount());

        this.forgeBlockEntity.addEssence(essenceType, toTransfer);
        this.jarBlockEntity.addEssence(-toTransfer);
        level.sendBlockUpdated(this.jarBlockEntity.getBlockPos(), this.jarBlockEntity.getBlockState(), this.jarBlockEntity.getBlockState(), 3);

        level.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), 3);
    }

    @Override
    public void onLoad() {
        if (this.level != null && this.level.isClientSide() && this.getBlockState().getValue(BlockStateProperties.ENABLED)) {
            if (this.playAnimation) {
                this.transformAnimation.startIfStopped(this.ageInTicks);
            } else {
                this.rotateAnimation.startIfStopped(this.ageInTicks);
            }
        }
    }

    public void startAnimation() {
        this.playAnimation = true;
        this.ageInTicks = 0;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider provider) {
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), LOGGER)) {
            TagValueOutput output = TagValueOutput.createWithoutContext(reporter);

            output.storeNullable("essence_path", EssencePath.CODEC.codec(), this.essencePath);

            return output.buildResult();
        }
    }

    @Override
    public void onDataPacket(Connection net, ValueInput valueInput) {
        this.handleUpdateTag(valueInput);
    }

    @Override
    public void handleUpdateTag(ValueInput input) {
        super.handleUpdateTag(input);

        this.essencePath = input.read("essence_path", EssencePath.CODEC.codec()).orElse(null);
    }

    @Override
    public int getAgeInTicks() {
        return this.ageInTicks;
    }
}
