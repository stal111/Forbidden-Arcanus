package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.common.tile.forge.MagicCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * Update Magic Circle Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateMagicCirclePacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-17
 */
public class UpdateMagicCirclePacket {

    private final BlockPos pos;
    private final ResourceLocation outerTexture;
    private final ResourceLocation innerTexture;

    public UpdateMagicCirclePacket(BlockPos pos, ResourceLocation outerTexture, ResourceLocation innerTexture) {
        this.pos = pos;
        this.outerTexture = outerTexture;
        this.innerTexture = innerTexture;
    }

    public static void encode(UpdateMagicCirclePacket packet, PacketBuffer buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeResourceLocation(packet.outerTexture);
        buffer.writeResourceLocation(packet.innerTexture);
    }

    public static UpdateMagicCirclePacket decode(PacketBuffer buffer) {
        return new UpdateMagicCirclePacket(buffer.readBlockPos(), buffer.readResourceLocation(), buffer.readResourceLocation());
    }

    public static void consume(UpdateMagicCirclePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            World world = Minecraft.getInstance().world;

            if (world == null || !(world.getTileEntity(packet.pos) instanceof HephaestusForgeTileEntity)) {
                return;
            }

            HephaestusForgeTileEntity tileEntity = (HephaestusForgeTileEntity) world.getTileEntity(packet.pos);

            if (tileEntity != null) {
                MagicCircle magicCircle = tileEntity.getMagicCircle();

                magicCircle.setOuterTexture(packet.outerTexture);
                magicCircle.setInnerTexture(packet.innerTexture);
                magicCircle.setSize(0);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
