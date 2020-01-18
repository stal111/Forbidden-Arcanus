package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.potion.effect.ModEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.network.NetworkHooks;

public class FrozenArrowEntity extends AbstractArrowEntity {

    public FrozenArrowEntity(World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.FROZEN_ARROW.getEntityType(), world);
        this.setKnockbackStrength(0);
    }

    public FrozenArrowEntity(double p_i48547_2_, double p_i48547_4_, double p_i48547_6_, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.FROZEN_ARROW.getEntityType(), p_i48547_2_, p_i48547_4_, p_i48547_6_, world);
        this.setKnockbackStrength(0);
    }

    public FrozenArrowEntity(LivingEntity entity, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.FROZEN_ARROW.getEntityType(), entity, world);
        this.setKnockbackStrength(0);
    }

    public FrozenArrowEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super((EntityType<? extends AbstractArrowEntity>) ModEntities.FROZEN_ARROW.getEntityType(), world);
        this.setKnockbackStrength(0);
    }

    @Override
    protected ItemStack getArrowStack() {
        return Items.ARROW.getDefaultInstance();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        Vec3d vec3d1 = this.getMotion();
        if (this.world.isRemote() && this.rand.nextDouble() >= 0.5) {
            this.world.addParticle(ParticleTypes.SMOKE, this.func_226277_ct_() - vec3d1.x, this.func_226278_cu_() - vec3d1.y + 0.05D, this.func_226281_cx_() - vec3d1.z, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    @Override
    protected void arrowHit(LivingEntity entity) {
        EffectInstance effectinstance = new EffectInstance(ModEffects.frozen, 100, 0);
        entity.addPotionEffect(effectinstance);

        entity.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);

        AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(this.world, this.func_226277_ct_(), this.func_226278_cu_(), this.func_226281_cx_());
        areaeffectcloudentity.setParticleData(ParticleTypes.EFFECT);
        areaeffectcloudentity.setRadius(1.0F);
        areaeffectcloudentity.setDuration(80);
        areaeffectcloudentity.setRadiusPerTick((7.0F - areaeffectcloudentity.getRadius()) / (float)areaeffectcloudentity.getDuration());
        areaeffectcloudentity.addEffect(new EffectInstance(ModEffects.frozen, 1, 1));

        this.world.playEvent(2006, this.getPosition(), 0);
        this.world.addEntity(areaeffectcloudentity);

        super.arrowHit(entity);
    }
}
