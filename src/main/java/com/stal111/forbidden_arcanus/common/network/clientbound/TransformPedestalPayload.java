package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author stal111
 * @since 2023-01-03
 */
public record TransformPedestalPayload(BlockPos pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(ForbiddenArcanus.MOD_ID, "transform_pedestal");

    public TransformPedestalPayload(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }
}
