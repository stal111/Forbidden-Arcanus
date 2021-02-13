package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.network.AurealUpdatePacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

/**
 * Baby Entity Spawn Listener
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.BabyEntitySpawnListener
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-30
 */
@Mod.EventBusSubscriber
public class BabyEntitySpawnListener {

    @SubscribeEvent
    public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
        if (event.getCausedByPlayer() != null) {
            PlayerEntity player = event.getCausedByPlayer();

            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                if (aureal.getCorruption() != 0) {
                    if (new Random().nextDouble() <= 0.45) {
                        aureal.setCorruption(aureal.getCorruption() - 1);
                        NetworkHandler.sendTo(player, new AurealUpdatePacket(player));
                    }
                }
            });
        }
    }
}
