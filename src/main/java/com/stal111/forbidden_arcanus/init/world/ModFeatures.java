package com.stal111.forbidden_arcanus.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.world.feature.BigFungyssFeature;
import com.stal111.forbidden_arcanus.world.feature.MegaFungyssFeature;
import com.stal111.forbidden_arcanus.world.feature.PetrifiedRootFeature;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.world.placement.DimensionConfig;
import com.stal111.forbidden_arcanus.world.placement.DimensionPlacement;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ForbiddenArcanus.MOD_ID);
    public static final DeferredRegister<Placement<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> PETRIFIED_ROOT = register("petrified_root", () -> new PetrifiedRootFeature(NoFeatureConfig.field_236558_a_));
    public static final RegistryObject<Feature<BigFungyssFeatureConfig>> BIG_FUNGYSS = register("big_fungyss", () -> new BigFungyssFeature(BigFungyssFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BigFungyssFeatureConfig>> MEGA_FUNGYSS = register("mega_fungyss", () -> new MegaFungyssFeature(BigFungyssFeatureConfig.CODEC));

    public static final RegistryObject<DimensionPlacement> DIMENSION_PLACEMENT = PLACEMENTS.register("dimension_placement", () -> new DimensionPlacement(DimensionConfig.CODEC));

    private static <T extends IFeatureConfig> RegistryObject<Feature<T>> register(String name, Supplier<Feature<T>> feature) {
        return FEATURES.register(name, feature);
    }
}