package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import com.stal111.forbidden_arcanus.common.world.structure.config.NipaConfig;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
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
}
