package com.stal111.forbidden_arcanus.item.tool;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class DracoArcanusScepterItem extends Item {

	public DracoArcanusScepterItem(String name) {
		super(new Item.Properties().group(Main.FORBIDDEN_ARCANUS).maxStackSize(1).setNoRepair());
		this.setRegistryName(new ResourceLocation(Main.MOD_ID, name));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entityLiving) {
		if (entityLiving instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entityLiving;
			if (!world.isRemote) {
//				this.fireSphere(world, player);
			}
			player.getCooldownTracker().setCooldown(this, 10);
		}
		return super.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		player.setActiveHand(hand);
		return new ActionResult<>(ActionResultType.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

//	private void fireSphere(World world, PlayerEntity player) {
//		world.playSound(null, player.getPosition(), ModSounds.dark_bolt_launch, SoundCategory.NEUTRAL, 1.0f, 1.0f);
//		RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, 20);
//		spawnSphere(player, beam);
//
//	}
//
//	private void spawnSphere(PlayerEntity player, RayTraceTools.Beam beam) {
//		
//		Vec3d start = beam.getStart();
//		Vec3d lookVec = beam.getLookVec();
//		double accelX = lookVec.x * 1.0D;
//		double accelY = lookVec.y * 1.0D;
//		double accelZ = lookVec.z * 1.0D;
//
//		EnergyBallEntity laser = new EnergyBallEntity(player.getEntityWorld(), player, accelX, accelY, accelZ);
//		laser.posX = start.x;
//		laser.posY = start.y;
//		laser.posZ = start.z;
//
//		player.getEntityWorld().func_217346_i(laser);
//
//	}

}
