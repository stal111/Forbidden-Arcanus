package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModParticles;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.util.Random;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class SeedBulletEntity extends ThrowableItemProjectile {

	public SeedBulletEntity(Level world, double x, double y, double z) {
		super(ModEntities.SEED_BULLET.get(), x, y, z, world);
	}

	public SeedBulletEntity(Level world, LivingEntity thrower) {
		super(ModEntities.SEED_BULLET.get(), thrower, world);
	}

    public SeedBulletEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level world) {
		super(entityType, world);
    }

    @Override
	protected void onHit(HitResult result) {
		if (result.getType() == HitResult.Type.ENTITY) {
			Entity entity = ((EntityHitResult)result).getEntity();
			entity.hurt(DamageSource.indirectMagic(this, this.getOwner()), 0);

		}
		if (!this.level.isClientSide) {
			this.level.addFreshEntity(new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(this.getRandomSeed())));
			this.level.broadcastEntityEvent(this, (byte)3);
			this.remove(RemovalReason.DISCARDED);
		}
	}

	private Item getRandomSeed() {
		return ModTags.Items.SEEDS.getRandomElement(new Random());
	}

	@OnlyIn(Dist.CLIENT)
	private ParticleOptions getParticle() {
		ItemStack lvt_1_1_ = this.getItemRaw();
		return (lvt_1_1_.isEmpty() ? (ParticleOptions) ModParticles.ITEM_SEED_BULLET.get() : new ItemParticleOption(ParticleTypes.ITEM, lvt_1_1_));
	}

	@Override
	public void handleEntityEvent(byte p_70103_1_) {
		if (p_70103_1_ == 3) {
			ParticleOptions lvt_2_1_ = this.getParticle();

			for(int lvt_3_1_ = 0; lvt_3_1_ < 8; ++lvt_3_1_) {
				this.level.addParticle(lvt_2_1_, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		Vec3 vec3d = this.getDeltaMovement();
		if (!this.isInWater()) {
			this.level.addParticle(ParticleTypes.END_ROD, this.getX() - vec3d.x * 0.25D, (this.getY() - vec3d.y * 0.25D) + 0.2D, this.getZ() - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
		}
	}

	@Override
	protected Item getDefaultItem() {
		return ModItems.SEED_BULLET.get();
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
