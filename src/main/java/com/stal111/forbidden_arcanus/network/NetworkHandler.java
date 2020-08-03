package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {

    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MOD_ID, "channel"), () -> "1.0", s -> true, s -> true);

        INSTANCE.registerMessage(nextID(), FlightTimeLeftPacket.class, FlightTimeLeftPacket::encode, FlightTimeLeftPacket::decode, FlightTimeLeftPacket.Handler::handle);
    }
}