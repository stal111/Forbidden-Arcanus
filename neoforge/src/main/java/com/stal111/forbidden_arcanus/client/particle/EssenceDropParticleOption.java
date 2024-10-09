package com.stal111.forbidden_arcanus.client.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.List;

/**
 * @author stal111
 * @since 12.06.2024
 */
public record EssenceDropParticleOption(EssenceType type, List<Vector3f> path) implements ParticleOptions {

    public static final MapCodec<EssenceDropParticleOption> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(EssenceDropParticleOption::type),
            ExtraCodecs.VECTOR3F.listOf().fieldOf("path").forGetter(EssenceDropParticleOption::path)
    ).apply(instance, EssenceDropParticleOption::new));

    public static final StreamCodec<FriendlyByteBuf, EssenceDropParticleOption> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceDropParticleOption::type,
            ByteBufCodecs.VECTOR3F.apply(ByteBufCodecs.list()),
            EssenceDropParticleOption::path,
            EssenceDropParticleOption::new
    );

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.type.getParticleType();
    }
}
