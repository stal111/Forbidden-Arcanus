package com.stal111.forbidden_arcanus.common.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.world.feature.treedecorators.CherryVinesDecorator;
import com.stal111.forbidden_arcanus.common.world.feature.treedecorators.LeafCarpetDecorator;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

import java.util.List;

/**
 * Cherry Tree Configuration <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.config.CherryTreeConfiguration
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-09
 */
public class CherryTreeConfiguration extends TreeConfiguration {

    public static final Codec<CherryTreeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((configuration) -> {
            return configuration.trunkProvider;
        }), BlockStateProvider.CODEC.fieldOf("thin_trunk_provider").forGetter(configuration -> {
            return configuration.thinTrunkProvider;
        }), TrunkPlacer.CODEC.fieldOf("trunk_placer").forGetter((configuration) -> {
            return configuration.trunkPlacer;
        }), BlockStateProvider.CODEC.fieldOf("foliage_provider").forGetter((configuration) -> {
            return configuration.foliageProvider;
        }), FoliagePlacer.CODEC.fieldOf("foliage_placer").forGetter((configuration) -> {
            return configuration.foliagePlacer;
        }), BlockStateProvider.CODEC.fieldOf("dirt_provider").forGetter((configuration) -> {
            return configuration.dirtProvider;
        }), FeatureSize.CODEC.fieldOf("minimum_size").forGetter((configuration) -> {
            return configuration.minimumSize;
        }), TreeDecorator.CODEC.listOf().fieldOf("decorators").forGetter((configuration) -> {
            return configuration.decorators;
        }), Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((configuration) -> {
            return configuration.ignoreVines;
        }), Codec.BOOL.fieldOf("force_dirt").orElse(false).forGetter((configuration) -> {
            return configuration.forceDirt;
        })).apply(instance, CherryTreeConfiguration::new);
    });

    public final BlockStateProvider thinTrunkProvider;

    public CherryTreeConfiguration(BlockStateProvider trunkProvider, BlockStateProvider thinTrunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, FeatureSize featureSize) {
        this(trunkProvider, thinTrunkProvider, trunkPlacer, foliageProvider, foliagePlacer, BlockStateProvider.simple(Blocks.DIRT), featureSize, List.of(new LeafCarpetDecorator(BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LEAF_CARPET.get())), CherryVinesDecorator.INSTANCE), true, false);
    }

    public CherryTreeConfiguration(BlockStateProvider trunkProvider, BlockStateProvider thinTrunkProvider, TrunkPlacer trunkPlacer, BlockStateProvider foliageProvider, FoliagePlacer foliagePlacer, BlockStateProvider dirtProvider, FeatureSize featureSize, List<TreeDecorator> decorators, boolean ignoreVines, boolean forceDirt) {
        super(trunkProvider, trunkPlacer, foliageProvider, foliagePlacer, dirtProvider, featureSize, decorators, ignoreVines, forceDirt);
        this.thinTrunkProvider = thinTrunkProvider;
    }
}
