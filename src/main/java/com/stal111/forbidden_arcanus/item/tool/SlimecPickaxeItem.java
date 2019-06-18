package com.stal111.forbidden_arcanus.item.tool;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class SlimecPickaxeItem extends ModPickaxeItem {

	public SlimecPickaxeItem(String name, IItemTier tier, int attackDamageIn, float attackSpeedIn) {
		super(name, tier, attackDamageIn, attackSpeedIn);
	}
	
	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (group == Main.FORBIDDEN_ARCANUS) {
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
