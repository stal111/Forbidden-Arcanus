package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InsertMoltenMaterialPayload(MoltenMaterial material) implements CustomPacketPayload {

    public static final Type<InsertMoltenMaterialPayload> TYPE = new Type<>(ForbiddenArcanus.identifier("insert_molten_material"));

    public static final StreamCodec<RegistryFriendlyByteBuf, InsertMoltenMaterialPayload> STREAM_CODEC = MoltenMaterial.STREAM_CODEC.map(
            InsertMoltenMaterialPayload::new,
            InsertMoltenMaterialPayload::material
    );

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
