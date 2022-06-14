package com.stal111.forbidden_arcanus.common.entity.projectile;

import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;

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
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.yRotO, shooter.xRotO);
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
    protected void defineSynchedData() {}

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

        HitResult hitResult = ProjectileUtil.getHitResult(this, entity -> entity.isAlive() && entity != this.shootingEntity);
        if (hitResult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitResult)) {
            this.onImpact(hitResult);
        }

        Vec3 vec3 = this.getDeltaMovement();
        this.setPos(getX() + vec3.x, getY() + (vec3.y - 0.01), getZ() + vec3.z);
        ProjectileUtil.rotateTowardsMovement(this, ROTATION_SPEED);

        float motionFactor = MOTION_FACTOR;

        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vec3.x * 0.25D, this.getY() - vec3.y * 0.25D, this.getZ() - vec3.z * 0.25D, vec3.x, vec3.y, vec3.z);
            }
            motionFactor = MOTION_FACTOR_WATER;
        }
        this.setDeltaMovement(vec3.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(motionFactor));
        this.setPos(this.getX(), this.getY(), this.getZ());
    }

    public void onImpact(HitResult result) {
        if (this.level.isClientSide()) {
            return;
        }

        if (result instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();

            entity.hurt(DamageSource.indirectMagic(this, this.shootingEntity), DAMAGE_AMOUNT);

            LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, this.level);
            lightningBolt.setPos(entity.getX(), entity.getY(), entity.getZ());

            this.level.addFreshEntity(lightningBolt);
        } else if (result.getType() == HitResult.Type.BLOCK) {
            this.level.playSound(null, new BlockPos(result.getLocation()), ModSounds.ENERGY_BALL_HIT.get(), SoundSource.NEUTRAL, 1.0F, 1.0F);
        }

        this.discard();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        Vec3 vec3 = this.getDeltaMovement();
        tag.put("direction", this.newDoubleList(vec3.x, vec3.y, vec3.z));
        tag.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        tag.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("power", 9)) {
            ListTag list = tag.getList("power", 6);

            if (list.size() == 3) {
                this.accelerationX = list.getDouble(0);
                this.accelerationY = list.getDouble(1);
                this.accelerationZ = list.getDouble(2);
            }
        }

        this.ticksAlive = tag.getInt("life");
        if (tag.contains("direction", 9) && tag.getList("direction", 6).size() == 3) {
            ListTag list = tag.getList("direction", 6);
            this.setDeltaMovement(list.getDouble(0), list.getDouble(1), list.getDouble(2));
        } else {
            this.discard();
        }
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
    public boolean hurt(@Nonnull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        this.markHurt();

        if (source.getEntity() != null) {
            Vec3 vec3 = source.getEntity().getLookAngle();

            this.setDeltaMovement(vec3);
            this.accelerationX = vec3.x * 0.1D;
            this.accelerationY = vec3.y * 0.1D;
            this.accelerationZ = vec3.z * 0.1D;

            if (source.getEntity() instanceof LivingEntity livingEntity) {
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
