package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.mojang.serialization.Dynamic;
import com.stal111.forbidden_arcanus.common.entity.QuantumLightDoorAnimationProvider;
import com.stal111.forbidden_arcanus.core.init.other.ModEntityDataSerializers;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTrader extends Mob implements QuantumLightDoorAnimationProvider {

    private static final EntityDataAccessor<Holder<DarkTraderVariant>> DATA_VARIANT_ID = SynchedEntityData.defineId(DarkTrader.class, ModEntityDataSerializers.DARK_TRADER_VARIANT.get());

    public final AnimationState portalAnimationState = new AnimationState();
    public final AnimationState spawnAnimationState = new AnimationState();

    public DarkTrader(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 14.0D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, DarkTraderVariant.BROOK);
    }

    @Override
    protected @NotNull Brain<?> makeBrain(@NotNull Dynamic<?> dynamic) {
        return DarkTraderAI.makeBrain(this, dynamic);
    }

    @Override
    public @NotNull Brain<DarkTrader> getBrain() {
        return (Brain<DarkTrader>) super.getBrain();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull EntitySpawnReason reason, @Nullable SpawnGroupData spawnData) {
        if (reason == EntitySpawnReason.MOB_SUMMONED) {
            this.setPose(Pose.EMERGING);
            this.getBrain().setMemoryWithExpiry(MemoryModuleType.IS_EMERGING, Unit.INSTANCE, DarkTraderAI.SPAWN_DURATION);
        }

        return super.finalizeSpawn(level, difficulty, reason, spawnData);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide()) {
            this.spawnAnimationState.animateWhen(this.getPose() == Pose.EMERGING && this.portalAnimationState.getTimeInMillis(this.tickCount) > 600, this.tickCount);
        }
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        if (DATA_POSE.equals(key)) {
            if (this.getPose() == Pose.EMERGING) {
                this.portalAnimationState.start(this.tickCount);
            }
        }
        super.onSyncedDataUpdated(key);
    }

    private Holder<DarkTraderVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<DarkTraderVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
    }

    @Override
    protected void customServerAiStep(@NotNull ServerLevel level) {
        ProfilerFiller profilerFiller = Profiler.get();

        profilerFiller.push("darkTraderBrain");
        this.getBrain().tick((ServerLevel) this.level(), this);
        profilerFiller.pop();

        profilerFiller.push("darkTraderActivityUpdate");
        DarkTraderAI.updateActivity(this);
        profilerFiller.pop();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        VariantUtils.writeVariant(output, this.getVariant());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);

        VariantUtils.readVariant(input, FARegistries.DARK_TRADER_VARIANT).ifPresent(this::setVariant);
    }

    @Override
    public AnimationState getAnimationState() {
        return this.portalAnimationState;
    }
}
