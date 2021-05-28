package com.stal111.forbidden_arcanus.init.world;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.world.structure.config.NipaConfig;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.world.IValhelsiaStructure;
import net.valhelsia.valhelsia_core.world.SimpleValhelsiaStructure;

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

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<NipaStructure> NIPA = register(new NipaStructure(NipaConfig.CODEC));

    private static <T extends SimpleValhelsiaStructure<?>> RegistryObject<T> register(T structure) {
        MOD_STRUCTURES.add(structure);
        return STRUCTURES.register(structure.getName(), () -> structure);
    }

    public static void setupStructures() {
        for (IValhelsiaStructure iStructure : MOD_STRUCTURES) {
            Structure<?> structure = iStructure.getStructure();
            StructureSeparationSettings separationSettings = iStructure.getSeparationSettings();

            Structure.NAME_STRUCTURE_BIMAP.put(Objects.requireNonNull(structure.getRegistryName()).toString(), structure);

            if (iStructure.transformsSurroundingLand()) {
                Structure.field_236384_t_ =
                        ImmutableList.<Structure<?>>builder()
                                .addAll(Structure.field_236384_t_)
                                .add(structure)
                                .build();
            }

            DimensionStructuresSettings.field_236191_b_ =
                    ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                            .putAll(DimensionStructuresSettings.field_236191_b_)
                            .put(structure, separationSettings)
                            .build();

            WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, separationSettings);
                    settings.getValue().getStructures().field_236193_d_ = tempMap;
                } else {
                    structureMap.put(structure, separationSettings);
                }
            });
        }
    }

    public static class SeparationSettings {
        public static final StructureSeparationSettings NIPA = new StructureSeparationSettings(WorldGenConfig.NIPA_SPACING.get(), WorldGenConfig.NIPA_SEPARATION.get(), 694349230);
    }
}
