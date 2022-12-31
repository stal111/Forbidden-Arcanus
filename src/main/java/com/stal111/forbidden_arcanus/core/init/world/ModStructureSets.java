package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.DatapackRegistryHelper;

import java.util.List;

/**
 * @author stal111
 * @since 2022-06-14
 */
public class ModStructureSets extends DatapackRegistryClass<StructureSet> {

    public static final DatapackRegistryHelper<StructureSet> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getDatapackHelper(Registries.STRUCTURE_SET);

    public static final ResourceKey<StructureSet> NIPAS = HELPER.createKey("nipas");


    public ModStructureSets(DataProviderInfo info, BootstapContext<StructureSet> context) {
        super(info, context);
    }

    @Override
    public void bootstrap(BootstapContext<StructureSet> context) {
        HolderGetter<Structure> structureRegistry = context.lookup(Registries.STRUCTURE);

        context.register(NIPAS, new StructureSet(List.of(StructureSet.entry(structureRegistry.getOrThrow(ModStructures.NIPA)), StructureSet.entry(structureRegistry.getOrThrow(ModStructures.NIPA_FLOATING))), new RandomSpreadStructurePlacement(35, 8, RandomSpreadType.LINEAR, 710359251)));
    }
}
