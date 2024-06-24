package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 13.05.2024
 */
public record UpdateEssencePayload(EssenceStorage storage) implements CustomPacketPayload {

    public static final Type<UpdateEssencePayload> TYPE = new Type<>(ForbiddenArcanus.location("update_aureal"));

    public static final StreamCodec<FriendlyByteBuf, UpdateEssencePayload> STREAM_CODEC = EssenceStorage.STREAM_CODEC.map(UpdateEssencePayload::new, UpdateEssencePayload::storage);

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
