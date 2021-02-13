package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Obsidian Skull Shield Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.ObsidianSkullShieldItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-12
 */
public class ObsidianSkullShieldItem extends Item {

    public ObsidianSkullShieldItem(Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    @Override
    public boolean isShield(ItemStack stack, @Nullable LivingEntity entity) {
        return true;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        player.setActiveHand(hand);
        return ActionResult.resultConsume(itemstack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            if (!livingEntity.isInLava() && !livingEntity.isBurning()) {
                livingEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600, 0, false, false, true));
            }
        }
        super.inventoryTick(stack, worldIn, entity, itemSlot, isSelected);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ModItems.OBSIDIAN_INGOT.get() || super.getIsRepairable(toRepair, repair);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".obsidian_skull_shield").mergeStyle(TextFormatting.GRAY));
    }
}
