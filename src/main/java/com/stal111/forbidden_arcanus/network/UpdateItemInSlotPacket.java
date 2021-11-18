package com.stal111.forbidden_arcanus.network;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

/**
 * Update Item In Slot Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateItemInSlotPacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-24
 */
public class UpdateItemInSlotPacket {

    private final BlockPos pos;
    private final ItemStack stack;
    private final int slot;

    public UpdateItemInSlotPacket(BlockPos pos, ItemStack stack, int slot) {
        this.pos = pos;
        this.stack = stack;
        this.slot = slot;
    }

    public static void encode(UpdateItemInSlotPacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItemStack(packet.stack);
        buffer.writeInt(packet.slot);
    }

    public static UpdateItemInSlotPacket decode(PacketBuffer buffer) {
        return new UpdateItemInSlotPacket(buffer.readBlockPos(), buffer.readItemStack(), buffer.readInt());
    }

    public static void consume(UpdateItemInSlotPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = ForbiddenArcanus.proxy.getClientWorld();

            if (world != null && world.getTileEntity(packet.pos) instanceof IInventory) {
                IInventory tileEntity = (IInventory) Objects.requireNonNull(world.getTileEntity(packet.pos));

                tileEntity.setInventorySlotContents(packet.slot, packet.stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
