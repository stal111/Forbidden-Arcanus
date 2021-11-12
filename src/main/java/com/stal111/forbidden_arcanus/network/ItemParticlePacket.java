package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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

    public static void encode(ItemParticlePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
    }

    public static ItemParticlePacket decode(FriendlyByteBuf buffer) {
        return new ItemParticlePacket(buffer.readBlockPos(), buffer.readItem());
    }

    public static void consume(ItemParticlePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Minecraft.getInstance().level;

            RenderUtils.addItemParticles(Objects.requireNonNull(world), packet.stack, packet.pos, 16);
        });
        ctx.get().setPacketHandled(true);
    }
}
