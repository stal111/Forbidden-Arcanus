package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

/**
 * Aureal Update Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.AurealUpdatePacket
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-27
 */
public class AurealUpdatePacket {

    private final UUID uuid;
    private CompoundNBT tag;

    public AurealUpdatePacket(UUID uuid, CompoundNBT tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public AurealUpdatePacket(PlayerEntity player) {
        this.uuid = player.getUniqueID();
        player.getCapability(AurealProvider.CAPABILITY, null).ifPresent((aureal) -> {
            this.tag = (CompoundNBT) AurealProvider.CAPABILITY.getStorage().writeNBT(AurealProvider.CAPABILITY, aureal, null);
        });
    }

    public static void encode(AurealUpdatePacket packet, PacketBuffer buffer) {
        buffer.writeUniqueId(packet.uuid);
        buffer.writeCompoundTag(packet.tag);
    }

    public static AurealUpdatePacket decode(PacketBuffer buffer) {
        return new AurealUpdatePacket(buffer.readUniqueId(), buffer.readCompoundTag());
    }

    public static void consume(AurealUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = ForbiddenArcanus.proxy.getClientWorld();
            PlayerEntity player = world.getPlayerByUuid(packet.uuid);
            if (player != null) {
                player.getCapability(AurealProvider.CAPABILITY).ifPresent((aureal) -> {
                    AurealProvider.CAPABILITY.getStorage().readNBT(AurealProvider.CAPABILITY, aureal, null, packet.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
