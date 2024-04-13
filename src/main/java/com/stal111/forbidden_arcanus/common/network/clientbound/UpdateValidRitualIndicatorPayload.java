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
 * @since 12.04.2024
 */
public record UpdateValidRitualIndicatorPayload(BlockPos pos, boolean showIndicator) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(ForbiddenArcanus.MOD_ID, "update_valid_ritual_indicator");

    public UpdateValidRitualIndicatorPayload(FriendlyByteBuf buffer) {
        this(buffer.readBlockPos(), buffer.readBoolean());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.pos);
        buffer.writeBoolean(this.showIndicator);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }
}
