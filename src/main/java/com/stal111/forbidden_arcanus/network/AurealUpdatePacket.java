package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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
    private CompoundTag tag;

    public AurealUpdatePacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public AurealUpdatePacket(Player player) {
        this.uuid = player.getUUID();
        player.getCapability(AurealProvider.CAPABILITY, null).ifPresent((aureal) -> {

            //TODO
            //this.tag = (CompoundTag) AurealProvider.
        });
    }

    public static void encode(AurealUpdatePacket packet, FriendlyByteBuf buffer) {
        buffer.writeUUID(packet.uuid);
        buffer.writeNbt(packet.tag);
    }

    public static AurealUpdatePacket decode(FriendlyByteBuf buffer) {
        return new AurealUpdatePacket(buffer.readUUID(), buffer.readNbt());
    }

    public static void consume(AurealUpdatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = ForbiddenArcanus.proxy.getClientWorld();
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                player.getCapability(AurealProvider.CAPABILITY).ifPresent((aureal) -> {

                    //TODO
                  //  AurealProvider.CAPABILITY.getStorage().readNBT(AurealProvider.CAPABILITY, aureal, null, packet.tag);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
