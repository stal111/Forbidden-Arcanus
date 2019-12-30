package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class SoullessSandBlock extends SoulSandBlock {

    public SoullessSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState p_196267_1_, World p_196267_2_, BlockPos p_196267_3_, Random p_196267_4_) {
        BubbleColumnBlock.placeBubbleColumn(p_196267_2_, p_196267_3_.up(), false);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ModItems.SOUL.getItem()) {
            if (world.isBlockModifiable(player, pos)) {
                ModUtils.shrinkStack(player, stack);
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
