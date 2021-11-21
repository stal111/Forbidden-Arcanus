package com.stal111.forbidden_arcanus.block.dispenser;

import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class SoulDispenseBehavior extends DefaultDispenseItemBehavior {

    public SoulDispenseBehavior() {
    }

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        Level world = source.getLevel();
        Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos pos = source.getPos().relative(direction, 1);
        Block block = world.getBlockState(pos).getBlock();
        if (block == NewModBlocks.SOULLESS_SAND.get()) {
            world.levelEvent(2001, pos, Block.getId(world.getBlockState(pos)));
            world.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
            stack.shrink(1);
            return stack;
        }
        return super.execute(source, stack);
    }
}
