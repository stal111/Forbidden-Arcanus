package com.stal111.forbidden_arcanus.entity;

import com.google.common.collect.Sets;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.RandomPos;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class PixieEntity extends TamableAnimal implements FlyingAnimal {

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Parrot.class, EntityDataSerializers.INT);
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(ModItems.ARCANE_CRYSTAL_DUST.get(), ModItems.SOUL.get());

    private int underWaterTicks;

    public PixieEntity(EntityType<? extends Entity> entityType, Level world) {
        super((EntityType<? extends TamableAnimal>) entityType, world);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public PixieEntity(Level world) {
        super((EntityType<? extends TamableAnimal>) ModEntities.PIXIE.get(), world);
        this.moveControl = new FlyingMoveControl(this, 15, true);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    public PixieEntity(FMLPlayMessages.SpawnEntity spawnEntity, Level world) {
        super((EntityType<? extends TamableAnimal>) ModEntities.PIXIE.get(), world);
        this.moveControl = new FlyingMoveControl(this, 15, true);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }


    public float getWalkTargetValue(BlockPos pos, LevelReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new FloatGoal(this));
       // this.goalSelector.addGoal(2, new FlyingAvoidEntityGoal<>(this, Skeleton.class, 15.0F, 1.3D, 1.7D));
       // this.goalSelector.addGoal(2, new FlyingAvoidEntityGoal<>(this, Player.class, 15.0F, 1.3D, 1.7D));
        this.goalSelector.addGoal(3, new PixieEntity.WanderGoal());
    }

    @Override
    protected void customServerAiStep() {
        if (this.isInWaterOrBubble()) {
            ++this.underWaterTicks;
        } else {
            this.underWaterTicks = 0;
        }

        if (this.underWaterTicks > 20) {
            this.hurt(DamageSource.DROWN, 1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.FLYING_SPEED, 0.6F).add(Attributes.MOVEMENT_SPEED, 0.3F);
    }


    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return size.height * 0.6F;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.mobInteract(player, hand);
        } else if (!this.isTame() && TAME_ITEMS.contains(itemstack.getItem())) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (!this.level.isClientSide) {
                if (this.random.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.level.broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte) 6);
                }
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (getVariant() == 0 && itemstack.getItem() == ModItems.DARK_SOUL.get()) {
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }

            if (random.nextDouble() <= 0.1D) {
                setVariant(1);
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if (this.isTame() && player.isCrouching() && player.getUUID() == getOwnerUUID()) {
            if (!this.level.isClientSide()) {
                this.remove(RemovalReason.DISCARDED);

                ItemStack stack = getVariant() == 0 ? new ItemStack(ModItems.PIXIE.get()) : new ItemStack(ModItems.CORRUPTED_PIXIE.get());

                if (!player.addItem(stack)) {
                    player.drop(stack, false);
                }
            }
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else {
            return super.mobInteract(player, hand);
        }
    }

    protected PathNavigation createNavigation(Level worldIn) {
        FlyingPathNavigation flyingpathnavigator = new FlyingPathNavigation(this, worldIn) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(false);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 0, 1);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean canUse() {
            return PixieEntity.this.navigation.isDone() && PixieEntity.this.random.nextInt(10) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean canContinueToUse() {
            return PixieEntity.this.navigation.isInProgress();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            Vec3 vector3d = this.getRandomLocation();
            if (vector3d != null) {
                PixieEntity.this.navigation.moveTo(PixieEntity.this.navigation.createPath(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3 getRandomLocation() {
            Vec3 vector3d = PixieEntity.this.getViewVector(0.0F);

            Vec3 vector3d2 = RandomPos.generateRandomPos(PixieEntity.this, PixieEntity.this::getOnPos);
            return vector3d2;
        }
    }
}
