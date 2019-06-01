package com.stal111.forbidden_arcanus.item.tool;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import com.stal111.forbidden_arcanus.util.RayTraceTools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ToolDracoArcanusScepter extends Item {

	public ToolDracoArcanusScepter(String name) {
		super(new Item.Properties().group(Main.FORBIDDEN_ARCANUS).maxStackSize(1).setNoRepair());
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (!world.isRemote) {
				this.fireSphere(world, player);
			}
			player.getCooldownTracker().setCooldown(this, 10);
		}
		return super.onItemUseFinish(stack, world, entityLiving);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		player.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
	
	@Override
	public EnumAction getUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		return 30;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	private void fireSphere(World world, EntityPlayer player) {
		world.playSound(null, player.getPosition(), ModSounds.dark_bolt_launch, SoundCategory.NEUTRAL, 1.0f, 1.0f);
		RayTraceTools.Beam beam = new RayTraceTools.Beam(world, player, 20);
		spawnSphere(player, beam);

	}

	private void spawnSphere(EntityPlayer player, RayTraceTools.Beam beam) {
		
		Vec3d start = beam.getStart();
		Vec3d lookVec = beam.getLookVec();
		double accelX = lookVec.x * 1.0D;
		double accelY = lookVec.y * 1.0D;
		double accelZ = lookVec.z * 1.0D;

		EnergyBallEntity laser = new EnergyBallEntity(player.getEntityWorld(), player, accelX, accelY, accelZ);
		laser.posX = start.x;
		laser.posY = start.y;
		laser.posZ = start.z;

		player.getEntityWorld().spawnEntity(laser);

	}

}
