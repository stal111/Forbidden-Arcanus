package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;

public class HephaestusForgeBlock extends Block {

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.makeCuboidShape(3.0D, 2.0D, 3.0D, 13.0D, 3.0D, 13.0D),
            Block.makeCuboidShape(5.0D, 3.0D, 5.0D, 11.0D, 7.0D, 11.0D),
            Block.makeCuboidShape(1.0D, 7.0D, 1.0D, 15.0D, 13.0D, 15.0D),
            Block.makeCuboidShape(0.0D, 11.0D, 0.0D, 3.0D, 14.0D, 3.0D),
            Block.makeCuboidShape(0.0D, 11.0D, 13.0D, 3.0D, 14.0D, 16.0D),
            Block.makeCuboidShape(13.0D, 11.0D, 0.0D, 16.0D, 14.0D, 3.0D),
            Block.makeCuboidShape(13.0D, 11.0D, 13.0D, 16.0D, 14.0D, 16.0D));

    public HephaestusForgeBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }
}
