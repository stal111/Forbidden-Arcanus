package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity;
import net.minecraftforge.fml.network.NetworkHooks;

public class SeedBulletEntity extends ProjectileItemEntity {

	@SuppressWarnings("unchecked")
	public SeedBulletEntity(World world) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.seed_bullet, world);
	}

	@SuppressWarnings("unchecked")
	public SeedBulletEntity(World world, double x, double y, double z) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.seed_bullet, x, y, z, world);
	}

	@SuppressWarnings("unchecked")
	public SeedBulletEntity(World world, LivingEntity thrower) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.seed_bullet, thrower, world);
	}

	@SuppressWarnings("unchecked")
	public SeedBulletEntity(SpawnEntity packet, World world) {
		super((EntityType<? extends ProjectileItemEntity>) ModEntities.seed_bullet, world);
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		this.world.addEntity(new ItemEntity(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.getRandomSeed())));

		this.remove();
	}

	private Item getRandomSeed() {
		return null;

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
		return ModItems.seed_bullet;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
