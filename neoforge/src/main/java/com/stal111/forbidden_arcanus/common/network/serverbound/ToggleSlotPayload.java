package com.stal111.forbidden_arcanus.common.network.serverbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.SelectedSlotState;
import com.stal111.forbidden_arcanus.common.inventory.ClibanoMenu;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleSlotPayload(SelectedSlotState state) implements CustomPacketPayload {

    public static final Type<ToggleSlotPayload> TYPE = new Type<>(ForbiddenArcanus.identifier("toggle_slot"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ToggleSlotPayload> STREAM_CODEC = StreamCodec.composite(
            SelectedSlotState.STREAM_CODEC,
            ToggleSlotPayload::state,
            ToggleSlotPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof ClibanoMenu menu) {
                menu.getSelectedMaterialState().toggleSlot(this.state.getSelected().orElse(null));
            }
        });
    }
}
