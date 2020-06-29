package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.world.gen.feature.PetrifiedRootFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Main.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> PETRIFIED_ROOT = register("petrified_root", () -> new PetrifiedRootFeature(NoFeatureConfig.field_236558_a_));

    private static <T extends IFeatureConfig> RegistryObject<Feature<T>> register(String name, Supplier<Feature<T>> feature) {
        return FEATURES.register(name, feature);
    }
}
