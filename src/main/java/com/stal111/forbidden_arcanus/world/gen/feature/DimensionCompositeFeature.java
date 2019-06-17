package com.stal111.forbidden_arcanus.world.gen.feature;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class DimensionCompositeFeature<FC extends IFeatureConfig, D extends IPlacementConfig> extends ConfiguredFeature<FC> {

    private final DimensionType dimension;

    public DimensionCompositeFeature(Feature<FC> featureIn, FC featureConfigIn, @Nonnull DimensionType dimension) {
        super(featureIn, featureConfigIn);
        this.dimension = dimension;
    }
    
    @Override
    public boolean place(IWorld p_222734_1_, ChunkGenerator<? extends GenerationSettings> p_222734_2_,
    		Random p_222734_3_, BlockPos p_222734_4_) {
    	return super.place(p_222734_1_, p_222734_2_, p_222734_3_, p_222734_4_);
    }

}