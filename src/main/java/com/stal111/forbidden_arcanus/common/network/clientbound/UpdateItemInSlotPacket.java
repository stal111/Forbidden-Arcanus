package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Update Item In Slot Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateItemInSlotPacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-24
 */
public record UpdateItemInSlotPacket(BlockPos pos, ItemStack stack, int slot) {

    public static void encode(UpdateItemInSlotPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.slot);
    }

    public static UpdateItemInSlotPacket decode(FriendlyByteBuf buffer) {
        return new UpdateItemInSlotPacket(buffer.readBlockPos(), buffer.readItem(), buffer.readInt());
    }

    public static void consume(UpdateItemInSlotPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleUpdateItemInSlot(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
