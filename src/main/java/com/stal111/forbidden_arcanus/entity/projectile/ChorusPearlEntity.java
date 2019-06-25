package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = IRendersAsItem.class)
public class ChorusPearlEntity extends ProjectileItemEntity {

	@SuppressWarnings("unchecked")
	public ChorusPearlEntity(World world) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.chorus_pearl, world);
	}

	@SuppressWarnings("unchecked")
	public ChorusPearlEntity(World world, double x, double y, double z) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.chorus_pearl, x, y, z, world);
	}

	@SuppressWarnings("unchecked")
	public ChorusPearlEntity(World world, LivingEntity thrower) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.chorus_pearl, thrower, world);
	}

	@SuppressWarnings("unchecked")
	public ChorusPearlEntity(SpawnEntity packet, World world) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.chorus_pearl, world);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		LivingEntity livingEntity = (LivingEntity) this.getThrower();
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			LivingEntity entity = (LivingEntity) ((EntityRayTraceResult) result).getEntity();
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, livingEntity), 0.0F);

			if (!world.isRemote) {
				for (int i = 0; i < 16; i++) {
					if (entity.isPassenger()) {
						entity.stopRiding();
					}
					double d1 = entity.posX + (entity.getRNG().nextDouble() - 0.5D) * 30.0D;
					double d2 = MathHelper.clamp(entity.posY + (double) ((entity).getRNG().nextInt(16) - 8), 0.0D,
							(double) (entity.world.getActualHeight() - 1));
					double d3 = entity.posZ + ((entity).getRNG().nextDouble() - 0.5D) * 30.0D;

					entity.setPositionAndUpdate(d1, d2, d3);
					entity.fallDistance = 0;
					entity.world.playSound((PlayerEntity) null, entity.posX, entity.posY, entity.posZ,
							SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
					entity.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
				}
			}
		} else {
			this.world.addEntity(
					new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.chorus_pearl)));
		}

		this.remove();
	}

	@Override
	public void tick() {
		LivingEntity entitylivingbase = this.getThrower();
		if (entitylivingbase != null && entitylivingbase instanceof PlayerEntity && !entitylivingbase.isAlive()) {
			this.remove();
		} else {
			super.tick();
		}

	}

	@Override
	protected Item func_213885_i() {
		return ModItems.chorus_pearl;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
