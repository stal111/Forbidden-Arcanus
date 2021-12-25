package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import net.valhelsia.valhelsia_core.common.network.UpdateCounterPacket;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ForbiddenArcanus.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), ClientboundUpdateAurealPacket.class, ClientboundUpdateAurealPacket::encode, ClientboundUpdateAurealPacket::decode, ClientboundUpdateAurealPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateCounterPacket.class, UpdateCounterPacket::encode, UpdateCounterPacket::decode, UpdateCounterPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdatePedestalPacket.class, UpdatePedestalPacket::encode, UpdatePedestalPacket::decode, UpdatePedestalPacket::consume);
        INSTANCE.registerMessage(nextID(), ItemParticlePacket.class, ItemParticlePacket::encode, ItemParticlePacket::decode, ItemParticlePacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateRitualPacket.class, UpdateRitualPacket::encode, UpdateRitualPacket::decode, UpdateRitualPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateItemInSlotPacket.class, UpdateItemInSlotPacket::encode, UpdateItemInSlotPacket::decode, UpdateItemInSlotPacket::consume);
        INSTANCE.registerMessage(nextID(), ClientboundUpdateRitualsPacket.class, ClientboundUpdateRitualsPacket::encode, ClientboundUpdateRitualsPacket::decode, ClientboundUpdateRitualsPacket::consume);
    }

    public static <MSG> void sendTo(Player player, MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        NetworkHandler.INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sentToTrackingChunk(LevelChunk chunk, MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
    }
}