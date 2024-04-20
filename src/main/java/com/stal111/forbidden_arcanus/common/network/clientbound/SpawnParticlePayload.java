package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author stal111
 * @since 19.04.2024
 */
public record SpawnParticlePayload(double x, double y, double z, int type) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(ForbiddenArcanus.MOD_ID, "spawn_particle");

    public SpawnParticlePayload(FriendlyByteBuf buffer) {
        this(buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readInt());
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeDouble(this.x);
        buffer.writeDouble(this.y);
        buffer.writeDouble(this.z);
        buffer.writeInt(this.type);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }
}
