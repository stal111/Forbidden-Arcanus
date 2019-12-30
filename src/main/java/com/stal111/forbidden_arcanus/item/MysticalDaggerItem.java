package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.potion.effect.ModEffects;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class MysticalDaggerItem extends SwordItem {

    public MysticalDaggerItem(IItemTier tier, float attackDamage, float attackSpeed, Item.Properties properties) {
        super(tier, (int) attackDamage, attackSpeed, new Item.Properties().group(Main.FORBIDDEN_ARCANUS).maxStackSize(1));
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        stack.setDamage(stack.getDamage() + 1);
        return stack;
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int i = new Random().nextInt(5);
        if (i == 1) {
            target.addPotionEffect(new EffectInstance(ModEffects.darkened, 100, 0));
        }
        return super.hitEntity(stack, target, attacker);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add((new TranslationTextComponent("tooltip." + Main.MOD_ID + ".mystical_dagger")).applyTextStyle(TextFormatting.GRAY));
    }
}
