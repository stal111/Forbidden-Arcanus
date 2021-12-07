package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.init.NewModItems;
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

import javax.annotation.Nonnull;

/**
 * Solid Edelwood Bucket Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.SolidEdelwoodBucketItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-07
 */
public class SolidEdelwoodBucketItem extends SolidBucketItem implements CapacityBucket {

    public SolidEdelwoodBucketItem(Block block, SoundEvent placeSound, Properties properties) {
        super(block, placeSound, properties);
    }

    @Nonnull
    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        ItemStack stack = context.getItemInHand().copy();
        BlockState state = level.getBlockState(pos);

        if (state.is(this.getBlock()) && state.getBlock() instanceof BucketPickup bucketPickup && !this.isFull(stack)) {
            if (player != null) {
                player.setItemInHand(context.getHand(), this.tryFill(stack).getSecond());

                bucketPickup.pickupBlock(level, pos, state);
                bucketPickup.getPickupSound().ifPresent((event) -> player.playSound(event, 1.0F, 1.0F));

                if (!level.isClientSide()) {
                    CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer) player, stack);
                }
            }
            level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        InteractionResult result = super.useOn(context);

        if (result.consumesAction() && player != null && !player.isCreative()) {
            player.setItemInHand(context.getHand(), this.tryDrain(stack));
        }

        return result;
    }

    @Override
    public int getCapacity() {
        return 3;
    }

    @Override
    public ItemStack getEmptyBucket() {
        return new ItemStack(NewModItems.EDELWOOD_BUCKET.get());
    }
}
