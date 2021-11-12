package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.entity.projectile.SeedBulletEntity;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.world.level.Level;

import net.minecraft.Util;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;

public class SeedBulletItem extends Item {

	public SeedBulletItem(Item.Properties properties) {
		super(properties);
		DispenserBlock.registerBehavior(this, new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				return Util.make(new SeedBulletEntity(worldIn, position.x(), position.y(), position.z()), (entity) -> {
					entity.setItem(stackIn);
				});
			}
		});
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!player.getAbilities().instabuild) {
			stack.shrink(1);
		}
		world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (player.getRandom().nextFloat() * 0.4F + 0.8F));
		player.getCooldowns().addCooldown(this, 15);
		if (!world.isClientSide) {
			SeedBulletEntity entity = new SeedBulletEntity(world, player);
			entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(entity);
		}
		player.awardStat(Stats.ITEM_USED.get(this));
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);

	}

}
