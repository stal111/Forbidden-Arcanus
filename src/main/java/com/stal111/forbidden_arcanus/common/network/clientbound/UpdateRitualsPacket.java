package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.NamedRitual;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Update Rituals Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateRitualsPacket
 *
 * @author stal111
 * @since 2021-12-21
 */
public record UpdateRitualsPacket(Collection<NamedRitual> rituals) {

    public static void encode(UpdateRitualsPacket packet, FriendlyByteBuf buffer) {
        buffer.writeCollection(packet.rituals, (buf, namedRitual) -> {
            buf.writeResourceLocation(namedRitual.name());
            buffer.writeJsonWithCodec(Ritual.CODEC, namedRitual.get());
        });
    }

    public static UpdateRitualsPacket decode(FriendlyByteBuf buffer) {
        return new UpdateRitualsPacket(buffer.readList(buf -> new NamedRitual(buf.readResourceLocation(), buf.readJsonWithCodec(Ritual.CODEC))));
    }

    public static void consume(UpdateRitualsPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ForbiddenArcanus.INSTANCE.getRitualLoader().setRituals(packet.rituals);
        });
        ctx.get().setPacketHandled(true);
    }
}
