package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.common.network.clientbound.SpawnParticlePayload;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class AurealMissile extends Projectile {

    public double accelerationPower = 0.03D;

    public AurealMissile(EntityType<? extends AurealMissile> entityType, Level level) {
        super(entityType, level);
    }

    public AurealMissile(LivingEntity livingEntity, Level level, double x, double y, double z) {
        super(ModEntities.AUREAL_MISSILE.get(), level);
        this.setOwner(livingEntity);
        this.setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {

    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }

        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);
        float f;
        if (!this.isInWater()) {
            f = this.getInertia();
        } else {
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * (double) 0.25F, d1 - vec3.y * (double) 0.25F, d2 - vec3.z * (double) 0.25F, vec3.x, vec3.y, vec3.z);
            }

            f = this.getLiquidInertia();
        }

        this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale(f));
        if (this.tickCount % 4 == 0) {
            this.level().addParticle(ModParticles.MAGIC_GLINT.get(), this.getX(), this.getY() + 0.2, this.getZ(), 0.0F, 0.0F, 0.0F);
        }

        this.setPos(d0, d1, d2);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level() instanceof ServerLevel serverLevel) {
            Entity entity = result.getEntity();

            if (this.getOwner() instanceof LivingEntity owner) {
                owner.setLastHurtMob(entity);
            }

            DamageSource damagesource = this.damageSources().magic();

            if (entity.hurt(damagesource, 5.0F) && entity instanceof LivingEntity livingEntity) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, livingEntity, damagesource);
            }
        }
    }

    @Override
    protected void onHit(@NotNull HitResult result) {
        super.onHit(result);

        if (this.level() instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(this.blockPosition()), new SpawnParticlePayload(this.getX(), this.getY(), this.getZ(), 2));

            this.discard();
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    private float getInertia() {
        return 0.95F;
    }

    private float getLiquidInertia() {
        return 0.8F;
    }
}
