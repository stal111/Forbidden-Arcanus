package com.stal111.forbidden_arcanus.world.structure.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * Nipa Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.structure.config.NipaConfig
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-07
 */
public class NipaConfig implements FeatureConfiguration {

    public static final Codec<NipaConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("floating_probability").forGetter((config) -> {
            return config.floatingProbability;
        })).apply(builder, NipaConfig::new);
    });

    private final float floatingProbability;

    public NipaConfig(float floatingProbability) {
        this.floatingProbability = floatingProbability;
    }

    public float getFloatingProbability() {
        return floatingProbability;
    }
}
