package com.stal111.forbidden_arcanus.common.block.dispenser;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Soul Dispense Behavior <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.dispenser.SoulDispenseBehavior
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-21
 */
public class SoulDispenseBehavior extends DefaultDispenseItemBehavior {

    @Nonnull
    @Override
    protected ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
        Level level = source.level();
        BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
        BlockState state = level.getBlockState(pos);

        if (state.is(ModBlocks.SOULLESS_SAND.get())) {
            stack.shrink(1);

            level.setBlockAndUpdate(pos, Blocks.SOUL_SAND.defaultBlockState());
            level.levelEvent(null, 2001, pos, Block.getId(state));

            return stack;
        }
        return super.execute(source, stack);
    }
}
