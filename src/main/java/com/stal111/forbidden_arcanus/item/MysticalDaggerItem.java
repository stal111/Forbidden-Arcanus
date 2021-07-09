package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Mystical Dagger Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.MysticalDaggerItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class MysticalDaggerItem extends SwordItem {

    public MysticalDaggerItem(IItemTier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, (int) attackDamage, attackSpeed, properties);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setDamage(stack.getDamage() + 10);
        return copy;
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull LivingEntity target, @Nonnull LivingEntity attacker) {
        if (attacker.getRNG().nextInt(5) == 0) {
            target.addPotionEffect(new EffectInstance(ModEffects.DARKENED.get(), 100, 0));
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".mystical_dagger").mergeStyle(TextFormatting.GRAY));
    }
}
