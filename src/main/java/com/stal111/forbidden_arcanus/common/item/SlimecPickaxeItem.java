package com.stal111.forbidden_arcanus.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

/**
 * Slimec Pickaxe Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SlimecPickaxeItem
 *
 * @author stal111
 * @version 1.19 - 2.0.1
 */
public class SlimecPickaxeItem extends PickaxeItem {

	public SlimecPickaxeItem(Tier tier, int attackDamage, float attackSpeed, Item.Properties properties) {
		super(tier, attackDamage, attackSpeed, properties);
	}
}
