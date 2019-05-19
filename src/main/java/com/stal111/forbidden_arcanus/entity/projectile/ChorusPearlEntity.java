package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ChorusPearlEntity extends EntityThrowable {

	public ChorusPearlEntity(World world) {
		super(ModEntities.TYPE_CHORUS_PEARL, world);
	}

	public ChorusPearlEntity(World world, double x, double y, double z) {
		super(ModEntities.TYPE_CHORUS_PEARL, x, y, z, world);
	}

	public ChorusPearlEntity(World world, EntityLivingBase thrower) {
		super(ModEntities.TYPE_CHORUS_PEARL, thrower, world);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		EntityLivingBase entity = (EntityLivingBase) result.entity;
		if (entity != null) {
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}

		if (result.type == RayTraceResult.Type.ENTITY) {
			if (!this.world.isRemote) {
				for (int i = 0; i < 16; i++) {
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
					double d1 = entity.posX + ((entity).getRNG().nextDouble() - 0.5D) * 30.0D;
					double d2 = MathHelper.clamp(result.entity.posY + (double) ((entity).getRNG().nextInt(16) - 8),
							0.0D, (double) (entity.world.getActualHeight() - 1));
					double d3 = entity.posZ + ((entity).getRNG().nextDouble() - 0.5D) * 30.0D;

					if (entity.attemptTeleport(d1, d2, d3)) {
						entity.fallDistance = 0;
						entity.world.playSound((EntityPlayer) null, result.entity.posX, result.entity.posY,
								result.entity.posZ, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F,
								1.0F);
						entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
						break;
					}
				}
			}
		} else if (!world.isRemote) {
			this.world.spawnEntity(
					new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.chorus_pearl)));
		}
		this.remove();
	}

	@Override
	public void tick() {
		EntityLivingBase entitylivingbase = this.getThrower();
		if (entitylivingbase != null && entitylivingbase instanceof EntityPlayer && !entitylivingbase.isAlive()) {
			this.remove();
		} else {
			super.tick();
		}

	}

}
