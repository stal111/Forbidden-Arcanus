package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

import java.util.List;

/**
 * @author stal111
 * @since 2022-06-14
 */
public class ModStructureSets implements RegistryClass {

    public static final RegistryHelper<StructureSet> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.STRUCTURE_SET_REGISTRY);

    public static final RegistryObject<StructureSet> NIPAS = HELPER.register("nipas", () -> new StructureSet(List.of(StructureSet.entry((Holder<Structure>) ModStructures.NIPA), StructureSet.entry((Holder<Structure>) ModStructures.NIPA_FLOATING)), new RandomSpreadStructurePlacement(WorldGenConfig.NIPA_SPACING.get(), WorldGenConfig.NIPA_SEPARATION.get(), RandomSpreadType.LINEAR, 710359251)));
}
