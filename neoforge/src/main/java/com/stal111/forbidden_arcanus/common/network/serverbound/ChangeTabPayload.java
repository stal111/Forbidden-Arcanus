package com.stal111.forbidden_arcanus.common.network.serverbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.tab.ContainerTab;
import com.stal111.forbidden_arcanus.common.inventory.tab.TabbedContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ChangeTabPayload(ContainerTab tab) implements CustomPacketPayload {

    public static final Type<ChangeTabPayload> TYPE = new Type<>(ForbiddenArcanus.identifier("change_tab"));

    public static final StreamCodec<FriendlyByteBuf, ChangeTabPayload> STREAM_CODEC = StreamCodec.composite(
            ContainerTab.STREAM_CODEC,
            ChangeTabPayload::tab,
            ChangeTabPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().containerMenu instanceof TabbedContainerMenu menu) {
                menu.setActiveTab(this.tab());
            }
        });
    }
}
