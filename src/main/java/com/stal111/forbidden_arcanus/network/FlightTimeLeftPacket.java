package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.event.RenderGameOverlayListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class FlightTimeLeftPacket {

    private int flightTimeLeft;


    public FlightTimeLeftPacket(PacketBuffer buf) {
    }

    public static void encode(FlightTimeLeftPacket packet, PacketBuffer buffer) {
        buffer.writeInt(packet.flightTimeLeft);
    }

    public static FlightTimeLeftPacket decode(PacketBuffer buffer) {
        int flightTimeLeft = buffer.readInt();
        return new FlightTimeLeftPacket(flightTimeLeft);
    }

    public FlightTimeLeftPacket(int flightTimeLeft) {
        this.flightTimeLeft = flightTimeLeft;
    }

    public static class Handler {
        public static void handle(FlightTimeLeftPacket packet, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                RenderGameOverlayListener.flightTimeLeft = packet.flightTimeLeft;

                ClientPlayerEntity player = Minecraft.getInstance().player;

                if (packet.flightTimeLeft == 0 && !player.abilities.isCreativeMode) {
                    player.abilities.allowFlying = false;
                    player.abilities.isFlying = false;
                } else if (!player.abilities.allowFlying) {
                    player.abilities.allowFlying = true;
                }
            });

            ctx.get().setPacketHandled(true);
        }
    }
}
