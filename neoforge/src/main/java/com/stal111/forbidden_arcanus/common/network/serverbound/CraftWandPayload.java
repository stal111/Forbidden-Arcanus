package com.stal111.forbidden_arcanus.common.network.serverbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.inventory.wand.WandDeskMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class CraftWandPayload implements CustomPacketPayload {

    public static final Type<CraftWandPayload> TYPE = new Type<>(ForbiddenArcanus.identifier("craft_wand"));

    public static final CraftWandPayload INSTANCE = new CraftWandPayload();
    public static final StreamCodec<FriendlyByteBuf, CraftWandPayload> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private CraftWandPayload() {}

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
           if (context.player().containerMenu instanceof WandDeskMenu menu) {
               menu.craftWand();
           }
        });
    }
}
