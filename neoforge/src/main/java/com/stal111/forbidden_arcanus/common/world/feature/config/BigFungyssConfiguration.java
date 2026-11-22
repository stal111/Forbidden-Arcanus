package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BigFungyssConfiguration(
        IntProvider height,
        BlockStateProvider capProvider,
        BlockStateProvider stemProvider,
        CapStyle capStyle
) implements FeatureConfiguration {

    public static final Codec<BigFungyssConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProviders.POSITIVE_CODEC.fieldOf("height").forGetter(BigFungyssConfiguration::height),
            BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(BigFungyssConfiguration::capProvider),
            BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(BigFungyssConfiguration::stemProvider),
            CapStyle.CODEC.fieldOf("cap_style").forGetter(BigFungyssConfiguration::capStyle)
    ).apply(instance, BigFungyssConfiguration::new));

    public enum CapStyle implements StringRepresentable {
        SMALL("small"),
        LARGE("large");

        public static final Codec<CapStyle> CODEC = StringRepresentable.fromEnum(CapStyle::values);

        private final String serializedName;

        CapStyle(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return this.serializedName;
        }
    }
}