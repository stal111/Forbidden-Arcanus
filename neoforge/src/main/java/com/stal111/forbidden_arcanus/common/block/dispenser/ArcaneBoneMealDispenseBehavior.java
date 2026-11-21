package com.stal111.forbidden_arcanus.common.block.dispenser;

import com.stal111.forbidden_arcanus.common.item.ArcaneBoneMealItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;

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
        ServerLevel level = source.level();
        BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));

        this.setSuccess(true);

        if (ArcaneBoneMealItem.tryConvertFarmland(stack, level, pos, null)) {
            return stack;
        }

        if (ArcaneBoneMealItem.applyBoneMeal(stack, level, pos, null) || BoneMealItem.growWaterPlant(stack, level, pos, null)) {
            ArcaneBoneMealItem.playGrowthEffects(level, pos);
        } else {
            this.setSuccess(false);
        }

        return stack;
    }
}
