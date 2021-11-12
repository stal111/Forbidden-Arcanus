package com.stal111.forbidden_arcanus.network;

import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

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

    public static void encode(UpdateItemInSlotPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.slot);
    }

    public static UpdateItemInSlotPacket decode(FriendlyByteBuf buffer) {
        return new UpdateItemInSlotPacket(buffer.readBlockPos(), buffer.readItem(), buffer.readInt());
    }

    public static void consume(UpdateItemInSlotPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Minecraft.getInstance().level;

            if (world != null && world.getBlockEntity(packet.pos) instanceof Container) {
                Container tileEntity = (Container) Objects.requireNonNull(world.getBlockEntity(packet.pos));

                tileEntity.setItem(packet.slot, packet.stack);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
