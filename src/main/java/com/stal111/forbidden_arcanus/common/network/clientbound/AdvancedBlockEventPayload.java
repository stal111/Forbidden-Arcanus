package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 02.06.2024
 */
public record AdvancedBlockEventPayload(BlockPos pos, Block block, int b1, int b2) implements CustomPacketPayload {

    public static final Type<AdvancedBlockEventPayload> TYPE = new Type<>(new ResourceLocation(ForbiddenArcanus.MOD_ID, "block_event"));

    public static final StreamCodec<RegistryFriendlyByteBuf, AdvancedBlockEventPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            AdvancedBlockEventPayload::pos,
            ByteBufCodecs.registry(Registries.BLOCK),
            AdvancedBlockEventPayload::block,
            ByteBufCodecs.INT,
            AdvancedBlockEventPayload::b1,
            ByteBufCodecs.INT,
            AdvancedBlockEventPayload::b2,
            AdvancedBlockEventPayload::new
    );

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
