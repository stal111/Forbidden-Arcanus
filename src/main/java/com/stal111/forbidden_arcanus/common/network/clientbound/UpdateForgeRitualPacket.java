package com.stal111.forbidden_arcanus.common.network.clientbound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Update Forge Ritual Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateForgeRitualPacket
 *
 * @author stal111
 * @since 2021-07-17
 */
public record UpdateForgeRitualPacket(BlockPos pos, @Nullable ResourceLocation ritual) {

    public static void encode(UpdateForgeRitualPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeOptional(Optional.ofNullable(packet.ritual), (buf, location) -> buffer.writeResourceLocation(location));
    }

    public static UpdateForgeRitualPacket decode(FriendlyByteBuf buffer) {
        return new UpdateForgeRitualPacket(buffer.readBlockPos(), buffer.readOptional(FriendlyByteBuf::readResourceLocation).orElse(null));
    }

//    public static void consume(UpdateForgeRitualPacket packet, CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;
//
//            ClientPacketHandler.handleUpdateRitual(packet);
//        });
//        context.setPacketHandled(true);
//    }
}
