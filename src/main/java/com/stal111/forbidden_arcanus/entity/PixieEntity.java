package com.stal111.forbidden_arcanus.entity;

import com.google.common.collect.Sets;
import com.stal111.forbidden_arcanus.entity.ai.FlyingAvoidEntityGoal;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Set;

public class PixieEntity extends TameableEntity implements IFlyingAnimal {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(ParrotEntity.class, DataSerializers.VARINT);
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(ModItems.ARCANE_CRYSTAL_DUST.get(), ModItems.SOUL.get());

    private int underWaterTicks;

    public PixieEntity(EntityType<Entity> type, World world) {
        super((EntityType<? extends TameableEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 15, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    protected PixieEntity(World world) {
        super((EntityType<? extends TameableEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 15, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public PixieEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super((EntityType<? extends TameableEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 15, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new FlyingAvoidEntityGoal<>(this, SkeletonEntity.class, 15.0F, 1.3D, 1.7D));
        this.goalSelector.addGoal(2, new FlyingAvoidEntityGoal<>(this, PlayerEntity.class, 15.0F, 1.3D, 1.7D));
        this.goalSelector.addGoal(3, new PixieEntity.WanderGoal());
    }

    @Override
    protected void updateAITasks() {
        if (this.isInWaterOrBubbleColumn()) {
            ++this.underWaterTicks;
        } else {
            this.underWaterTicks = 0;
        }

        if (this.underWaterTicks > 20) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setMotion(this.getMotion().mul(1.0D, 0.6D, 1.0D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().func_233815_a_(Attributes.MAX_HEALTH, 4.0D).func_233815_a_(Attributes.FLYING_SPEED, 0.6F).func_233815_a_(Attributes.MOVEMENT_SPEED, 0.3F);
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntitySize size) {
        return size.height * 0.6F;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
    }

    @Override
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() instanceof SpawnEggItem) {
            return super.func_230254_b_(player, hand);
        } else if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote) {
                if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.setTamedBy(player);
                    this.world.setEntityState(this, (byte) 7);
                } else {
                    this.world.setEntityState(this, (byte) 6);
                }
            }

            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else if (getVariant() == 0 && itemstack.getItem() == ModItems.DARK_SOUL.get()) {
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }

            if (rand.nextDouble() <= 0.1D) {
                setVariant(1);
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else if (this.isTamed() && player.isCrouching() && player.getUniqueID() == getOwnerId()) {
            if (!this.world.isRemote()) {
                this.remove();

                ItemStack stack = getVariant() == 0 ? new ItemStack(ModItems.PIXIE.get()) : new ItemStack(ModItems.CORRUPTED_PIXIE.get());

                if (!player.addItemStackToInventory(stack)) {
                    player.dropItem(stack, false);
                }
            }
            return ActionResultType.func_233537_a_(this.world.isRemote);
        } else {
            return super.func_230254_b_(player, hand);
        }
    }

    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn) {
            public boolean canEntityStandOnPos(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanSwim(false);
        flyingpathnavigator.setCanEnterDoors(true);
        return flyingpathnavigator;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataManager.get(VARIANT), 0, 1);
    }

    public void setVariant(int variant) {
        this.dataManager.set(VARIANT, variant);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(VARIANT, 0);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    class WanderGoal extends Goal {
        WanderGoal() {
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return PixieEntity.this.navigator.noPath() && PixieEntity.this.rand.nextInt(10) == 0;
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return PixieEntity.this.navigator.func_226337_n_();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Vector3d vector3d = this.getRandomLocation();
            if (vector3d != null) {
                PixieEntity.this.navigator.setPath(PixieEntity.this.navigator.getPathToPos(new BlockPos(vector3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vector3d getRandomLocation() {
            Vector3d vector3d = PixieEntity.this.getLook(0.0F);

            Vector3d vector3d2 = RandomPositionGenerator.findAirTarget(PixieEntity.this, 8, 7, vector3d, ((float)Math.PI / 2F), 2, 1);
            return vector3d2 != null ? vector3d2 : RandomPositionGenerator.findGroundTarget(PixieEntity.this, 8, 4, -2, vector3d, (double)((float)Math.PI / 2F));
        }
    }
}
