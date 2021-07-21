package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.common.tile.forge.ritual.Ritual;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Update Ritual Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateRitualPacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-17
 */
public class UpdateRitualPacket {

    private final BlockPos pos;
    @Nullable private final Ritual ritual;
    private final int counter;

    public UpdateRitualPacket(BlockPos pos, @Nullable Ritual ritual, int counter) {
        this.pos = pos;
        this.ritual = ritual;
        this.counter = counter;
    }

    public static void encode(UpdateRitualPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        ResourceLocation empty = new ResourceLocation("", "");
        buffer.writeResourceLocation(packet.ritual != null ? packet.ritual.getName() : empty);
        buffer.writeInt(packet.counter);
    }

    public static UpdateRitualPacket decode(PacketBuffer buffer) {
        return new UpdateRitualPacket(buffer.readBlockPos(), RitualLoader.getRituals().get(buffer.readResourceLocation()), buffer.readInt());
    }

    public static void consume(UpdateRitualPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = Minecraft.getInstance().world;

            if (world == null || !(world.getTileEntity(packet.pos) instanceof HephaestusForgeTileEntity)) {
                return;
            }

            HephaestusForgeTileEntity tileEntity = (HephaestusForgeTileEntity) world.getTileEntity(packet.pos);

            if (tileEntity != null) {
                tileEntity.getRitualManager().setActiveRitual(packet.ritual);
                tileEntity.getRitualManager().setCounter(packet.counter);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
