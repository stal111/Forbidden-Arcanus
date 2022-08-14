package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.animation.DrakoDeorumWingsAnimation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticKey;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsCategory;
import net.valhelsia.valhelsia_core.client.cosmetics.CosmeticsManager;

import java.util.Optional;
import java.util.UUID;

/**
 * @author stal111
 * @since 2022-08-13
 */
@Mod.EventBusSubscriber
public class ClientPlayerEvents {

    @SubscribeEvent
    public static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END || event.side == LogicalSide.SERVER) {
            return;
        }

        Player player = event.player;
        UUID uuid = player.getUUID();
        Optional<CosmeticKey> key = CosmeticsManager.getInstance().getActiveCosmetic(uuid, CosmeticsCategory.BACK);

        if (key.isEmpty() || !key.get().name().equalsIgnoreCase("draco_aurum_wings")) {
            return;
        }

        if (player.isFallFlying()) {
            startAnimation(player, DrakoDeorumWingsAnimation.FLY);
        } else if (player.isSprinting() && !player.isCrouching()) {
            startAnimation(player, DrakoDeorumWingsAnimation.RUN);
        } else if (player.isCrouching()) {
                startAnimation(player, DrakoDeorumWingsAnimation.SNEAK);
        } else if (!player.isFallFlying()) {
            startAnimation(player, DrakoDeorumWingsAnimation.WALK);
        }
    }

    private static void startAnimation(Player player, DrakoDeorumWingsAnimation animation) {
        for (DrakoDeorumWingsAnimation otherAnimation : DrakoDeorumWingsAnimation.values()) {
            if (otherAnimation == animation) {
                continue;
            }

            otherAnimation.getAnimationState(player.getUUID()).stop();
        }

        animation.getAnimationState(player.getUUID()).startIfStopped(player.tickCount);
    }
}
