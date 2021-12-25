package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

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

    public static void consume(AddItemParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleAddItemParticle(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
