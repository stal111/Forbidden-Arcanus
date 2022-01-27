package com.stal111.forbidden_arcanus.common.block.dispenser;

import com.stal111.forbidden_arcanus.common.item.ArcaneBoneMealItem;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import javax.annotation.Nonnull;

/**
 * Arcane Bone Meal Dispense Behavior <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.dispenser.ArcaneBoneMealDispenseBehavior
 *
 * @author stal111
 * @version 1.18.1 - 2.0.3
 * @since 2022-01-27
 */
public class ArcaneBoneMealDispenseBehavior extends OptionalDispenseItemBehavior {

    @Nonnull
    @Override
    protected ItemStack execute(@Nonnull BlockSource source, @Nonnull ItemStack stack) {
        Level level = source.getLevel();
        BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        BlockState state = level.getBlockState(pos);

        this.setSuccess(true);

        if (state.is(Blocks.FARMLAND)) {
            stack.shrink(1);

            level.setBlockAndUpdate(pos, ModBlocks.MAGICAL_FARMLAND.get().defaultBlockState().setValue(BlockStateProperties.MOISTURE, state.getValue(BlockStateProperties.MOISTURE)));
            level.levelEvent(null, 2001, pos, Block.getId(state));
        } else if (ArcaneBoneMealItem.applyBoneMeal(stack, level, pos, null)) {
            if (!level.isClientSide()) {
                level.levelEvent(2005, pos, 0);
            }
        } else {
            this.setSuccess(false);
        }

        return stack;
    }
}
