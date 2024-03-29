package com.stal111.forbidden_arcanus.core.init.other;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

import java.util.stream.Collectors;

/**
 * @author stal111
 * @since 2021-07-31
 */
public class ModPOITypes implements RegistryClass {

    public static final MappedRegistryHelper<PoiType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.POINT_OF_INTEREST_TYPE);

    public static final RegistryEntry<PoiType> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> new PoiType(ModBlocks.HEPHAESTUS_FORGE_TIER_1.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(HephaestusForgeBlock.ACTIVATED)).collect(Collectors.toUnmodifiableSet()), 0, 1));
    public static final RegistryEntry<PoiType> CLIBANO_MAIN_PART = HELPER.register("clibano_main_part", () -> new PoiType(ImmutableSet.of(ModBlocks.CLIBANO_MAIN_PART.get().defaultBlockState()), 0, 1));

}
