package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Mystical Dagger Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.MysticalDaggerItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class MysticalDaggerItem extends SwordItem {

    public MysticalDaggerItem(Tier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, (int) attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setDamageValue(stack.getDamageValue() + 10);
        return copy;
    }

    @Override
    public boolean hurtEnemy(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (attacker.getRandom().nextInt(5) == 0) {
            target.addEffect(new MobEffectInstance(ModEffects.DARKENED.get(), 100, 0));
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".mystical_dagger").withStyle(ChatFormatting.GRAY));
    }
}
