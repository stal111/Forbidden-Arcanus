package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2022-10-22
 */
public record AddThrownAurealBottleParticle(double x, double y, double z) {

    public static void encode(AddThrownAurealBottleParticle packet, FriendlyByteBuf buffer) {
        buffer.writeDouble(packet.x);
        buffer.writeDouble(packet.y);
        buffer.writeDouble(packet.z);
    }

    public static AddThrownAurealBottleParticle decode(FriendlyByteBuf buffer) {
        return new AddThrownAurealBottleParticle(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
    }

    public static void consume(AddThrownAurealBottleParticle packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleAddThrownAurealBottleParticle(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
