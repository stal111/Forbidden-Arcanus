package com.stal111.forbidden_arcanus.common.network.clientbound;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

/**
 * Add Item Particle Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.AddItemParticlePacket
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-13
 */
public record AddItemParticlePacket(BlockPos pos, ItemStack stack) {

    public static void encode(AddItemParticlePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
    }

    public static AddItemParticlePacket decode(FriendlyByteBuf buffer) {
        return new AddItemParticlePacket(buffer.readBlockPos(), buffer.readItem());
    }

//    public static void consume(AddItemParticlePacket packet, CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;
//
//            ClientPacketHandler.handleAddItemParticle(packet);
//        });
//        context.setPacketHandled(true);
//    }
}
