package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.config.AurealConfig;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Baby Entity Spawn Listener <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.BabyEntitySpawnListener
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-01-30
 */
@Mod.EventBusSubscriber
public class BabyEntitySpawnListener {

    @SubscribeEvent
    public static void onBabyEntitySpawn(BabyEntitySpawnEvent event) {
        PlayerEntity player = event.getCausedByPlayer();

        if (player == null) {
            return;
        }

        IAureal aureal = AurealHelper.getCapability(player);

        if (aureal.getCorruption() != 0 && player.getRNG().nextDouble() < AurealConfig.BREEDING_DECREASEMENT_CHANCE.get()) {
            aureal.decreaseCorruption(1);

            AurealHelper.sendAurealUpdatePacket(player);
        }
    }
}
