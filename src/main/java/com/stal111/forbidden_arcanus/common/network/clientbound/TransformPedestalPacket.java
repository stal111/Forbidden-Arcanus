package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

/**
 * @author stal111
 * @since 2023-01-03
 */
public record TransformPedestalPacket(BlockPos pos) {

    public static void encode(TransformPedestalPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static TransformPedestalPacket decode(FriendlyByteBuf buffer) {
        return new TransformPedestalPacket(buffer.readBlockPos());
    }

    public static void consume(TransformPedestalPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleTransformPedestal(packet);
        });
        context.setPacketHandled(true);
    }
}
