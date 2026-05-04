package com.stal111.forbidden_arcanus.common.network.serverbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleMaterialPayload(Holder<MoltenMaterialType> moltenMaterialType) implements CustomPacketPayload {

    public static final Type<ToggleMaterialPayload> TYPE = new Type<>(ForbiddenArcanus.identifier("select_material"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleMaterialPayload> STREAM_CODEC = StreamCodec.composite(
            MoltenMaterialType.STREAM_CODEC,
            ToggleMaterialPayload::moltenMaterialType,
            ToggleMaterialPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ClibanoMenu menu) {
                menu.getSelectedMaterialState().toggleType(moltenMaterialType);
            }
        });
    }
}
