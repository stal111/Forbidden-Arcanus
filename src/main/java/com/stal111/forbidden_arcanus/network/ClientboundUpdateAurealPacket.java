package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Clientbound Update Aureal Packet <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.ClientboundUpdateAurealPacket
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-27
 */
public record ClientboundUpdateAurealPacket(CompoundTag tag) {

    public ClientboundUpdateAurealPacket(Player player) {
        this(AurealHelper.save(new CompoundTag(), AurealHelper.getCapability(player)));
    }

    public static void encode(ClientboundUpdateAurealPacket packet, FriendlyByteBuf buffer) {
        buffer.writeNbt(packet.tag);
    }

    public static ClientboundUpdateAurealPacket decode(FriendlyByteBuf buffer) {
        return new ClientboundUpdateAurealPacket(buffer.readNbt());
    }

    public static void consume(ClientboundUpdateAurealPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;
            Player player = Minecraft.getInstance().player;

            if (player != null) {
                AurealHelper.load(packet.tag, AurealHelper.getCapability(player));
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
