package com.stal111.forbidden_arcanus.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;

public class NetworkHandler {

//    public static SimpleChannel INSTANCE;

    public static void init() {
//        INSTANCE = ChannelBuilder.named(new ResourceLocation(ForbiddenArcanus.MOD_ID, "channel")).networkProtocolVersion(1).simpleChannel();
//
//       // INSTANCE.messageBuilder(UpdateCounterPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(UpdateCounterPacket::encode).decoder(UpdateCounterPacket::decode).consumerNetworkThread(UpdateCounterPacket::consume).add();
//        INSTANCE.messageBuilder(AddItemParticlePacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(AddItemParticlePacket::encode).decoder(AddItemParticlePacket::decode).consumerNetworkThread(AddItemParticlePacket::consume).add();
//        INSTANCE.messageBuilder(UpdateForgeRitualPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(UpdateForgeRitualPacket::encode).decoder(UpdateForgeRitualPacket::decode).consumerNetworkThread(UpdateForgeRitualPacket::consume).add();
//        INSTANCE.messageBuilder(UpdateItemInSlotPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(UpdateItemInSlotPacket::encode).decoder(UpdateItemInSlotPacket::decode).consumerNetworkThread(UpdateItemInSlotPacket::consume).add();
//        INSTANCE.messageBuilder(AddThrownAurealBottleParticle.class, NetworkDirection.PLAY_TO_CLIENT).encoder(AddThrownAurealBottleParticle::encode).decoder(AddThrownAurealBottleParticle::decode).consumerNetworkThread(AddThrownAurealBottleParticle::consume).add();
//        INSTANCE.messageBuilder(TransformPedestalPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(TransformPedestalPacket::encode).decoder(TransformPedestalPacket::decode).consumerNetworkThread(TransformPedestalPacket::consume).add();
//        INSTANCE.messageBuilder(CreateMagicCirclePacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(CreateMagicCirclePacket::encode).decoder(CreateMagicCirclePacket::decode).consumerNetworkThread(CreateMagicCirclePacket::consume).add();
//        INSTANCE.messageBuilder(RemoveMagicCirclePacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(RemoveMagicCirclePacket::encode).decoder(RemoveMagicCirclePacket::decode).consumerNetworkThread(RemoveMagicCirclePacket::consume).add();
//        INSTANCE.messageBuilder(CreateValidRitualIndicatorPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(CreateValidRitualIndicatorPacket::encode).decoder(CreateValidRitualIndicatorPacket::decode).consumerNetworkThread(CreateValidRitualIndicatorPacket::consume).add();
//        INSTANCE.messageBuilder(RemoveValidRitualIndicatorPacket.class, NetworkDirection.PLAY_TO_CLIENT).encoder(RemoveValidRitualIndicatorPacket::encode).decoder(RemoveValidRitualIndicatorPacket::decode).consumerNetworkThread(RemoveValidRitualIndicatorPacket::consume).add();
    }

    public static <MSG> void sendTo(ServerPlayer player, MSG msg) {
//        NetworkHandler.INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static <MSG> void sendToTrackingChunk(LevelChunk chunk, MSG msg) {
//        NetworkHandler.INSTANCE.send(msg, PacketDistributor.TRACKING_CHUNK.with(chunk));
    }
}