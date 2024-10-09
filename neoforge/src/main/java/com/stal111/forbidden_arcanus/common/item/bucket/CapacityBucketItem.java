package com.stal111.forbidden_arcanus.common.item.bucket;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 14.12.2023
 */
public class CapacityBucketItem extends BucketItem implements CapacityFluidBucket {

    private static final double BURN_CHANCE = 0.005;

    private final Fluid fluid;
    private final int capacity;
    private final BucketFamily family;

    public CapacityBucketItem(Fluid fluid, int capacity, BucketFamily family, Properties builder) {
        super(fluid, builder);
        this.fluid = fluid;
        this.capacity = capacity;
        this.family = family;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand hand) {
        return CapacityMilkBucketItem.tryMilk(stack.copy(), player, livingEntity, hand, this);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean isSelected) {
        if (this.getFluid().isSame(Fluids.LAVA) && level.getRandom().nextDouble() < BURN_CHANCE) {
            if (entity instanceof Player player && !player.getAbilities().instabuild) {
                player.getInventory().setItem(slot, new ItemStack(Items.CHARCOAL));
            }

            level.setBlockAndUpdate(entity.blockPosition(), this.getFluid().defaultFluidState().createLegacyBlock());
        }
        super.inventoryTick(stack, level, entity, slot, isSelected);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, this.getFluid().isSame(Fluids.EMPTY) || !this.isFull(stack) ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        BlockPos relativePos = pos.relative(direction);

        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(relativePos, direction, stack)) {
            return InteractionResultHolder.fail(stack);
        }

        IFluidHandlerItem fluidHandlerItem = FluidUtil.getFluidHandler(stack.copyWithCount(1)).orElseThrow(() -> new IllegalStateException("CapacityBucketItem did not have a fluid handler capability!"));

        BlockState state = level.getBlockState(pos);
        FluidState fluid = level.getFluidState(pos);

        boolean isEmpty = this.getFluid().isSame(Fluids.EMPTY);

        if ((isEmpty || !this.isFull(stack)) && state.getBlock() instanceof BucketPickup bucketPickup && (!fluid.isEmpty() || bucketPickup instanceof PowderSnowBlock)) {
            ItemStack filledBucket = this.fillBucket(fluidHandlerItem, fluid, bucketPickup, player, level, pos, state);

            return InteractionResultHolder.sidedSuccess(ItemUtils.createFilledResult(stack, player, filledBucket), level.isClientSide());
        }

        BlockPos placePos = this.canBlockContainFluid(player, level, pos, state) ? pos : relativePos;

        if (this.emptyContents(player, level, placePos, hitResult, stack)) {
            this.checkExtraContent(player, level, stack, placePos);

            fluidHandlerItem.drain(FluidType.BUCKET_VOLUME, IFluidHandlerItem.FluidAction.EXECUTE);

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayer, placePos, stack);
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            ItemStack result = player.getAbilities().instabuild ? stack : fluidHandlerItem.getContainer();

            return InteractionResultHolder.sidedSuccess(result, level.isClientSide());
        }

        return InteractionResultHolder.fail(stack);
    }

    private ItemStack fillBucket(IFluidHandlerItem fluidHandlerItem, FluidState fluid, BucketPickup bucketPickup, Player player, Level level, BlockPos pos, BlockState state) {
        ItemStack filledBucket;

        bucketPickup.pickupBlock(player, level, pos, state);

        if (!fluid.isEmpty() && fluidHandlerItem.isFluidValid(0, new FluidStack(fluid.getType(), FluidType.BUCKET_VOLUME))) {
            fluidHandlerItem.fill(new FluidStack(fluid.getType(), FluidType.BUCKET_VOLUME), IFluidHandlerItem.FluidAction.EXECUTE);

            filledBucket = fluidHandlerItem.getContainer().copy();
        } else {
            filledBucket = new ItemStack(this.family.powderSnowBucket().get());
        }

        bucketPickup.getPickupSound(state).ifPresent(soundEvent -> player.playSound(soundEvent, 1.0F, 1.0F));

        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, filledBucket);
        }

        return filledBucket;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public BucketFamily getFamily() {
        return this.family;
    }

    @Override
    public Fluid getFluid() {
        return this.fluid;
    }
}
