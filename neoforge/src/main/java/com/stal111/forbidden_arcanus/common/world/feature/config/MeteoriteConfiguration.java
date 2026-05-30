package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MeteoriteConfiguration(
        int radius,
        BlockStateProvider blockStateProvider,
        int craterRadius,
        float magmaChance,
        float fireChance,
        int magmaRadiusOffset,
        float coreRoughness
) implements FeatureConfiguration {

    public static final Codec<MeteoriteConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(1, 64).fieldOf("radius").forGetter(MeteoriteConfiguration::radius),
            BlockStateProvider.CODEC.fieldOf("block_state_provider").forGetter(MeteoriteConfiguration::blockStateProvider),
            Codec.intRange(0, 128).fieldOf("crater_radius").forGetter(MeteoriteConfiguration::craterRadius),
            Codec.floatRange(0F, 1F).fieldOf("magma_chance").forGetter(MeteoriteConfiguration::magmaChance),
            Codec.floatRange(0F, 1F).fieldOf("fire_chance").forGetter(MeteoriteConfiguration::fireChance),
            Codec.intRange(0, 32).fieldOf("magma_radius_offset").forGetter(MeteoriteConfiguration::magmaRadiusOffset),
            Codec.floatRange(0F, 1F).fieldOf("core_roughness").forGetter(MeteoriteConfiguration::coreRoughness)
    ).apply(instance, MeteoriteConfiguration::new));
}
