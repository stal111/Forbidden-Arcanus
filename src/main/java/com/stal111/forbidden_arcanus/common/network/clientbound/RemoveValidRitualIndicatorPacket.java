package com.stal111.forbidden_arcanus.common.network.clientbound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author stal111
 * @since 2023-01-19
 */
public record RemoveValidRitualIndicatorPacket(BlockPos pos) {

    public static void encode(RemoveValidRitualIndicatorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static RemoveValidRitualIndicatorPacket decode(FriendlyByteBuf buffer) {
        return new RemoveValidRitualIndicatorPacket(buffer.readBlockPos());
    }

//    public static void consume(RemoveValidRitualIndicatorPacket packet, CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;
//
//            ClientPacketHandler.handleRemoveValidRitualIndicator(packet);
//        });
//        context.setPacketHandled(true);
//    }
}
