package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

import javax.annotation.Nonnull;

/**
 * Mystical Dagger Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.MysticalDaggerItem
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class MysticalDaggerItem extends SwordItem {

    public MysticalDaggerItem(Tier tier, Item.Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setDamageValue(stack.getDamageValue() + 10);
        return copy;
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (attacker.getRandom().nextInt(5) == 0) {
            //target.addEffect(new MobEffectInstance(ModMobEffects.DARKENED.get(), 100, 0));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
