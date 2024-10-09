package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * Big Fungyss Feature Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-13
 */
public class BigFungyssFeatureConfig implements FeatureConfiguration {

    public static final Codec<BigFungyssFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> {
            return config.capProvider;
        }), BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> {
            return config.stemProvider;
        }), BlockStateProvider.CODEC.fieldOf("hyphae_provider").forGetter((config) -> {
            return config.stemProvider;
        }), Codec.intRange(0, 1).fieldOf("variant").forGetter((config) -> {
            return config.variant;
        })).apply(builder, BigFungyssFeatureConfig::new);
    });
    public final BlockStateProvider capProvider;
    public final BlockStateProvider stemProvider;
    public final BlockStateProvider hyphaeProvider;
    public final int variant;

    public BigFungyssFeatureConfig(BlockStateProvider capProvider, BlockStateProvider stemProvider, BlockStateProvider hyphaeProvider, int variant) {
        this.capProvider = capProvider;
        this.stemProvider = stemProvider;
        this.hyphaeProvider = hyphaeProvider;
        this.variant = variant;
    }
}