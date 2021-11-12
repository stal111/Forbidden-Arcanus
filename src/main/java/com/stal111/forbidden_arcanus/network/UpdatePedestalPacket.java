package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.block.tileentity.PedestalTileEntity;
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

    public static void encode(UpdatePedestalPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeItem(packet.stack);
        buffer.writeInt(packet.itemHeight);
    }

    public static UpdatePedestalPacket decode(FriendlyByteBuf buffer) {
        return new UpdatePedestalPacket(buffer.readBlockPos(), buffer.readItem(), buffer.readInt());
    }

    public static void consume(UpdatePedestalPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Minecraft.getInstance().level;

            if (world != null && world.getBlockEntity(packet.pos) instanceof PedestalTileEntity) {
                PedestalTileEntity tileEntity = (PedestalTileEntity) Objects.requireNonNull(world.getBlockEntity(packet.pos));

                tileEntity.setStack(packet.stack);
                tileEntity.setItemHeight(packet.itemHeight);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
