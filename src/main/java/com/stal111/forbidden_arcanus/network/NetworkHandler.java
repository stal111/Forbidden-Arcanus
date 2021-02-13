package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int id = 0;

    public static int nextID() {
        return id++;
    }

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ForbiddenArcanus.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(
                nextID(),
                FlightTimeLeftPacket.class,
                FlightTimeLeftPacket::encode,
                FlightTimeLeftPacket::decode,
                FlightTimeLeftPacket.Handler::handle
        );

        INSTANCE.registerMessage(
                nextID(),
                AurealUpdatePacket.class,
                AurealUpdatePacket::encode,
                AurealUpdatePacket::decode,
                AurealUpdatePacket::consume
        );
    }

    public static <MSG> void sendTo(PlayerEntity player, MSG msg) {
        NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), msg);
    }

    public static <MSG> void sendToServer(MSG msg) {
        NetworkHandler.INSTANCE.sendToServer(msg);
    }
}