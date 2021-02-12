package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.tileentity.ObsidianSkullTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.valhelsia.valhelsia_core.block.ValhelsiaContainerBlock;

import javax.annotation.Nullable;

/**
 * Obsidian Skull Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.ObsidianSkullBlock
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullBlock extends ValhelsiaContainerBlock implements IArmorVanishable {

    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;
    private static final VoxelShape SHAPE = Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D);

    public ObsidianSkullBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(ROTATION, 0));
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ObsidianSkullTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(ROTATION, MathHelper.floor((double) (context.getPlacementYaw() * 16.0F / 360.0F) + 0.5D) & 15);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(ROTATION, rot.rotate(state.get(ROTATION), 16));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.with(ROTATION, mirrorIn.mirrorRotation(state.get(ROTATION), 16));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }
}
