package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.BigFungyssFeature;
import com.stal111.forbidden_arcanus.common.world.feature.EdelwoodFeature;
import com.stal111.forbidden_arcanus.common.world.feature.MegaFungyssFeature;
import com.stal111.forbidden_arcanus.common.world.feature.MeteoriteFeature;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssConfiguration;
import com.stal111.forbidden_arcanus.common.world.feature.config.EdelwoodConfiguration;
import com.stal111.forbidden_arcanus.common.world.feature.config.MegaFungyssConfiguration;
import com.stal111.forbidden_arcanus.common.world.feature.config.MeteoriteConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModFeatures implements RegistryClass {

    public static final MappedRegistryHelper<Feature<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.FEATURE);

    public static final RegistryEntry<Feature<?>, Feature<BigFungyssConfiguration>> BIG_FUNGYSS = HELPER.register("big_fungyss", () -> new BigFungyssFeature(BigFungyssConfiguration.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<MegaFungyssConfiguration>> MEGA_FUNGYSS = HELPER.register("mega_fungyss", () -> new MegaFungyssFeature(MegaFungyssConfiguration.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<EdelwoodConfiguration>> EDELWOOD = HELPER.register("edelwood", () -> new EdelwoodFeature(EdelwoodConfiguration.CODEC));
    public static final RegistryEntry<Feature<?>, Feature<MeteoriteConfiguration>> METEORITE = HELPER.register("meteorite", () -> new MeteoriteFeature(MeteoriteConfiguration.CODEC));

}