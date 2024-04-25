package com.stal111.forbidden_arcanus.data.worldgen.features;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

/**
 * Mod Vegetation Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.worldgen.features.ModVegetationFeatures
 *
 * @author stal111
 * @since 2022-04-24
 */
public class ModVegetationFeatures extends DatapackRegistryClass<ConfiguredFeature<?, ?>> {

    public static final DatapackRegistryHelper<ConfiguredFeature<?, ?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CONFIGURED_FEATURE);



    public ModVegetationFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        super(context);
    }

    @Override
    public void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeatureRegistry = context.lookup(Registries.PLACED_FEATURE);
    }
}
