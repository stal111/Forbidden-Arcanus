package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

/**
 * @author stal111
 * @since 2023-01-19
 */
public record CreateValidRitualIndicatorPacket(BlockPos pos) {

    public static void encode(CreateValidRitualIndicatorPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static CreateValidRitualIndicatorPacket decode(FriendlyByteBuf buffer) {
        return new CreateValidRitualIndicatorPacket(buffer.readBlockPos());
    }

    public static void consume(CreateValidRitualIndicatorPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleCreateValidRitualIndicator(packet);
        });
        context.setPacketHandled(true);
    }
}
