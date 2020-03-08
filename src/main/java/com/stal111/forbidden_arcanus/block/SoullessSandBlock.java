package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SoullessSandBlock extends SoulSandBlock {

    public SoullessSandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BubbleColumnBlock.placeBubbleColumn(world, pos.up(), false);
        super.tick(state, world, pos, random);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() == ModItems.SOUL.get()) {
            if (world.isBlockModifiable(player, pos)) {
                ItemStackUtils.shrinkStack(player, stack);
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                if (!world.isRemote) {
                    world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
                }
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader reader, BlockPos pos, PathType type) {
        return true;
    }
}
