package com.stal111.forbidden_arcanus.common.item.bucket;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 05.01.2024
 */
public class SolidCapacityBucketItem extends SolidBucketItem implements CapacityBucket {

    private final BucketFamily family;

    public SolidCapacityBucketItem(Block block, SoundEvent placeSound, BucketFamily family, Properties properties) {
        super(block, placeSound, properties);
        this.family = family;
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (state.is(this.getBlock()) && state.getBlock() instanceof BucketPickup bucketPickup && !this.isFull(stack)) {
            if (player != null) {
                player.setItemInHand(context.getHand(), this.setFullness(stack, this.getFullness(stack) + 1));

                bucketPickup.pickupBlock(player, level, pos, state);
                bucketPickup.getPickupSound(state).ifPresent(event -> player.playSound(event, 1.0F, 1.0F));

                if (player instanceof ServerPlayer serverPlayer) {
                    CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, stack);
                }
            }
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        ItemStack bucket = this.setFullness(stack.copy(), this.getFullness(stack) - 1);

        InteractionResult result = super.useOn(context);

        if (result.consumesAction() && player != null && !player.isCreative()) {
            player.setItemInHand(context.getHand(), bucket);
        }

        return result;
    }

    @Override
    public int getCapacity() {
        return 3;
    }

    @Override
    public BucketFamily getFamily() {
        return this.family;
    }
}
