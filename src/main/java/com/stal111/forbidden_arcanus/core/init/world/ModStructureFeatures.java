package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

/**
 * Mod Structure Features
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructureFeatures
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-04-11
 */
public class ModStructureFeatures {

    public static final Holder<ConfiguredStructureFeature<?, ?>> NIPA = register("nipa", ModStructures.NIPA.get().configured(new NipaConfig(0.3F), BiomeTags.IS_FOREST));
    public static final Holder<ConfiguredStructureFeature<?, ?>> NIPA_ALWAYS_FLOATING = register("nipa_floating", ModStructures.NIPA.get().configured(new NipaConfig(1.0F), BiomeTags.IS_FOREST));


    private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> Holder<ConfiguredStructureFeature<?, ?>> register(String name, ConfiguredStructureFeature<FC, F> configuredStructureFeature) {
        ResourceKey<ConfiguredStructureFeature<?, ?>> resourceKey = ResourceKey.create(Registry.CONFIGURED_STRUCTURE_FEATURE_REGISTRY, new ResourceLocation(ForbiddenArcanus.MOD_ID, name));

        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, resourceKey, configuredStructureFeature);
    }
}
