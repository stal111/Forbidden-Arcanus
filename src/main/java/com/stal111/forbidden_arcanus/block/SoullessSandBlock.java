package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SoullessSandBlock extends SoulSandBlock {

    public SoullessSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ModItems.soul) {
            if (world.isBlockModifiable(player, pos)) {
                if (!player.abilities.isCreativeMode) {
                    stack.shrink(1);
                }
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                if (!world.isRemote) {
                    world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader reader, BlockPos pos, PathType type) {
        return true;
    }
}
