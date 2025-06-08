package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class AurealMissile extends AbstractHurtingProjectile {

    public AurealMissile(EntityType<? extends AurealMissile> entityType, Level level) {
        super(entityType, level);
    }

    public AurealMissile(LivingEntity livingEntity, Level level, double x, double y, double z) {
        super(ModEntities.AUREAL_MISSILE.get(), x, y, z, level);
        this.setOwner(livingEntity);
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
