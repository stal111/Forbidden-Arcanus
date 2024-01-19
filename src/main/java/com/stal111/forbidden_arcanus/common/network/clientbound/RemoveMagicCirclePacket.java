package com.stal111.forbidden_arcanus.common.network.clientbound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author stal111
 * @since 2023-01-16
 */
public record RemoveMagicCirclePacket(BlockPos pos) {

    public static void encode(RemoveMagicCirclePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static RemoveMagicCirclePacket decode(FriendlyByteBuf buffer) {
        return new RemoveMagicCirclePacket(buffer.readBlockPos());
    }

//    public static void consume(RemoveMagicCirclePacket packet, CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;
//
//            ClientPacketHandler.handleRemoveMagicCircle(packet);
//        });
//        context.setPacketHandled(true);
//    }
}
