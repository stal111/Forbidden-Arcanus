package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class DracoArcanusArrowEntity extends AbstractArrowEntity {

    public DracoArcanusArrowEntity(LivingEntity entity, World world) {
        super(ModEntities.DRACO_ARCANUS_ARROW.get(), entity, world);
    }

    public DracoArcanusArrowEntity(EntityType<? extends AbstractArrowEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ModItems.DRACO_ARCANUS_ARROW.get());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vector3d vec3d1 = this.getMotion();
        if (this.world.isRemote() && this.rand.nextDouble() >= 0.5) {
            this.world.addParticle(ParticleTypes.DRAGON_BREATH, this.getPosX() - vec3d1.x, this.getPosY() - vec3d1.y + 0.05D, this.getPosZ() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ());
        areaeffectcloudentity.setParticleData(ParticleTypes.DRAGON_BREATH);
        areaeffectcloudentity.setRadius(2.0F);
        areaeffectcloudentity.setDuration(400);
        areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new EffectInstance(Effects.INSTANT_DAMAGE, 1, 1));

        this.world.playEvent(2006, this.getPosition(), 0);
        this.world.addEntity(areaeffectcloudentity);
        super.arrowHit(entity);
    }
}
