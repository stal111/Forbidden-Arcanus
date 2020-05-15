package com.stal111.forbidden_arcanus.entity;

import com.stal111.forbidden_arcanus.init.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.FMLPlayMessages;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class PixieEntity extends AnimalEntity implements IFlyingAnimal {

    private int underWaterTicks;

    public PixieEntity(EntityType<Entity> type, World world) {
        super((EntityType<? extends AnimalEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 20, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    protected PixieEntity(World world) {
        super((EntityType<? extends AnimalEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 20, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public PixieEntity(FMLPlayMessages.SpawnEntity spawnEntity, World world) {
        super((EntityType<? extends AnimalEntity>) ModEntities.PIXIE.get(), world);
        this.moveController = new FlyingMovementController(this, 20, true);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.COCOA, -1.0F);
        this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        return worldIn.getBlockState(pos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PixieEntity.WanderGoal());
        this.goalSelector.addGoal(1, new SwimGoal(this));
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

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6F);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3F);
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
    protected float getStandingEyeHeight(Pose pose, EntitySize sizeIn) {
        return sizeIn.height / 2.0F;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        return null;
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
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                PixieEntity.this.navigator.setPath(PixieEntity.this.navigator.getPathToPos(new BlockPos(vec3d), 1), 1.0D);
            }

        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d = PixieEntity.this.getLook(0.0F);

            int i = 8;
            Vec3d vec3d2 = RandomPositionGenerator.findAirTarget(PixieEntity.this, 8, 7, vec3d, ((float)Math.PI / 2F), 2, 1);
            return vec3d2 != null ? vec3d2 : RandomPositionGenerator.findGroundTarget(PixieEntity.this, 8, 4, -2, vec3d, (double)((float)Math.PI / 2F));
        }
    }
}
