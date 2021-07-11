package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Update Pedestal Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdatePedestalPacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-10
 */
public class UpdatePedestalPacket {

    private final BlockPos pos;

    public UpdatePedestalPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static void encode(UpdatePedestalPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
    }

    public static UpdatePedestalPacket decode(PacketBuffer buffer) {
        return new UpdatePedestalPacket(buffer.readBlockPos());
    }

    public static void consume(UpdatePedestalPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = Minecraft.getInstance().world;

            if (world != null && world.getTileEntity(packet.pos) instanceof PedestalTileEntity) {
                ((PedestalTileEntity) Objects.requireNonNull(world.getTileEntity(packet.pos))).clearStack();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
