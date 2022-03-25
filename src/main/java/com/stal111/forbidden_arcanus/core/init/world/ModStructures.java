package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;
import net.valhelsia.valhelsia_core.common.world.SimpleValhelsiaStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Mod Structures
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModStructures
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
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
        ResourceKey<StructureSet> resourceKey = ResourceKey.create(Registry.STRUCTURE_SET_REGISTRY, new ResourceLocation(ForbiddenArcanus.MOD_ID, "nipas"));

        BuiltinRegistries.register(BuiltinRegistries.STRUCTURE_SETS, resourceKey, new StructureSet(List.of(StructureSet.entry(ModStructureFeatures.NIPA), StructureSet.entry(ModStructureFeatures.NIPA_ALWAYS_FLOATING)), new RandomSpreadStructurePlacement(WorldGenConfig.NIPA_SPACING.get(), WorldGenConfig.NIPA_SEPARATION.get(), RandomSpreadType.LINEAR, 710359251)));
    }
}
