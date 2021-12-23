package com.stal111.forbidden_arcanus.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.EdelwoodFeature;
import com.stal111.forbidden_arcanus.world.feature.BigFungyssFeature;
import com.stal111.forbidden_arcanus.world.feature.MegaFungyssFeature;
import com.stal111.forbidden_arcanus.world.feature.RootFeature;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.world.placement.DimensionConfig;
import com.stal111.forbidden_arcanus.world.placement.DimensionPlacement;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, ForbiddenArcanus.MOD_ID);
    public static final DeferredRegister<FeatureDecorator<?>> PLACEMENTS = DeferredRegister.create(ForgeRegistries.DECORATORS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<Feature<BlockStateConfiguration>> PETRIFIED_ROOT = register("petrified_root", () -> new RootFeature(BlockStateConfiguration.CODEC));
    public static final RegistryObject<Feature<BigFungyssFeatureConfig>> BIG_FUNGYSS = register("big_fungyss", () -> new BigFungyssFeature(BigFungyssFeatureConfig.CODEC));
    public static final RegistryObject<Feature<BigFungyssFeatureConfig>> MEGA_FUNGYSS = register("mega_fungyss", () -> new MegaFungyssFeature(BigFungyssFeatureConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> EDELWOOD = register("edelwood", () -> new EdelwoodFeature(NoneFeatureConfiguration.CODEC));

    public static final RegistryObject<DimensionPlacement> DIMENSION_PLACEMENT = PLACEMENTS.register("dimension_placement", () -> new DimensionPlacement(DimensionConfig.CODEC));

    private static <T extends FeatureConfiguration> RegistryObject<Feature<T>> register(String name, Supplier<Feature<T>> feature) {
        return FEATURES.register(name, feature);
    }
}