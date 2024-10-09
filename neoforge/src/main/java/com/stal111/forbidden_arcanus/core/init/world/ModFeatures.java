package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.BigFungyssFeature;
import com.stal111.forbidden_arcanus.common.world.feature.EdelwoodFeature;
import com.stal111.forbidden_arcanus.common.world.feature.MegaFungyssFeature;
import com.stal111.forbidden_arcanus.common.world.feature.RootFeature;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModFeatures implements RegistryClass {

    public static final MappedRegistryHelper<Feature<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.FEATURE);

    public static final RegistryEntry<Feature<?>, Feature<BlockStateConfiguration>> PETRIFIED_ROOT = HELPER.register("petrified_root", () -> new RootFeature(BlockStateConfiguration.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<BigFungyssFeatureConfig>> BIG_FUNGYSS = HELPER.register("big_fungyss", () -> new BigFungyssFeature(BigFungyssFeatureConfig.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<BigFungyssFeatureConfig>> MEGA_FUNGYSS = HELPER.register("mega_fungyss", () -> new MegaFungyssFeature(BigFungyssFeatureConfig.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<NoneFeatureConfiguration>> EDELWOOD = HELPER.register("edelwood", () -> new EdelwoodFeature(NoneFeatureConfiguration.CODEC));

}