package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

/**
 * Mod Structure Features
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructureFeatures
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-04-11
 */
public class ModStructureFeatures {

    public static final ConfiguredStructureFeature<NipaConfig, ? extends StructureFeature<NipaConfig>> NIPA = register("nipa", ModStructures.NIPA.get().configured(new NipaConfig(0.3F)));
    public static final ConfiguredStructureFeature<NipaConfig, ? extends StructureFeature<NipaConfig>> NIPA_ALWAYS_FLOATING = register("nipa_floating", ModStructures.NIPA.get().configured(new NipaConfig(1.0F)));

    private static <FC extends FeatureConfiguration, F extends StructureFeature<FC>> ConfiguredStructureFeature<FC, F> register(String name, ConfiguredStructureFeature<FC, F> structureFeature) {
        if (!FlatLevelGeneratorSettings.STRUCTURE_FEATURES.containsKey(structureFeature.feature)) {
            FlatLevelGeneratorSettings.STRUCTURE_FEATURES.put(structureFeature.feature, structureFeature);
        }

        return BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), structureFeature);
    }
}
