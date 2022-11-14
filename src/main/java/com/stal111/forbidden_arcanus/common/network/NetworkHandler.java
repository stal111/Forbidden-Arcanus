package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.network.clientbound.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.valhelsia.valhelsia_core.common.network.UpdateCounterPacket;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ForbiddenArcanus.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), UpdateAurealPacket.class, UpdateAurealPacket::encode, UpdateAurealPacket::decode, UpdateAurealPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateCounterPacket.class, UpdateCounterPacket::encode, UpdateCounterPacket::decode, UpdateCounterPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdatePedestalPacket.class, UpdatePedestalPacket::encode, UpdatePedestalPacket::decode, UpdatePedestalPacket::consume);
        INSTANCE.registerMessage(nextID(), AddItemParticlePacket.class, AddItemParticlePacket::encode, AddItemParticlePacket::decode, AddItemParticlePacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateForgeRitualPacket.class, UpdateForgeRitualPacket::encode, UpdateForgeRitualPacket::decode, UpdateForgeRitualPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateItemInSlotPacket.class, UpdateItemInSlotPacket::encode, UpdateItemInSlotPacket::decode, UpdateItemInSlotPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateRitualsPacket.class, UpdateRitualsPacket::encode, UpdateRitualsPacket::decode, UpdateRitualsPacket::consume);
        INSTANCE.registerMessage(nextID(), UpdateForgeInputsPacket.class, UpdateForgeInputsPacket::encode, UpdateForgeInputsPacket::decode, UpdateForgeInputsPacket::consume);
        INSTANCE.registerMessage(nextID(), AddThrownAurealBottleParticle.class, AddThrownAurealBottleParticle::encode, AddThrownAurealBottleParticle::decode, AddThrownAurealBottleParticle::consume);
        INSTANCE.registerMessage(nextID(), UpdateResidueTypesPacket.class, UpdateResidueTypesPacket::encode, UpdateResidueTypesPacket::decode, UpdateResidueTypesPacket::consume);
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