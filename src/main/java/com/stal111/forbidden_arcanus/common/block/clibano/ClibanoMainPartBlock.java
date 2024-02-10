package com.stal111.forbidden_arcanus.common.block.clibano;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFrameBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Clibano Main Part Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.clibano.ClibanoMainPartBlock
 *
 * @author stal111
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
        if (state.is(newState.getBlock())) {
            return;
        }

        if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(pos) instanceof ClibanoMainBlockEntity blockEntity) {
            Containers.dropContents(level, pos, blockEntity.getStacks());

            blockEntity.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(pos));

            super.onRemove(state, level, pos, newState, isMoving);
        }

        ClibanoMainPartBlock.dismantle(level, pos);
    }

    public static void dismantle(Level level, BlockPos mainPos) {
        var positions = BlockPos.betweenClosed(mainPos.offset(-1, -1, -1), mainPos.offset(1, 1, 1));

        for (BlockPos pos : positions) {
            if (level.getBlockEntity(pos) instanceof ClibanoFrameBlockEntity blockEntity && !level.getBlockState(pos).isAir()) {
                level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(level.getBlockState(pos)));

                level.setBlockAndUpdate(pos, blockEntity.getFrameData().replaceState());
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
