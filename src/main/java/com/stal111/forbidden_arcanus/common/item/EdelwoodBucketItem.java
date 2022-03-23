package com.stal111.forbidden_arcanus.common.item;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.core.init.ModEnchantments;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Edelwood Bucket Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.EdelwoodBucketItem
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-12-02
 */
public class EdelwoodBucketItem extends BucketItem implements CapacityBucket {

    public static final Map<Item, Supplier<? extends Item>> ITEM_TO_BUCKET = new ImmutableMap.Builder<Item, Supplier<? extends Item>>()
            .put(Items.WATER_BUCKET, ModItems.EDELWOOD_WATER_BUCKET)
            .put(Items.LAVA_BUCKET, ModItems.EDELWOOD_LAVA_BUCKET)
            .put(Items.MILK_BUCKET, ModItems.EDELWOOD_MILK_BUCKET)
            .put(Items.POWDER_SNOW_BUCKET, ModItems.EDELWOOD_POWDER_SNOW_BUCKET)
            .build();
    private static final double BURN_CHANCE = 0.005;

    private final int capacity;

    public EdelwoodBucketItem(Supplier<? extends Fluid> supplier, Properties properties) {
        this(supplier, 0, properties);
    }

    public EdelwoodBucketItem(Supplier<? extends Fluid> supplier, int capacity, Properties properties) {
        super(supplier, properties);
        this.capacity = capacity;
    }

    @Override
    public void inventoryTick(@Nonnull ItemStack stack, @Nonnull Level level, @Nonnull Entity entity, int slot, boolean isSelected) {
        if (this.shouldBurn(stack, level, entity)) {
            if (entity instanceof Player player) {
                player.getInventory().setItem(slot, new ItemStack(Items.CHARCOAL));
            }
            BlockPos pos = entity.blockPosition();

            if (stack.is(ModItems.EDELWOOD_LAVA_BUCKET.get())) {
                level.setBlockAndUpdate(pos, this.getFluid().defaultFluidState().createLegacyBlock());
            } else if (stack.getItem() instanceof EdelwoodMobBucketItem mobBucket) {
                mobBucket.spawn((ServerLevel) level, stack, pos);
            }
        }

        super.inventoryTick(stack, level, entity, slot, isSelected);
    }

    private boolean shouldBurn(ItemStack stack, Level level, Entity entity) {
        if (level.isClientSide() || !this.canBurn(stack) || EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.PERMAFROST.get(), stack) != 0) {
            return false;
        }

        if (entity instanceof Player player && player.getAbilities().instabuild) {
            return false;
        }

        return level.getRandom().nextDouble() < BURN_CHANCE;
    }

    protected boolean canBurn(ItemStack stack) {
        return this.getFluid().is(FluidTags.LAVA);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level, @Nonnull Player player, @Nonnull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, this.getFluid() == Fluids.EMPTY ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onBucketUse(player, level, stack, hitResult);

        if (ret != null) {
            return ret;
        }

        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(stack);
        }

        BlockPos pos = hitResult.getBlockPos();
        Direction direction = hitResult.getDirection();
        BlockPos relativePos = pos.relative(direction);

        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(relativePos, direction, stack)) {
            return InteractionResultHolder.fail(stack);

        }

        BlockState state = level.getBlockState(pos);

        boolean flag = this.getFluid() == level.getBlockState(relativePos).getFluidState().getType();
        boolean isEmptyFluid = this.getFluid().isSame(Fluids.EMPTY);

        if (isEmptyFluid || (flag && !this.isFull(stack))) {
            if (!isEmptyFluid) {
                state = level.getBlockState(relativePos);
                pos = relativePos;
            }

            if (state.getBlock() instanceof BucketPickup bucketPickup) {
                ItemStack filledBucket = bucketPickup.pickupBlock(level, pos, state);

                if (filledBucket.isEmpty() || !ITEM_TO_BUCKET.containsKey(filledBucket.getItem())) {
                    return this.cancelFluidPickup(pos, state, level, stack);
                }

                ItemStack bucket = new ItemStack(ITEM_TO_BUCKET.get(filledBucket.getItem()).get());
                filledBucket = stack.is(bucket.getItem()) ? stack.copy() : ItemStackUtils.transferEnchantments(stack, bucket);


                if (!isEmptyFluid && !this.tryFill(filledBucket).getFirst()) {
                    return this.cancelFluidPickup(pos, state, level, stack);
                }

                filledBucket = ItemUtils.createFilledResult(stack, player, filledBucket);

                player.awardStat(Stats.ITEM_USED.get(this));
                bucketPickup.getPickupSound().ifPresent((event) -> player.playSound(event, 1.0F, 1.0F));
                level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

                if (!level.isClientSide()) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, filledBucket);
                }

                return InteractionResultHolder.sidedSuccess(filledBucket, level.isClientSide());
            }
        } else {
            pos = this.canBlockContainFluid(level, pos, state) ? pos : relativePos;

            if (this.emptyContents(player, level, pos, hitResult)) {
                this.checkExtraContent(player, level, stack, pos);

                if (player instanceof ServerPlayer) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, pos, stack);
                }

                player.awardStat(Stats.ITEM_USED.get(this));

                return InteractionResultHolder.sidedSuccess(this.drainBucket(stack, player), level.isClientSide());
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    private InteractionResultHolder<ItemStack> cancelFluidPickup(BlockPos pos, BlockState state, Level level, ItemStack stack) {
        level.setBlock(pos, state, 11);

        return InteractionResultHolder.fail(stack);
    }

    private ItemStack drainBucket(ItemStack stack, Player player) {
        if (player.getAbilities().instabuild) {
            return stack;
        }
        if (this instanceof EdelwoodMobBucketItem item) {
            stack = this.transferFullness(stack, item.getFluidBucket());
        }

        stack = ((CapacityBucket) stack.getItem()).tryDrain(stack);

        return stack;
    }

    private boolean canBlockContainFluid(Level level, BlockPos pos, BlockState state) {
        return state.getBlock() instanceof LiquidBlockContainer liquidBlockContainer && liquidBlockContainer.canPlaceLiquid(level, pos, state, this.getFluid());
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public ItemStack getEmptyBucket() {
        return new ItemStack(ModItems.EDELWOOD_BUCKET.get());
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return !stack.is(ModItems.EDELWOOD_BUCKET.get());
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_BUCKET.get()));
    }
}
