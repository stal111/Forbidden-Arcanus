package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Clibano Main Part Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.ClibanoMainPartBlock
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-05-22
 */
public class ClibanoMainPartBlock extends Block implements EntityBlock {

    public ClibanoMainPartBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new ClibanoMainBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(pos) instanceof ClibanoMainBlockEntity blockEntity) {
                Containers.dropContents(level, pos, blockEntity);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }

        pos = pos.relative(Direction.DOWN).relative(Direction.NORTH).relative(Direction.WEST);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    BlockPos offsetPos = pos.offset(i, j, k);

                    if (level.getBlockEntity(offsetPos) instanceof ClibanoBlockEntity blockEntity) {
                        level.levelEvent(2001, offsetPos, Block.getId(level.getBlockState(offsetPos)));

                        level.setBlock(offsetPos, blockEntity.getState(), 2);
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return null;
        }
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.CLIBANO_MAIN.get(), ClibanoMainBlockEntity::serverTick);
    }
}
