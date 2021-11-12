package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class SlimecPickaxeItem extends PickaxeItem {

	public SlimecPickaxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		if (group == ForbiddenArcanus.FORBIDDEN_ARCANUS || group == CreativeModeTab.TAB_SEARCH) {
			ItemStack stack = new ItemStack(this);
			stack.enchant(Enchantments.SILK_TOUCH, 1);
			items.add(stack);
		}
	}
	
	@Override
	public void onCraftedBy(ItemStack stack, Level worldIn, Player playerIn) {
		stack.enchant(Enchantments.SILK_TOUCH, 1);
		super.onCraftedBy(stack, worldIn, playerIn);
	}
	
}
