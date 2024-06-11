package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;
import java.util.Optional;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorBlockEntity extends BlockEntity {

    private static final Map<EssenceType, Holder<PoiType>> ESSENCE_TYPE_TO_POI_TYPE = Map.of(
            EssenceType.AUREAL, ModPOITypes.AUREAL_UTREM_JAR,
            EssenceType.SOULS, ModPOITypes.SOULS_UTREM_JAR,
            EssenceType.BLOOD, ModPOITypes.BLOOD_UTREM_JAR,
            EssenceType.EXPERIENCE, ModPOITypes.EXPERIENCE_UTREM_JAR
    );

    private static final int TICKS_BETWEEN_ACTIONS = 100;
    private static final int TRANSFORM_ANIMATION_DURATION = 60;

    public final AnimationState transformAnimation = new AnimationState();
    public final AnimationState rotateAnimation = new AnimationState();

    private boolean playAnimation = false;
    private int tickCount;

    public QuantumInjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_INJECTOR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        blockEntity.transformAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && blockEntity.playAnimation, blockEntity.tickCount);
        blockEntity.rotateAnimation.animateWhen(state.getValue(BlockStateProperties.ENABLED) && !blockEntity.transformAnimation.isStarted(), blockEntity.tickCount);

        if (blockEntity.playAnimation && blockEntity.tickCount >= TRANSFORM_ANIMATION_DURATION) {
            blockEntity.playAnimation = false;
        }

        blockEntity.tickCount++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        if (level.getGameTime() % TICKS_BETWEEN_ACTIONS != 0 || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.getPoiManager()
                .findClosest(holder -> holder.value() == ModPOITypes.HEPHAESTUS_FORGE.get(), pos, 4, PoiManager.Occupancy.ANY)
                .flatMap(forgePos -> serverLevel.getBlockEntity(forgePos, ModBlockEntities.HEPHAESTUS_FORGE.get()))
                .ifPresent(forgeBlockEntity -> {
                    transferEssence(serverLevel, pos, forgeBlockEntity);
                });

    }

    private static void transferEssence(ServerLevel level, BlockPos pos, HephaestusForgeBlockEntity blockEntity) {
        Optional<EssenceType> essenceType = getEssenceType(blockEntity);

        essenceType.ifPresent(type -> {
            level.getPoiManager()
                    .findClosest(holder -> holder.value() == ESSENCE_TYPE_TO_POI_TYPE.get(type).value(), pos, 10, PoiManager.Occupancy.ANY)
                    .flatMap(essenceJarPos -> level.getBlockEntity(essenceJarPos, ModBlockEntities.ESSENCE_UTREM_JAR.get()))
                    .ifPresent(essenceJarBlockEntity -> {
                        blockEntity.getEssenceManager().increaseEssence(type, 100);
                        essenceJarBlockEntity.addEssence(-100);
                    });
        });
    }

    private static Optional<EssenceType> getEssenceType(HephaestusForgeBlockEntity blockEntity) {
        for (EssenceType type : EssenceType.values()) {
            if (!blockEntity.getEssenceManager().isEssenceFull(type)) {
                return Optional.of(type);
            }
        }

        return Optional.empty();
    }

    @Override
    public void onLoad() {
        if (this.level != null && this.level.isClientSide() && this.getBlockState().getValue(BlockStateProperties.ENABLED)) {
            if (this.playAnimation) {
                this.transformAnimation.startIfStopped(this.tickCount);
            } else {
                this.rotateAnimation.startIfStopped(this.tickCount);
            }
        }
    }

    public void startAnimation() {
        this.playAnimation = true;
        this.tickCount = 0;
    }

    public int getTickCount() {
        return this.tickCount;
    }
}
