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

    public final AnimationState animation = new AnimationState();

    private int tickCount;

    public QuantumInjectorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUANTUM_INJECTOR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, QuantumInjectorBlockEntity blockEntity) {
        blockEntity.animation.animateWhen(state.getValue(BlockStateProperties.ENABLED), blockEntity.tickCount);

        blockEntity.tickCount++;
    }

    public int getTickCount() {
        return this.tickCount;
    }
}
