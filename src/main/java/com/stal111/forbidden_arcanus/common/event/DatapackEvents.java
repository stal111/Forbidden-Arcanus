package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.loader.HephaestusForgeInputLoader;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.network.ClientboundUpdateRitualsPacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Datapack Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.DatapackEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-21
 */
@Mod.EventBusSubscriber
public class DatapackEvents {

    @SubscribeEvent
    public static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new HephaestusForgeInputLoader());
        event.addListener(new RitualLoader());
    }

    @SubscribeEvent
    public static void onDatapackSync(OnDatapackSyncEvent event) {
        if (event.getPlayer() != null) {
            syncData(event.getPlayer());
        } else {
            event.getPlayerList().getPlayers().forEach(DatapackEvents::syncData);
        }
    }

    private static void syncData(ServerPlayer player) {
        NetworkHandler.sendTo(player, new ClientboundUpdateRitualsPacket(RitualLoader.rituals));
    }
}
