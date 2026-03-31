package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.List;

/**
 * @author stal111
 * @since 2022-09-14
 */
public abstract class AbstractLostSoul extends PathfinderMob {

    public static final EntityDataAccessor<Boolean> DATA_SCARED = SynchedEntityData.defineId(AbstractLostSoul.class, EntityDataSerializers.BOOLEAN);

    private static final Brain.Provider<AbstractLostSoul> BRAIN_PROVIDER = Brain.provider(
            List.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.IS_IN_WATER),
            _ -> LostSoulAi.getActivities()
    );

    private int extractCounter = 0;

    public final AnimationState stillAnimationState = new AnimationState();
    public final AnimationState fearAnimationState = new AnimationState();

    private final int trailColor;

    public AbstractLostSoul(EntityType<? extends AbstractLostSoul> entityType, Level level, int trailColor) {
        super(entityType, level);
        this.trailColor = trailColor;

        this.setPathfindingMalus(PathType.BLOCKED, 16.0F);
        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 16.0F);
        this.setPathfindingMalus(PathType.FIRE, -1.0F);
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
    public Brain<AbstractLostSoul> getBrain() {
        return (Brain<AbstractLostSoul>) super.getBrain();
    }

    @Override
    protected Brain<? extends LivingEntity> makeBrain(Brain.Packed packedBrain) {
        return BRAIN_PROVIDER.makeBrain(this, packedBrain);
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
    public boolean causeFallDamage(double fallDistance, float multiplier, DamageSource source) {
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
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Scared", this.entityData.get(DATA_SCARED));
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(DATA_SCARED, input.getBooleanOr("Scared", false));
    }

    public boolean isScared() {
        return this.entityData.get(DATA_SCARED);
    }
}
