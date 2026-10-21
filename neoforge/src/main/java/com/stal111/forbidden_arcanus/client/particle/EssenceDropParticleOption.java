package com.stal111.forbidden_arcanus.client.particle;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record EssenceDropParticleOption(EssencePath path) implements ParticleOptions {

    public static final MapCodec<EssenceDropParticleOption> CODEC = EssencePath.CODEC.xmap(EssenceDropParticleOption::new, EssenceDropParticleOption::path);

    public static final StreamCodec<FriendlyByteBuf, EssenceDropParticleOption> STREAM_CODEC = EssencePath.STREAM_CODEC.map(EssenceDropParticleOption::new, EssenceDropParticleOption::path);

    public EssenceDropParticleOption(EssenceType essenceType) {
        this(new EssencePath(essenceType, List.of()));
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return this.path.essenceType().getParticleType();
    }
}
