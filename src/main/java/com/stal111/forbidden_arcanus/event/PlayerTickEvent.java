package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.potion.effect.ModEffects;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class PlayerTickEvent {

    public static List<String> fly = new ArrayList<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        String username = event.player.getDisplayName().toString();
        if (event.player.isPotionActive(ModEffects.fly)) {
            if (!event.player.abilities.isCreativeMode || !event.player.isSpectator()) {
                event.player.abilities.allowFlying = true;
                if (!fly.contains(username))
                    fly.add(username);
            }
        } else if (fly.contains(username)) {
            fly.remove(username);
            event.player.abilities.allowFlying = false;
            event.player.abilities.isFlying = false;
        }
    }
}
