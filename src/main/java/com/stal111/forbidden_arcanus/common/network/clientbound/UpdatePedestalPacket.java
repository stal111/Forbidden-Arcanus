package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Update Pedestal Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdatePedestalPacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-10
 */
public record UpdatePedestalPacket(BlockPos pos, ItemStack stack, int itemHeight) {

    public static void encode(UpdatePedestalPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.itemHeight);
    }

    public static UpdatePedestalPacket decode(FriendlyByteBuf buffer) {
        return new UpdatePedestalPacket(buffer.readBlockPos(), buffer.readItem(), buffer.readInt());
    }

    public static void consume(UpdatePedestalPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleUpdatePedestal(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
