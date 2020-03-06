package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.world.gen.feature.CherrywoodTreeFeature;
import com.stal111.forbidden_arcanus.world.gen.feature.PetrifiedRootFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public enum ModFeatures {
    CHERRYWOOD_TREE(new CherrywoodTreeFeature((dynamic -> TreeFeatureConfig.func_227338_a_(dynamic)))),
    PETRIFIED_ROOT(new PetrifiedRootFeature(NoFeatureConfig::deserialize));

    private final Feature feature;

    ModFeatures(Feature feature) {
        this.feature = feature;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public Feature getFeature() {
        if (feature.getRegistryName() == null) {
            feature.setRegistryName(Main.MOD_ID + getName());
        }
        return feature;
    }
}
