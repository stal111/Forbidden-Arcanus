package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 02.03.2024
 */
public record SetClibanoResiduesPayload(ResiduesStorage residueAmounts) implements CustomPacketPayload {

    public static final Type<SetClibanoResiduesPayload> TYPE = new Type<>(ForbiddenArcanus.location("set_clibano_residues"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SetClibanoResiduesPayload> STREAM_CODEC = StreamCodec.composite(
            ResiduesStorage.STREAM_CODEC,
            SetClibanoResiduesPayload::residueAmounts,
            SetClibanoResiduesPayload::new
    );

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
