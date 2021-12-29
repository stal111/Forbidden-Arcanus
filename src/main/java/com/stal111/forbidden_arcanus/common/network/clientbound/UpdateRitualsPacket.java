package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Update Rituals Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateRitualsPacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-12-21
 */
public record UpdateRitualsPacket(Map<ResourceLocation, Ritual> rituals) {

    public static void encode(UpdateRitualsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeMap(packet.rituals, FriendlyByteBuf::writeResourceLocation, (friendlyByteBuf, ritual) -> ritual.serializeToNetwork(friendlyByteBuf));
    }

    public static UpdateRitualsPacket decode(FriendlyByteBuf buffer) {
        return new UpdateRitualsPacket(buffer.readMap(FriendlyByteBuf::readResourceLocation, Ritual::fromNetwork));
    }

    public static void consume(UpdateRitualsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            RitualLoader.setRituals(packet.rituals);
        });
        ctx.get().setPacketHandled(true);
    }
}
