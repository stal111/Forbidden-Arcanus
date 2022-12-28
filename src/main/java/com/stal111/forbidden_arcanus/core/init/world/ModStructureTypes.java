package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.structure.NipaStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModStructureTypes implements RegistryClass {

    public static final MappedRegistryHelper<StructureType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(Registries.STRUCTURE_TYPE);

    public static final RegistryObject<StructureType<NipaStructure>> NIPA = HELPER.register("nipa", () -> () -> NipaStructure.CODEC);

}
