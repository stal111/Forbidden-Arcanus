package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorBlockEntity extends BlockEntity {

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
