package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Item Particle Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.ItemParticlePacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-13
 */
public class ItemParticlePacket {

    private final BlockPos pos;
    private final ItemStack stack;

    public ItemParticlePacket(BlockPos pos, ItemStack stack) {
        this.pos = pos;
        this.stack = stack;
    }

    public static void encode(ItemParticlePacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItemStack(packet.stack);
    }

    public static ItemParticlePacket decode(PacketBuffer buffer) {
        return new ItemParticlePacket(buffer.readBlockPos(), buffer.readItemStack());
    }

    public static void consume(ItemParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = ForbiddenArcanus.proxy.getClientWorld();

            RenderUtils.addItemParticles(Objects.requireNonNull(world), packet.stack, packet.pos, 16);
        });
        ctx.get().setPacketHandled(true);
    }
}
