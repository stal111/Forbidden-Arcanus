package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Update Forge Ritual Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateForgeRitualPacket
 *
 * @author stal111
 * @since 2021-07-17
 */
public record UpdateForgeRitualPacket(BlockPos pos, Optional<ResourceLocation> ritual) {

    public static void encode(UpdateForgeRitualPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeOptional(packet.ritual, (buf, resourceLocation) -> buffer.writeResourceLocation(resourceLocation));
    }

    public static UpdateForgeRitualPacket decode(FriendlyByteBuf buffer) {
        return new UpdateForgeRitualPacket(buffer.readBlockPos(), buffer.readOptional(FriendlyByteBuf::readResourceLocation));
    }

    public static void consume(UpdateForgeRitualPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleUpdateRitual(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
