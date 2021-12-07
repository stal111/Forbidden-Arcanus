package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.CapacityBucket;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Player Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.PlayerEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-28
 */
@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;

            if (player.onClimbable() && !player.isCrouching() && player.getFeetBlockState().is(NewModBlocks.EDELWOOD_LADDER.get())) {
                if (player.zza > 0.0f) {
                    player.move(MoverType.SELF, new Vec3(0.0, BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                } else {
                    player.move(MoverType.SELF, new Vec3(0.0, -BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        Entity entity = event.getTarget();
        InteractionHand hand = event.getHand();

        if (entity instanceof Cow cow && !cow.isBaby()) {
            if (stack.getItem() instanceof CapacityBucket capacityBucket) {
                ItemStack copy = stack.copy();

                ItemStack result = null;

                if (stack.is(NewModItems.EDELWOOD_MILK_BUCKET.get()) && !capacityBucket.isFull(stack)) {
                    result = capacityBucket.tryFill(ItemUtils.createFilledResult(copy, player, stack)).getSecond();
                } else if (stack.is(capacityBucket.getEmptyBucket().getItem())) {
                    result = ItemUtils.createFilledResult(copy, player, new ItemStack(NewModItems.EDELWOOD_MILK_BUCKET.get()));
                }

                if (result != null) {
                    player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
                    player.setItemInHand(hand, result);
                    player.swing(hand);
                }
            }
        }
    }
}
