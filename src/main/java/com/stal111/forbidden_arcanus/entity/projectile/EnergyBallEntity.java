package com.stal111.forbidden_arcanus.entity.projectile;

import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
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
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public class EnergyBallEntity extends Projectile {

    private LivingEntity shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    private double accelerationX;
    private double accelerationY;
    private double accelerationZ;

    public EnergyBallEntity(Level worldIn) {
        super(ModEntities.ENERGY_BALL.get(), worldIn);
    }

    public EnergyBallEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ) {
        super(ModEntities.ENERGY_BALL.get(), worldIn);
        this.shootingEntity = shooter;
        this.moveTo(shooter.getX(), shooter.getY(), shooter.getZ(), shooter.yRotO, shooter.xRotO);
        this.setPos(this.getX(), this.getY(), this.getZ());
        this.setDeltaMovement(Vec3.ZERO);

        double d0 = Mth.sqrt((float) (accelX * accelX + accelY * accelY + accelZ * accelZ));

        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
    }

    public EnergyBallEntity(FMLPlayMessages.SpawnEntity packet, Level world) {
        super(ModEntities.ENERGY_BALL.get(), world);
    }

    public EnergyBallEntity(EntityType<? extends EnergyBallEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData() {
    }

    @OnlyIn(Dist.CLIENT)
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
        if (this.level.isClientSide || (this.shootingEntity == null || this.shootingEntity.isAlive()) && this.level.hasChunkAt(new BlockPos(this.getX(), this.getY(), this.getZ()))) {
            super.tick();
            ++this.ticksInAir;

            HitResult raytraceresult = ProjectileUtil.getHitResult(this, entity -> entity.isAlive() && entity != this.shootingEntity);
            if (raytraceresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
                this.onImpact(raytraceresult);
            }

            Vec3 vec3d = this.getDeltaMovement();
            this.setPos(getX() + vec3d.x, getY() + (vec3d.y - 0.01), getZ() + vec3d.z);
            ProjectileUtil.rotateTowardsMovement(this, 0.2F);

            float f = this.getMotionFactor();

            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level.addParticle(ParticleTypes.BUBBLE, this.getX() - vec3d.x * 0.25D, this.getY() - vec3d.y * 0.25D, this.getZ() - vec3d.z * 0.25D, vec3d.x, vec3d.y, vec3d.z);
                }
                f = 0.8F;
            }
            this.setDeltaMovement(vec3d.add(this.accelerationX, this.accelerationY, this.accelerationZ).scale(f));
            this.setPos(this.getX(), this.getY(), this.getZ());
        } else {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected float getMotionFactor() {
        return 0.95F;
    }


    public void onImpact(HitResult result) {
        if (!this.level.isClientSide) {
            if (result.getType() == HitResult.Type.ENTITY) {
                Entity entity = ((EntityHitResult)result).getEntity();
                ServerLevel world = (ServerLevel) entity.level;
                entity.hurt(DamageSource.indirectMagic(this, this.shootingEntity), 5.5F);

                LightningBolt lightningBoltEntity = new LightningBolt(EntityType.LIGHTNING_BOLT, world);
                lightningBoltEntity.setPos(entity.getX(), entity.getY(), entity.getZ());

                world.addFreshEntity(lightningBoltEntity);
            } else if (result.getType() == HitResult.Type.BLOCK) {
                level.playSound(null, new BlockPos(result.getLocation().x, result.getLocation().y, result.getLocation().z), ModSounds.dark_bolt_hit, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
            this.remove(RemovalReason.DISCARDED);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        Vec3 vec3d = this.getDeltaMovement();
        compound.put("direction", this.newDoubleList(vec3d.x, vec3d.y, vec3d.z));
        compound.put("power", this.newDoubleList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.putInt("life", this.ticksAlive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("power", 9)) {
            ListTag listnbt = compound.getList("power", 6);
            if (listnbt.size() == 3) {
                this.accelerationX = listnbt.getDouble(0);
                this.accelerationY = listnbt.getDouble(1);
                this.accelerationZ = listnbt.getDouble(2);
            }
        }

        this.ticksAlive = compound.getInt("life");
        if (compound.contains("direction", 9) && compound.getList("direction", 6).size() == 3) {
            ListTag listnbt1 = compound.getList("direction", 6);
            this.setDeltaMovement(listnbt1.getDouble(0), listnbt1.getDouble(1), listnbt1.getDouble(2));
        } else {
            this.remove(RemovalReason.DISCARDED);
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
    public boolean hurt(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markHurt();
            if (source.getEntity() != null) {
                Vec3 vec3d = source.getEntity().getLookAngle();
                this.setDeltaMovement(vec3d);
                this.accelerationX = vec3d.x * 0.1D;
                this.accelerationY = vec3d.y * 0.1D;
                this.accelerationZ = vec3d.z * 0.1D;
                if (source.getEntity() instanceof LivingEntity) {
                    this.shootingEntity = (LivingEntity)source.getEntity();
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public float getBrightness() {
        return 1.0F;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}