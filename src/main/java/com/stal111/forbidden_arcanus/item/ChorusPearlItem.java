package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.ChorusPearlEntity;

import net.minecraft.block.DispenserBlock;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.ProjectileDispenseBehavior;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChorusPearlItem extends Item {

	public ChorusPearlItem(Item.Properties properties) {
		super(properties);
		DispenserBlock.registerDispenseBehavior(this, new ProjectileDispenseBehavior() {
			protected ProjectileEntity getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
				return Util.make(new ChorusPearlEntity(worldIn, position.getX(), position.getY(), position.getZ()), (entity) -> {
					entity.setItem(stackIn);
				});
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!player.abilities.isCreativeMode) {
			stack.shrink(1);
		}
		world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
		player.getCooldownTracker().setCooldown(this, 15);
		if (!world.isRemote) {
			ChorusPearlEntity entity = new ChorusPearlEntity(world, player);
			entity.func_234612_a_(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
			world.addEntity(entity);
		}
		player.addStat(Stats.ITEM_USED.get(this));
		return new ActionResult<>(ActionResultType.SUCCESS, stack);

	}

}
