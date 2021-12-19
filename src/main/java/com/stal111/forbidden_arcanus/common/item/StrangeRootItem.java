package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

/**
 * Strange Root Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.StrangeRootItem
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class StrangeRootItem extends ItemNameBlockItem {

    public StrangeRootItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nonnull
    @Override
    public ItemStack finishUsingItem(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull LivingEntity entity) {
        super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide()) {
            entity.getActiveEffects().stream()
                    .filter(mobEffectInstance -> mobEffectInstance.getEffect().getCategory() == MobEffectCategory.HARMFUL)
                    .forEach(mobEffectInstance -> entity.removeEffect(mobEffectInstance.getEffect()));
        }

        return stack;
    }
}
