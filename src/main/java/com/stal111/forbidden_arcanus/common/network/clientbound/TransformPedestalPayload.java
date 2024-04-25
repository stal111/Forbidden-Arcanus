package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 2023-01-03
 */
public record TransformPedestalPayload(BlockPos pos) implements CustomPacketPayload {

    public static final Type<TransformPedestalPayload> TYPE = new Type<>(new ResourceLocation(ForbiddenArcanus.MOD_ID, "transform_pedestal"));

    public static final StreamCodec<FriendlyByteBuf, TransformPedestalPayload> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            TransformPedestalPayload::pos,
            TransformPedestalPayload::new
    );

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
