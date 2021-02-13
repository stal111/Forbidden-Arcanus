package com.stal111.forbidden_arcanus.item.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.OptionalDispenseBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WallOrFloorItem;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Eternal Obsidian Skull Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.block.EternalObsidianSkullItem
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-13
 */
public class EternalObsidianSkullItem extends WallOrFloorItem {

    public EternalObsidianSkullItem(Block floorBlock, Block wallBlock, Properties properties) {
        super(floorBlock, wallBlock, properties);
        DispenserBlock.registerDispenseBehavior(this, new OptionalDispenseBehavior() {
            @Override
            protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                this.setSuccessful(ArmorItem.func_226626_a_(source, stack));
                return stack;
            }
        });
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;

            livingEntity.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 999999, 0, false, false, true));
        }
        super.inventoryTick(stack, worldIn, entity, itemSlot, isSelected);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".eternal_obsidian_skull").mergeStyle(TextFormatting.GRAY));
    }
}
