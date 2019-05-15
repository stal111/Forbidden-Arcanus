package com.stal111.forbidden_arcanus.item.tool;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ToolDracoArcanusScepter extends Item {

	public ToolDracoArcanusScepter(String name) {
		super(new Item.Properties().group(Main.FORBIDDEN_ARCANUS).maxStackSize(1).setNoRepair());
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			
		}
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

}
