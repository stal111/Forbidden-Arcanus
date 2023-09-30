package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

/**
 * Update Item In Slot Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateItemInSlotPacket
 *
 * @author stal111
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

    public static void consume(UpdateItemInSlotPacket packet, CustomPayloadEvent.Context context) {
        context.enqueueWork(() -> {
            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleUpdateItemInSlot(packet);
        });
        context.setPacketHandled(true);
    }
}
