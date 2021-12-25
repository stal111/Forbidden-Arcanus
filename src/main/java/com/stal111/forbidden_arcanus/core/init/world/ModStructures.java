package com.stal111.forbidden_arcanus.core.init.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;
import net.valhelsia.valhelsia_core.common.world.SimpleValhelsiaStructure;

import java.util.*;

/**
 * Mod Structures
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructures
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-07
 */
public class ModStructures {

    public static final List<IValhelsiaStructure> MOD_STRUCTURES = new ArrayList<>();

    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<NipaStructure> NIPA = register(new NipaStructure(NipaConfig.CODEC));

    private static <T extends SimpleValhelsiaStructure<?>> RegistryObject<T> register(T structure) {
        MOD_STRUCTURES.add(structure);
        return STRUCTURES.register(structure.getName(), () -> structure);
    }

    public static void setupStructures() {
        for (IValhelsiaStructure iStructure : MOD_STRUCTURES) {
            StructureFeature<?> structure = iStructure.getStructure();
            StructureFeatureConfiguration separationSettings = iStructure.getFeatureConfiguration();

            StructureFeature.STRUCTURES_REGISTRY.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);

            if (iStructure.transformsSurroundingLand()) {
                StructureFeature.NOISE_AFFECTING_FEATURES =
                        ImmutableList.<StructureFeature<?>>builder()
                                .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                                .add(structure)
                                .build();
            }

            StructureSettings.DEFAULTS =
                    ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                            .putAll(StructureSettings.DEFAULTS)
                            .put(structure, separationSettings)
                            .build();

            BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
                Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

                if (structureMap instanceof ImmutableMap) {
                    Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, separationSettings);
                    settings.getValue().structureSettings().structureConfig = tempMap;
                } else {
                    structureMap.put(structure, separationSettings);
                }
            });
        }
    }

    public static class SeparationSettings {
        public static final StructureFeatureConfiguration NIPA = new StructureFeatureConfiguration(WorldGenConfig.NIPA_SPACING.get(), WorldGenConfig.NIPA_SEPARATION.get(), 694349230);
    }
}
