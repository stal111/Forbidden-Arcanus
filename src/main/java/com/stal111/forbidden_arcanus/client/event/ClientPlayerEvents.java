package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.animation.DracoAurumWingsAnimation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.common.Mod;

/**
 * @author stal111
 * @since 2022-08-13
 */
@Mod.EventBusSubscriber
public class ClientPlayerEvents {

//    @SubscribeEvent
//    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
//        if (event.phase == TickEvent.Phase.END || event.side == LogicalSide.SERVER) {
//            return;
//        }
//
//        Player player = event.player;
//        UUID uuid = player.getUUID();
//        Optional<CosmeticKey> key = CosmeticsManager.getInstance().getActiveCosmetic(uuid, CosmeticsCategory.BACK);
//
//        if (key.isEmpty() || !key.get().name().equalsIgnoreCase("draco_aurum_wings")) {
//            return;
//        }
//
//        if (player.isFallFlying()) {
//            startAnimation(player, DracoAurumWingsAnimation.FLY);
//        } else if (player.isSprinting() && !player.isCrouching()) {
//            startAnimation(player, DracoAurumWingsAnimation.RUN);
//        } else if (player.isCrouching()) {
//                startAnimation(player, DracoAurumWingsAnimation.SNEAK);
//        } else if (!player.isFallFlying()) {
//            startAnimation(player, DracoAurumWingsAnimation.WALK);
//        }
//    }

    private static void startAnimation(Player player, DracoAurumWingsAnimation animation) {
        for (DracoAurumWingsAnimation otherAnimation : DracoAurumWingsAnimation.values()) {
            if (otherAnimation == animation) {
                continue;
            }

            otherAnimation.getAnimationState(player.getUUID()).stop();
        }

        animation.getAnimationState(player.getUUID()).startIfStopped(player.tickCount);
    }
}
