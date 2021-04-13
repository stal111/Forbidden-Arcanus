package com.stal111.forbidden_arcanus.init.world;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.world.structure.config.NipaConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

/**
 * Mod Structures
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructures
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-07
 */
public class ModStructures {

    public static final List<Structure<?>> MOD_STRUCTURES = new ArrayList<>();

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<NipaStructure> NIPA = register("nipa", new NipaStructure(NipaConfig.CODEC), new StructureSeparationSettings(WorldGenConfig.NIPA_SPACING.get(), WorldGenConfig.NIPA_SEPARATION.get(), 694349230));

    private static <T extends Structure<?>> RegistryObject<T> register(String name, T structure, StructureSeparationSettings structureSeparationSettings) {
        Structure.NAME_STRUCTURE_BIMAP.put(ForbiddenArcanus.MOD_ID + ":" + name, structure);
        Structure.STRUCTURE_DECORATION_STAGE_MAP.put(structure, structure.getDecorationStage());

        MOD_STRUCTURES.add(structure);

        DimensionStructuresSettings.field_236191_b_ =
                ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                        .putAll(DimensionStructuresSettings.field_236191_b_)
                        .put(structure, structureSeparationSettings)
                        .build();

        return STRUCTURES.register(name, () -> structure);
    }
}