package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.Packet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class DracoArcanusArrowEntity extends AbstractArrow {

    public DracoArcanusArrowEntity(LivingEntity entity, Level world) {
        super(ModEntities.DRACO_ARCANUS_ARROW.get(), entity, world);
    }

    public DracoArcanusArrowEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.DRACO_ARCANUS_ARROW.get());
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vec3 vec3d1 = this.getDeltaMovement();
        if (this.level.isClientSide() && this.random.nextDouble() >= 0.5) {
            this.level.addParticle(ParticleTypes.DRAGON_BREATH, this.getX() - vec3d1.x, this.getY() - vec3d1.y + 0.05D, this.getZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(this.level, this.getX(), this.getY(), this.getZ());
        areaeffectcloudentity.setParticle(ParticleTypes.DRAGON_BREATH);
        areaeffectcloudentity.setRadius(2.0F);
        areaeffectcloudentity.setDuration(400);
        areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));

        this.level.levelEvent(2006, this.blockPosition(), 0);
        this.level.addFreshEntity(areaeffectcloudentity);
        super.doPostHurtEffects(entity);
    }
}
