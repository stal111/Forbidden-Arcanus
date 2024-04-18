package com.stal111.forbidden_arcanus.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;

public class NetworkHandler {

//    public static SimpleChannel INSTANCE;

    public static void init() {
//        INSTANCE = ChannelBuilder.named(new ResourceLocation(ForbiddenArcanus.MOD_ID, "channel")).networkProtocolVersion(1).simpleChannel();
//
//        INSTANCE.messageBuilder(UpdateItemInSlotPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(UpdateItemInSlotPacket::encode).decoder(UpdateItemInSlotPacket::decode).consumerNetworkThread(UpdateItemInSlotPacket::consume).add();
//        INSTANCE.messageBuilder(AddThrownAurealBottleParticle.class, NetworkDirection.PLAY_TO_CLIENT).encoder(AddThrownAurealBottleParticle::encode).decoder(AddThrownAurealBottleParticle::decode).consumerNetworkThread(AddThrownAurealBottleParticle::consume).add();
    }

    public static <MSG> void sendTo(ServerPlayer player, MSG msg) {
//        NetworkHandler.INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static <MSG> void sendToTrackingChunk(LevelChunk chunk, MSG msg) {
//        NetworkHandler.INSTANCE.send(msg, PacketDistributor.TRACKING_CHUNK.with(chunk));
    }
}