package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class SlimecPickaxeItem extends PickaxeItem {

	public SlimecPickaxeItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Item.Properties properties) {
		super(tier, attackDamageIn, attackSpeedIn, properties);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (group == ForbiddenArcanus.FORBIDDEN_ARCANUS || group == ItemGroup.SEARCH) {
			ItemStack stack = new ItemStack(this);
			stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
			items.add(stack);
		}
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
		stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
		super.onCreated(stack, worldIn, playerIn);
	}
	
}
