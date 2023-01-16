package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.block.entity.forge.MagicCircle;
import com.stal111.forbidden_arcanus.common.network.ClientPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2023-01-16
 */
public record CreateMagicCirclePacket(BlockPos pos, ResourceLocation innerTexture, ResourceLocation outerTexture, int progress) {

    public CreateMagicCirclePacket(BlockPos pos, MagicCircle.TextureProvider provider, int progress) {
        this(pos, provider.getInnerTexture(), provider.getOuterTexture(), progress);
    }

    public static void encode(CreateMagicCirclePacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        buffer.writeResourceLocation(packet.innerTexture);
        buffer.writeResourceLocation(packet.outerTexture);
        buffer.writeInt(packet.progress);
    }

    public static CreateMagicCirclePacket decode(FriendlyByteBuf buffer) {
        return new CreateMagicCirclePacket(buffer.readBlockPos(), buffer.readResourceLocation(), buffer.readResourceLocation(), buffer.readInt());
    }

    public static void consume(CreateMagicCirclePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ClientPacketHandler.handleCreateMagicCircle(packet);
        });
        ctx.get().setPacketHandled(true);
    }
}
