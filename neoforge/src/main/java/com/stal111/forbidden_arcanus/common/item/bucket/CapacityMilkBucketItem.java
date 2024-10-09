package com.stal111.forbidden_arcanus.common.item.bucket;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 16.12.2023
 */
public class CapacityMilkBucketItem extends MilkBucketItem implements CapacityBucket {

    private final BucketFamily family;

    public CapacityMilkBucketItem(BucketFamily family, Properties properties) {
        super(properties);
        this.family = family;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack stack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand hand) {
        return tryMilk(stack, player, livingEntity, hand, this);
    }

    public static InteractionResult tryMilk(ItemStack stack, Player player, LivingEntity entity, InteractionHand hand, CapacityBucket bucket) {
        if (bucket.isFull(stack) || entity.isBaby()) {
            return InteractionResult.PASS;
        }

        if (entity instanceof Cow || entity instanceof Goat) {
            player.playSound(getMilkingSound(entity), 1.0F, 1.0F);

            boolean isClient = player.level().isClientSide();

            if (!isClient) {
                ItemStack result = stack.is(bucket.getEmptyBucket())
                        ? ItemUtils.createFilledResult(stack, player, new ItemStack(bucket.getMilkBucket()))
                        : bucket.setFullness(stack, bucket.getFullness(stack) + 1);

                player.setItemInHand(hand, result);
            }

            return InteractionResult.sidedSuccess(isClient);
        }

        return InteractionResult.PASS;
    }

    public static SoundEvent getMilkingSound(LivingEntity entity) {
        if (entity instanceof Goat goat) {
            return goat.isScreamingGoat() ? SoundEvents.GOAT_SCREAMING_MILK : SoundEvents.GOAT_MILK;
        }

        return SoundEvents.COW_MILK;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        super.finishUsingItem(stack.copy(), level, livingEntity);

        return this.setFullness(stack, this.getFullness(stack) - 1);
    }

    @Override
    public int getCapacity() {
        return 4;
    }

    @Override
    public BucketFamily getFamily() {
        return this.family;
    }
}
