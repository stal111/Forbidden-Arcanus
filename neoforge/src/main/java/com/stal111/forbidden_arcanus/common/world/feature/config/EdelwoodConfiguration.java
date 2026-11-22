package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record EdelwoodConfiguration(
        IntProvider height,
        float branchChance,
        int branchMinHeight
) implements FeatureConfiguration {

    public static final Codec<EdelwoodConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProviders.POSITIVE_CODEC.fieldOf("height").forGetter(EdelwoodConfiguration::height),
            Codec.floatRange(0.0F, 1.0F).fieldOf("branch_chance").forGetter(EdelwoodConfiguration::branchChance),
            ExtraCodecs.POSITIVE_INT.fieldOf("branch_min_height").forGetter(EdelwoodConfiguration::branchMinHeight)
    ).apply(instance, EdelwoodConfiguration::new));
}
