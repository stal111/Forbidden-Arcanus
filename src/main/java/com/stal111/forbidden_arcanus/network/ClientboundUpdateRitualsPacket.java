package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Clientbound Update Rituals Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.ClientboundUpdateRitualsPacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-12-21
 */
public record ClientboundUpdateRitualsPacket(Map<ResourceLocation, Ritual> rituals) {

    public static void encode(ClientboundUpdateRitualsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeMap(packet.rituals, FriendlyByteBuf::writeResourceLocation, (friendlyByteBuf, ritual) -> ritual.serializeToNetwork(friendlyByteBuf));
    }

    public static ClientboundUpdateRitualsPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundUpdateRitualsPacket(buffer.readMap(FriendlyByteBuf::readResourceLocation, Ritual::fromNetwork));
    }

    public static void consume(ClientboundUpdateRitualsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            RitualLoader.setRituals(packet.rituals);
        });
        ctx.get().setPacketHandled(true);
    }
}
