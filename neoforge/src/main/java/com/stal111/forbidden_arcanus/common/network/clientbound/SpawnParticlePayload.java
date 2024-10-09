package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * @author stal111
 * @since 19.04.2024
 */
public record SpawnParticlePayload(double x, double y, double z, int id) implements CustomPacketPayload {

    public static final Type<SpawnParticlePayload> TYPE = new Type<>(ForbiddenArcanus.location("spawn_particle"));

    public static final StreamCodec<ByteBuf, SpawnParticlePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            SpawnParticlePayload::x,
            ByteBufCodecs.DOUBLE,
            SpawnParticlePayload::y,
            ByteBufCodecs.DOUBLE,
            SpawnParticlePayload::z,
            ByteBufCodecs.INT,
            SpawnParticlePayload::id,
            SpawnParticlePayload::new
    );

    public void handle(IPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
