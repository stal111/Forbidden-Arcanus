package com.stal111.forbidden_arcanus.core.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;

/**
 * Slime Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.mixin.SlimeMixin
 *
 * @author stal111
 * @since 2022-01-16
 */
@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob implements Bucketable {

    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Slime.class, EntityDataSerializers.BOOLEAN);

    protected SlimeMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow
    public abstract int getSize();

    @Shadow
    public abstract void setSize(int pSize, boolean pResetHealth);

    @Inject(at = @At(value = "RETURN"), method = "defineSynchedData")
    private void forbiddenArcanus_defineSynchedData(CallbackInfo ci) {
        this.entityData.define(FROM_BUCKET, false);
    }

    @Inject(at = @At(value = "RETURN"), method = "addAdditionalSaveData")
    private void forbiddenArcanus_addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putBoolean("FromBucket", this.fromBucket());
    }

    @Inject(at = @At(value = "RETURN"), method = "readAdditionalSaveData")
    private void forbiddenArcanus_readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.setFromBucket(tag.getBoolean("FromBucket"));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public void saveToBucketTag(@Nonnull ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);

        stack.getOrCreateTag().putInt("Size", this.getSize());
    }

    @Override
    public void loadFromBucketTag(@Nonnull CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        this.setSize(tag.getInt("Size"), false);
    }

    @Nonnull
    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    @Nonnull
    @Override
    public SoundEvent getPickupSound() {
        return null;
    }
}
