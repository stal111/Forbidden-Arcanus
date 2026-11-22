package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record BigFungyssFeatureConfig(
        BlockStateProvider capProvider,
        BlockStateProvider stemProvider,
        BlockStateProvider hyphaeProvider,
        int variant
) implements FeatureConfiguration {

    public static final Codec<BigFungyssFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(BigFungyssFeatureConfig::capProvider),
            BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(BigFungyssFeatureConfig::stemProvider),
            BlockStateProvider.CODEC.fieldOf("hyphae_provider").forGetter(BigFungyssFeatureConfig::hyphaeProvider),
            Codec.intRange(0, 1).fieldOf("variant").forGetter(BigFungyssFeatureConfig::variant)
    ).apply(instance, BigFungyssFeatureConfig::new));
}