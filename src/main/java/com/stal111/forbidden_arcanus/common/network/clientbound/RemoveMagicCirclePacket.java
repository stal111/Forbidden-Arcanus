package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

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

    public static void consume(RemoveMagicCirclePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleRemoveMagicCircle(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
