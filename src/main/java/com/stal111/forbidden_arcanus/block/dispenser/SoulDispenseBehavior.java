package com.stal111.forbidden_arcanus.block.dispenser;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoulDispenseBehavior extends DefaultDispenseItemBehavior {

    public SoulDispenseBehavior() {
    }

    @Override
    protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        World world = source.getWorld();
        Direction direction = source.getBlockState().get(DispenserBlock.FACING);
        BlockPos pos = source.getBlockPos().offset(direction, 1);
        Block block = world.getBlockState(pos).getBlock();
        if (block == ModBlocks.SOULLESS_SAND.getBlock()) {
            world.playEvent(2001, pos, Block.getStateId(world.getBlockState(pos)));
            world.setBlockState(pos, Blocks.SOUL_SAND.getDefaultState());
            stack.shrink(1);
            return stack;
        }
        return super.dispenseStack(source, stack);
    }
}
