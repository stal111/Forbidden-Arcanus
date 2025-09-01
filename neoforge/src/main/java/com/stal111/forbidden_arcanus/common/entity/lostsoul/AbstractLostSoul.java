package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import com.stal111.forbidden_arcanus.core.init.ModMemoryModules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;

/**
 * @author stal111
 * @since 2022-09-14
 */
public abstract class AbstractLostSoul extends PathfinderMob {

    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.IS_IN_WATER, MemoryModuleType.IS_PANICKING, ModMemoryModules.SCARED_TIME.get());
    protected static final ImmutableList<SensorType<? extends Sensor<? super AbstractLostSoul>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.IS_IN_WATER);

    public static final EntityDataAccessor<Boolean> DATA_SCARED = SynchedEntityData.defineId(AbstractLostSoul.class, EntityDataSerializers.BOOLEAN);

    private int extractCounter = 0;

    public final AnimationState stillAnimationState = new AnimationState();
    public final AnimationState fearAnimationState = new AnimationState();

    private final int trailColor;

    public AbstractLostSoul(EntityType<? extends AbstractLostSoul> entityType, Level level, int trailColor) {
        super(entityType, level);
        this.trailColor = trailColor;

        this.setPathfindingMalus(PathType.BLOCKED, 16.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.moveControl = new FlyingMoveControl(this, 15, true);

        this.noPhysics = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.FLYING_SPEED, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public static boolean canSpawn(EntityType<? extends AbstractLostSoul> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return true;
    }

    @Override
    public void knockback(double strength, double x, double z) {
    }

    @Override
    protected Brain.Provider<AbstractLostSoul> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return LostSoulAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<AbstractLostSoul> getBrain() {
        return (Brain<AbstractLostSoul>) super.getBrain();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos).isAir() ? 35.0F : 0.0F;
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);

        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(true);
        navigation.setCanPassDoors(true);

        return navigation;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SCARED, false);
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()) {
            if (this.extractCounter != 0) {
                this.extractCounter--;
            }

            if (this.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY_ENTITY) && !this.isScared()) {
                this.entityData.set(DATA_SCARED, true);

                this.setPathfindingMalus(PathType.BLOCKED, 0.0F);

                this.getBrain().setActiveActivityIfPossible(Activity.PANIC);
            }
        } else if (this.level().getGameTime() % 10 == 0) {
            Vec3 viewVector = this.calculateViewVector(this.getXRot(), this.getYRot());

            this.level().addParticle(new DustParticleOptions(this.trailColor, 1.0F), this.getX() - viewVector.x * 0.5D, this.getY() + 0.2D, this.getZ() - viewVector.z * 0.5D, 0.0F, 0.0F, 0.0F);
        }

        super.tick();
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (key.equals(DATA_SCARED)) {
            if (this.isScared()) {
                this.fearAnimationState.startIfStopped(this.tickCount);
                this.stillAnimationState.stop();
            } else {
                this.stillAnimationState.startIfStopped(this.tickCount);
                this.fearAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        ProfilerFiller profilerFiller = Profiler.get();

        profilerFiller.push("lostSoulBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        profilerFiller.pop();

        super.customServerAiStep(level);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Scared", this.entityData.get(DATA_SCARED));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(DATA_SCARED, tag.getBoolean("Scared"));
    }

    public boolean isScared() {
        return this.entityData.get(DATA_SCARED);
    }
}
