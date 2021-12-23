package com.stal111.forbidden_arcanus.init.world;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.world.placement.DimensionConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.Features;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.blockplacers.ColumnPlacer;
import net.minecraft.world.level.levelgen.feature.blockplacers.SimpleBlockPlacer;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.world.level.levelgen.placement.FrequencyWithExtraChanceDecoratorConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.OptionalInt;

/**
 * Mod Configured Features
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures
 *
 * @author stal111
 * @version 2.0.0
 */
public class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> ARCANE_CRYSTAL_ORE = register("ore_arcane_crystal", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.ARCANE_CRYSTAL_ORE, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_HEIGHT.get())).squared().count(WorldGenConfig.ARCANE_CRYSTAL_ORE_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> RUNESTONE = register("ore_rune", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.RUNIC_STONE, WorldGenConfig.RUNESTONE_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.RUNESTONE_MAX_HEIGHT.get())).squared().count(WorldGenConfig.RUNESTONE_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> DARKSTONE = register("ore_darkstone", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.DARKSTONE, WorldGenConfig.DARKSTONE_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.DARKSTONE_MAX_HEIGHT.get())).squared().count(WorldGenConfig.DARKSTONE_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> ARCANE_GILDED_DARKSTONE = register("ore_arcane_gilded_darkstone", Feature.ORE.configured(new OreConfiguration(FillerBlockTypes.DARKSTONE, States.ARCANE_GILDED_DARKSTONE, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.ARCANE_GILDED_DARKSTONE_MAX_HEIGHT.get())).squared().count(WorldGenConfig.ARCANE_GILDED_DARKSTONE_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> DARK_RUNESTONE = register("ore_dark_rune", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.RUNIC_DARKSTONE, WorldGenConfig.DARK_RUNESTONE_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.DARK_RUNESTONE_MAX_HEIGHT.get())).squared().count(WorldGenConfig.DARK_RUNESTONE_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> STELLA_ARCANUM = register("ore_stella_arcanum", Feature.ORE.configured(new OreConfiguration(OreConfiguration.Predicates.NATURAL_STONE, States.STELLA_ARCANUM, WorldGenConfig.STELLA_ARCANUM_MAX_VEIN_SIZE.get())).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(WorldGenConfig.STELLA_ARCANUM_MAX_HEIGHT.get())).squared().count(WorldGenConfig.STELLA_ARCANUM_COUNT.get()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<?, ?> XPETRIFIED_ORE = register("ore_xpetrified", Feature.REPLACE_SINGLE_BLOCK.configured(new ReplaceBlockConfiguration(States.STONE, States.XPETRIFIED_ORE)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.oreList))));
    public static final ConfiguredFeature<TreeConfiguration, ?> CHERRYWOOD = (ConfiguredFeature<TreeConfiguration, ?>) register("cherrywood", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.CHERRYWOOD_LOG), new ForkingTrunkPlacer(5, 2, 2), new SimpleStateProvider(States.CHERRYWOOD_LEAVES), new SimpleStateProvider(States.CHERRYWOOD_SAPLING), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build()).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<TreeConfiguration, ?> MYSTERYWOOD = (ConfiguredFeature<TreeConfiguration, ?>) register("mysterywood", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(new SimpleStateProvider(States.MYSTERYWOOD_LOG), new FancyTrunkPlacer(3, 11, 0), new WeightedStateProvider(weightedBlockStateBuilder().add(States.MYSTERYWOOD_LEAVES, 4).add(States.NUGGETY_MYSTERYWOOD_LEAVES, 1)), new SimpleStateProvider(States.MYSTERYWOOD_SAPLING), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build())).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList)));

    public static final ConfiguredFeature<?, ?> YELLOW_ORCHID = register("flower_yellow_orchid", Feature.FLOWER.configured(Configs.YELLOW_ORCHID).decorated(Features.Decorators.ADD_32).decorated(Features.Decorators.HEIGHTMAP_SQUARE).count(14)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList)));
    public static final ConfiguredFeature<?, ?> EDELWOOD_LOG = register("edelwood_log", Feature.RANDOM_PATCH.configured(Configs.EDELWOOD_LOG).count(3).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<?, ?> PETRIFIED_ROOT = register("petrified_root", ModFeatures.PETRIFIED_ROOT.get().configured(new BlockStateConfiguration(States.PETRIFIED_ROOT)).rangeUniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(50)).squared().count(50).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));

    public static final ConfiguredFeature<?, ?> CHERRYWOOD_TREES = register("trees_cherrywood", CHERRYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<?, ?> MYSTERYWOOD_TREES = register("trees_mysterywood", MYSTERYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));

    public static final ConfiguredFeature<?, ?> BIG_FUNGYSS_0 = register("big_fungyss_0", ModFeatures.BIG_FUNGYSS.get().configured(new BigFungyssFeatureConfig(new SimpleStateProvider(States.FUNGYSS_BLOCK), new SimpleStateProvider(States.FUNGYSS_STEM), new SimpleStateProvider(States.FUNGYSS_HYPHAE), 0)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<?, ?> BIG_FUNGYSS_1 = register("big_fungyss_1", ModFeatures.BIG_FUNGYSS.get().configured(new BigFungyssFeatureConfig(new SimpleStateProvider(States.FUNGYSS_BLOCK), new SimpleStateProvider(States.FUNGYSS_STEM), new SimpleStateProvider(States.FUNGYSS_HYPHAE), 1)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<?, ?> MEGA_FUNGYSS_0 = register("mega_fungyss_0", ModFeatures.MEGA_FUNGYSS.get().configured(new BigFungyssFeatureConfig(new SimpleStateProvider(States.FUNGYSS_BLOCK), new SimpleStateProvider(States.FUNGYSS_STEM), new SimpleStateProvider(States.FUNGYSS_HYPHAE), 0)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
    public static final ConfiguredFeature<?, ?> MEGA_FUNGYSS_1 = register("mega_fungyss_1", ModFeatures.MEGA_FUNGYSS.get().configured(new BigFungyssFeatureConfig(new SimpleStateProvider(States.FUNGYSS_BLOCK), new SimpleStateProvider(States.FUNGYSS_STEM), new SimpleStateProvider(States.FUNGYSS_HYPHAE), 1)).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), configuredFeature);
    }

    public static void load() {

    }

    private static SimpleWeightedRandomList.Builder<BlockState> weightedBlockStateBuilder() {
        return SimpleWeightedRandomList.builder();
    }

    public static final class Configs {
        public static final RandomPatchConfiguration YELLOW_ORCHID = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(States.YELLOW_ORCHID), SimpleBlockPlacer.INSTANCE)).tries(10).build();
        public static final RandomPatchConfiguration EDELWOOD_LOG = (new RandomPatchConfiguration.GrassConfigurationBuilder(new SimpleStateProvider(States.EDELWOOD_LOG), new ColumnPlacer(ConstantInt.of(3)))).tries(3).whitelist(ImmutableSet.of(States.GRASS_BLOCK.getBlock())).xspread(0).yspread(0).zspread(0).noProjection().build();
    }

    public static final class FillerBlockTypes {
        private static final RuleTest DARKSTONE = new BlockMatchTest(ModBlocks.DARKSTONE.get());
    }

    public static final class States {
        private static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
        private static final BlockState STONE = Blocks.STONE.defaultBlockState();
        private static final BlockState ARCANE_CRYSTAL_ORE = ModBlocks.ARCANE_CRYSTAL_ORE.get().defaultBlockState();
        private static final BlockState RUNIC_STONE = ModBlocks.RUNIC_STONE.get().defaultBlockState();
        private static final BlockState RUNIC_DARKSTONE = ModBlocks.RUNIC_DARKSTONE.get().defaultBlockState();
        private static final BlockState DARKSTONE = ModBlocks.DARKSTONE.get().defaultBlockState();
        private static final BlockState ARCANE_GILDED_DARKSTONE = ModBlocks.ARCANE_GILDED_DARKSTONE.get().defaultBlockState();
        private static final BlockState STELLA_ARCANUM = ModBlocks.STELLA_ARCANUM.get().defaultBlockState();
        private static final BlockState XPETRIFIED_ORE = ModBlocks.XPETRIFIED_ORE.get().defaultBlockState();
        private static final BlockState CHERRYWOOD_LOG = ModBlocks.CHERRYWOOD_LOG.get().defaultBlockState();
        private static final BlockState CHERRYWOOD_LEAVES = ModBlocks.CHERRYWOOD_LEAVES.get().defaultBlockState();
        private static final BlockState CHERRYWOOD_SAPLING = ModBlocks.CHERRYWOOD_SAPLING.get().defaultBlockState();
        private static final BlockState MYSTERYWOOD_LOG = ModBlocks.MYSTERYWOOD_LOG.get().defaultBlockState();
        private static final BlockState MYSTERYWOOD_LEAVES = ModBlocks.MYSTERYWOOD_LEAVES.get().defaultBlockState();
        private static final BlockState NUGGETY_MYSTERYWOOD_LEAVES = ModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get().defaultBlockState();
        private static final BlockState MYSTERYWOOD_SAPLING = ModBlocks.MYSTERYWOOD_SAPLING.get().defaultBlockState();
        private static final BlockState YELLOW_ORCHID = ModBlocks.YELLOW_ORCHID.get().defaultBlockState();
        private static final BlockState EDELWOOD_LOG = ModBlocks.EDELWOOD_LOG.get().defaultBlockState();
        private static final BlockState PETRIFIED_ROOT = ModBlocks.PETRIFIED_ROOT.get().defaultBlockState();
        private static final BlockState FUNGYSS_BLOCK = ModBlocks.FUNGYSS_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
        private static final BlockState FUNGYSS_STEM = ModBlocks.FUNGYSS_STEM.get().defaultBlockState();
        private static final BlockState FUNGYSS_HYPHAE = ModBlocks.FUNGYSS_HYPHAE.get().defaultBlockState();
    }
}
