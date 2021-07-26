package com.stal111.forbidden_arcanus.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.world.structure.config.NipaConfig;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.Structure;

/**
 * Mod Structure Features
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructureFeatures
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-04-11
 */
public class ModStructureFeatures {

    public static final StructureFeature<NipaConfig, ? extends Structure<NipaConfig>> NIPA = register("nipa", ModStructures.NIPA.get().withConfiguration(new NipaConfig(0.3F)));
    public static final StructureFeature<NipaConfig, ? extends Structure<NipaConfig>> NIPA_ALWAYS_FLOATING = register("nipa_floating", ModStructures.NIPA.get().withConfiguration(new NipaConfig(1.0F)));

    private static <FC extends IFeatureConfig, F extends Structure<FC>> StructureFeature<FC, F> register(String name, StructureFeature<FC, F> structureFeature) {
        if (!FlatGenerationSettings.STRUCTURES.containsKey(structureFeature.field_236268_b_)) {
            FlatGenerationSettings.STRUCTURES.put(structureFeature.field_236268_b_, structureFeature);
        }

        return WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), structureFeature);
    }
}
