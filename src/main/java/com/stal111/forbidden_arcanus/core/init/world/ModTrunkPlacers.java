package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * Mod Trunk Placers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.init.world.ModTrunkPlacers
 *
 * @author stal111
 * @since 2022-03-26
 */
public class ModTrunkPlacers implements RegistryClass {

    public static final MappedRegistryHelper<TrunkPlacerType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(Registries.TRUNK_PLACER_TYPE);

    public static final RegistryObject<TrunkPlacerType<CherryTrunkPlacer>> CHERRY_TRUNK_PLACER = HELPER.register("cherry_trunk_placer", () -> new TrunkPlacerType<>(CherryTrunkPlacer.CODEC));

}
