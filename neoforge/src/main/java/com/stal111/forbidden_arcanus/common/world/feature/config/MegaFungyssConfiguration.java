package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record MegaFungyssConfiguration(
        IntProvider height,
        IntProvider smallFungyssHeight,
        BlockStateProvider capProvider,
        BlockStateProvider stemProvider,
        BlockStateProvider hyphaeProvider,
        SideDecoration sideDecoration
) implements FeatureConfiguration {

    public static final Codec<MegaFungyssConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IntProviders.POSITIVE_CODEC.fieldOf("height").forGetter(MegaFungyssConfiguration::height),
            IntProviders.POSITIVE_CODEC.fieldOf("small_fungyss_height").forGetter(MegaFungyssConfiguration::smallFungyssHeight),
            BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(MegaFungyssConfiguration::capProvider),
            BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(MegaFungyssConfiguration::stemProvider),
            BlockStateProvider.CODEC.fieldOf("hyphae_provider").forGetter(MegaFungyssConfiguration::hyphaeProvider),
            SideDecoration.CODEC.fieldOf("side_decoration").forGetter(MegaFungyssConfiguration::sideDecoration)
    ).apply(instance, MegaFungyssConfiguration::new));

    public enum SideDecoration implements StringRepresentable {
        SMALL_CAPS("small_caps"),
        SMALL_FUNGYSS("small_fungyss");

        public static final Codec<SideDecoration> CODEC = StringRepresentable.fromEnum(SideDecoration::values);

        private final String serializedName;

        SideDecoration(String serializedName) {
            this.serializedName = serializedName;
        }

        @Override
        public String getSerializedName() {
            return this.serializedName;
        }
    }
}