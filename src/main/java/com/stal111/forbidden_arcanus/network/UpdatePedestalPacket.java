package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
import net.minecraft.item.ItemStack;
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
    private final ItemStack stack;
    private final int itemHeight;

    public UpdatePedestalPacket(BlockPos pos, ItemStack stack, int itemHeight) {
        this.pos = pos;
        this.stack = stack;
        this.itemHeight = itemHeight;
    }

    public static void encode(UpdatePedestalPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItemStack(packet.stack);
        buffer.writeInt(packet.itemHeight);
    }

    public static UpdatePedestalPacket decode(PacketBuffer buffer) {
        return new UpdatePedestalPacket(buffer.readBlockPos(), buffer.readItemStack(), buffer.readInt());
    }

    public static void consume(UpdatePedestalPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = ForbiddenArcanus.proxy.getClientWorld();

            if (world != null && world.getTileEntity(packet.pos) instanceof PedestalTileEntity) {
                PedestalTileEntity tileEntity = (PedestalTileEntity) Objects.requireNonNull(world.getTileEntity(packet.pos));

                tileEntity.setStack(packet.stack);
                tileEntity.setItemHeight(packet.itemHeight);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
