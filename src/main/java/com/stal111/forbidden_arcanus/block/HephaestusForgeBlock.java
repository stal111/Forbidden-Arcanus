package com.stal111.forbidden_arcanus.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.HephaestusForgeBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class HephaestusForgeBlock extends Block {

    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(
            VoxelShapeHelper.combineAll(
                    makeCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 3.0D, 15.0D),
                    makeCuboidShape(2.0D, 3.0D, 2.0D, 14.0D, 4.0D, 14.0D),
                    makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 8.0D, 12.0D),
                    makeCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D)
            ),
            VoxelShapeHelper.combineAll(
                    makeCuboidShape(0.0D, 15.0D, 3.0D, 16.0D, 16.0D, 13.0D),
                    makeCuboidShape(3.0D, 15.0D, 0.0D, 13.0D, 16.0D, 16.0D)
            ),
            IBooleanFunction.ONLY_FIRST
    );

    public HephaestusForgeBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }
}
