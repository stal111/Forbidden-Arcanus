package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

/**
 * Energy Ball <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.entity.projectile.EnergyBall
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
public class EnergyBall extends Projectile {

    private static final float MOTION_FACTOR = 0.95F;
    private static final float MOTION_FACTOR_WATER = 0.8F;
    private static final float ROTATION_SPEED = 0.2F;
    private static final float DAMAGE_AMOUNT = 5.5F;

    private LivingEntity shootingEntity;
    private int ticksAlive;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public EnergyBall(Level level, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(ModEntities.ENERGY_BALL.get(), level);
        this.shootingEntity = shooter;
        this.snapTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.yRotO, shooter.xRotO);
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        double d0 = Mth.sqrt((float) (accelX * accelX + accelY * accelY + accelZ * accelZ));

        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
    }

    public EnergyBall(EntityType<? extends EnergyBall> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {

    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    @Override
    public void tick() {
        super.tick();

        HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, entity -> entity.isAlive() && entity != this.shootingEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitResult)) {
            this.onImpact(hitResult);
        }

        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(getX() + vec3.x, getY() + (vec3.y - 0.01), getZ() + vec3.z);
        ProjectileUtil.rotateTowardsMovement(this, ROTATION_SPEED);

        float motionFactor = MOTION_FACTOR;

        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.level().addParticle(ParticleTypes.BUBBLE, this.getX() - vec3.x * 0.25D, this.getY() - vec3.y * 0.25D, this.getZ() - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }
            motionFactor = MOTION_FACTOR_WATER;
        }
        this.setDeltaMovement(vec3.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(motionFactor));
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    public void onImpact(HitResult result) {
        if (this.level().isClientSide()) {
            return;
        }

        if (result instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();

            entity.hurt(this.level().damageSources().indirectMagic(this, this.shootingEntity), DAMAGE_AMOUNT);

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level());
            lightningBolt.setPos(entity.getX(), entity.getY(), entity.getZ());

            this.level().addFreshEntity(lightningBolt);
        } else if (result.getType() == HitResult.Type.BLOCK) {
            Vec3 vec3 = result.getLocation();

            this.level().playSound(null, vec3.x(), vec3.y(), vec3.z(), ModSounds.ENERGY_BALL_HIT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        this.discard();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        Vec3 vec3 = this.getDeltaMovement();
        //TODO
//        tag.put("direction", this.newDoubleList(vec3.x, vec3.y, vec3.z));
//        tag.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        output.putInt("life", this.ticksAlive);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

//        if (tag.contains("power")) {
//            ListTag list = tag.getListOrEmpty("power");
//
//            if (list.size() == 3) {
//                this.accelerationX = list.getDoubleOr(0, 0);
//                this.accelerationY = list.getDoubleOr(1, 0);
//                this.accelerationZ = list.getDoubleOr(2, 0);
//            }
//        }
//
//        this.ticksAlive = tag.getIntOr("life", 0);
//        if (tag.contains("direction") && tag.getListOrEmpty("direction").size() == 3) {
//            ListTag list = tag.getListOrEmpty("direction");
//            this.setDeltaMovement(list.getDoubleOr(0, 0), list.getDoubleOr(1, 0), list.getDoubleOr(2, 0));
//        } else {
//            this.discard();
//        }
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 1.0F;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        this.markHurt();

        if (damageSource.getEntity() != null) {
            Vec3 vec3 = damageSource.getEntity().getLookAngle();

            this.setDeltaMovement(vec3);
            this.accelerationX = vec3.x * 0.1D;
            this.accelerationY = vec3.y * 0.1D;
            this.accelerationZ = vec3.z * 0.1D;

            if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
                this.shootingEntity = livingEntity;
            }

            return true;
        }
        return false;
    }

    @Override
    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }
}
