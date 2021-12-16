package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Draco Arcanus Arrow <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.entity.projectile.DracoArcanusArrow
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-16
 */
public class DracoArcanusArrow extends AbstractArrow {

    public DracoArcanusArrow(EntityType<? extends DracoArcanusArrow> entityType, Level level) {
        super(entityType, level);
    }

    public DracoArcanusArrow(Level level, LivingEntity shooter) {
        super(ModEntities.DRACO_ARCANUS_ARROW.get(), shooter, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level.isClientSide() && !this.inGround) {
            this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Nonnull
    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(NewModItems.DRACO_ARCANUS_ARROW.get());
    }

    @Override
    protected void doPostHurtEffects(@Nonnull LivingEntity entity) {
        super.doPostHurtEffects(entity);

        AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());

        if (this.getOwner() instanceof LivingEntity owner) {
            areaEffectCloud.setOwner(owner);
        }
        areaEffectCloud.setParticle(ParticleTypes.DRAGON_BREATH);
        areaEffectCloud.setRadius(2.0F);
        areaEffectCloud.setDuration(400);
        areaEffectCloud.setRadiusPerTick((7.0F - areaEffectCloud.getRadius()) / (float) areaEffectCloud.getDuration());
        areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));

        this.level.levelEvent(2006, this.blockPosition(), 0);
        this.level.addFreshEntity(areaEffectCloud);
    }
}
