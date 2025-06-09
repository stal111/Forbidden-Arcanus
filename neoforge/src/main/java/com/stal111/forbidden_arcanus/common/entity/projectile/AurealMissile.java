package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AurealMissile extends AbstractHurtingProjectile {

    public AurealMissile(EntityType<? extends AurealMissile> entityType, Level level) {
        super(entityType, level);
    }

    public AurealMissile(LivingEntity livingEntity, Level level, double x, double y, double z) {
        super(ModEntities.AUREAL_MISSILE.get(), x, y, z, level);
        this.setOwner(livingEntity);
        this.accelerationPower = 0.01D;
    }

    @Override
    public void tick() {
        this.accelerationPower = 0.01D;

        Entity entity = this.getOwner();
        if (this.level().isClientSide() || (entity == null || !entity.isRemoved()) && this.level().hasChunkAt(this.blockPosition())) {
            super.tick();
            if (this.shouldBurn()) {
                this.igniteForSeconds(1.0F);
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity, this.getClipType());
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
                for(int i = 0; i < 4; ++i) {
                    float f1 = 0.25F;
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * (double)0.25F, d1 - vec3.y * (double)0.25F, d2 - vec3.z * (double)0.25F, vec3.x, vec3.y, vec3.z);
                }

                f = this.getLiquidInertia();
            }

            this.setDeltaMovement(vec3.add(vec3.normalize().scale(this.accelerationPower)).scale(f));
            if (this.tickCount % 4 == 0) {
                this.level().addParticle(ModParticles.MAGIC_GLINT.get(), this.getX(), this.getY() + 0.2, this.getZ(), 0.0F, 0.0F, 0.0F);
            }

            this.setPos(d0, d1, d2);
        } else {
            this.discard();
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return false;
    }

    @Override
    protected boolean shouldBurn() {
        return false;
    }

    @Nullable
    @Override
    protected ParticleOptions getTrailParticle() {
        return null;
    }
}
