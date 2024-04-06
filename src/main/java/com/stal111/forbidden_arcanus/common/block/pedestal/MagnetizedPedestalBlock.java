package com.stal111.forbidden_arcanus.common.block.pedestal;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * @author stal111
 * @since 2022-12-29
 */
public class MagnetizedPedestalBlock extends PedestalBlock {

    private static final VoxelShape ABOVE_SHAPE = Block.box(1.0D, 14.0D, 1.0D, 15.0D, 18.0D, 15.0D);

    public MagnetizedPedestalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!(level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) || blockEntity.hasStack()) {
            return;
        }

        if (entity instanceof ItemEntity itemEntity && Shapes.joinIsNotEmpty(Shapes.create(entity.getBoundingBox().move((-pos.getX()), (-pos.getY()), (-pos.getZ()))), ABOVE_SHAPE, BooleanOp.AND)) {
            blockEntity.setStack(itemEntity.getItem().split(1), null, PedestalEffectTrigger.MAGNETIZED_PICKUP);
        }
    }
}
