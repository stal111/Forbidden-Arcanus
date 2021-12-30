package com.stal111.forbidden_arcanus.core.init.world;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.ForkingTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.OptionalInt;

/**
 * Mod Configured Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures
 *
 * @author stal111
 * @version 1.18.1 - 2.0.0
 */
public class ModConfiguredFeatures {

    private static final RuleTest DARKSTONE_ORE_REPLACEABLES = new TagMatchTest(ModTags.Blocks.DARKSTONE_ORE_REPLACEABLES);

    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_ARCANE_CRYSTAL_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.ARCANE_CRYSTAL_ORE), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.DEEPSLATE_ARCANE_CRYSTAL_ORE));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_RUNIC_STONE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.RUNIC_STONE), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.RUNIC_DEEPSLATE), OreConfiguration.target(DARKSTONE_ORE_REPLACEABLES, States.RUNIC_DARKSTONE));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_DARKSTONE_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.DARKSTONE), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.DARKSTONE));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_STELLA_ARCANUM_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.STELLA_ARCANUM), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.STELLA_ARCANUM));
    public static final ImmutableList<OreConfiguration.TargetBlockState> ORE_XPETRIFIED_TARGET_LIST = ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.XPETRIFIED_ORE), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.XPETRIFIED_ORE));

    public static final ConfiguredFeature<?, ?> ARCANE_CRYSTAL_ORE = register("ore_arcane_crystal", Feature.ORE.configured(new OreConfiguration(ORE_ARCANE_CRYSTAL_TARGET_LIST, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())));
    public static final ConfiguredFeature<?, ?> RUNIC_STONE = register("ore_rune", Feature.ORE.configured(new OreConfiguration(ORE_RUNIC_STONE_TARGET_LIST, WorldGenConfig.RUNIC_STONE_MAX_VEIN_SIZE.get())));
    public static final ConfiguredFeature<?, ?> DARKSTONE = register("ore_darkstone", Feature.ORE.configured(new OreConfiguration(ORE_DARKSTONE_TARGET_LIST, WorldGenConfig.DARKSTONE_MAX_VEIN_SIZE.get())));
    public static final ConfiguredFeature<?, ?> ARCANE_GILDED_DARKSTONE = register("ore_arcane_gilded_darkstone", Feature.ORE.configured(new OreConfiguration(FillerBlockTypes.DARKSTONE, States.ARCANE_GILDED_DARKSTONE, WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())));
    public static final ConfiguredFeature<?, ?> STELLA_ARCANUM = register("ore_stella_arcanum", Feature.ORE.configured(new OreConfiguration(ORE_STELLA_ARCANUM_TARGET_LIST, WorldGenConfig.STELLA_ARCANUM_MAX_VEIN_SIZE.get())));
    public static final ConfiguredFeature<?, ?> XPETRIFIED_ORE = register("ore_xpetrified", Feature.ORE.configured(new OreConfiguration(ORE_XPETRIFIED_TARGET_LIST, 3)));

    public static final ConfiguredFeature<TreeConfiguration, ?> CHERRYWOOD = register("cherrywood", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LOG.get()), new ForkingTrunkPlacer(5, 2, 2), BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LEAVES.get()), new AcaciaFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))).ignoreVines().build()));
    public static final ConfiguredFeature<TreeConfiguration, ?> MYSTERYWOOD = register("mysterywood", Feature.TREE.configured((new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MYSTERYWOOD_LOG.get()), new FancyTrunkPlacer(3, 11, 0), new WeightedStateProvider(weightedBlockStateBuilder().add(States.MYSTERYWOOD_LEAVES, 4).add(States.NUGGETY_MYSTERYWOOD_LEAVES, 1)), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4)))).ignoreVines().build()));

    public static final ConfiguredFeature<?, ?> YELLOW_ORCHID = register("flower_yellow_orchid", Feature.FLOWER.configured(new RandomPatchConfiguration(64, 6, 2, () -> {
        return Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.YELLOW_ORCHID.get()))).onlyWhenEmpty();
    })));
    public static final ConfiguredFeature<?, ?> EDELWOOD = register("edelwood", ModFeatures.EDELWOOD.get().configured(NoneFeatureConfiguration.INSTANCE));
    public static final ConfiguredFeature<?, ?> PETRIFIED_ROOT = register("petrified_root", ModFeatures.PETRIFIED_ROOT.get().configured(new BlockStateConfiguration(States.PETRIFIED_ROOT)));

   // public static final ConfiguredFeature<?, ?> CHERRYWOOD_TREES = register("trees_cherrywood", CHERRYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
   // public static final ConfiguredFeature<?, ?> MYSTERYWOOD_TREES = register("trees_mysterywood", MYSTERYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));

    public static final ConfiguredFeature<?, ?> BIG_FUNGYSS_0 = register("big_fungyss_0", ModFeatures.BIG_FUNGYSS.get().configured(new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK), SimpleStateProvider.simple(States.FUNGYSS_STEM), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE), 0)));
    public static final ConfiguredFeature<?, ?> BIG_FUNGYSS_1 = register("big_fungyss_1", ModFeatures.BIG_FUNGYSS.get().configured(new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK), SimpleStateProvider.simple(States.FUNGYSS_STEM), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE), 1)));
    public static final ConfiguredFeature<?, ?> MEGA_FUNGYSS_0 = register("mega_fungyss_0", ModFeatures.MEGA_FUNGYSS.get().configured(new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK), SimpleStateProvider.simple(States.FUNGYSS_STEM), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE), 0)));
    public static final ConfiguredFeature<?, ?> MEGA_FUNGYSS_1 = register("mega_fungyss_1", ModFeatures.MEGA_FUNGYSS.get().configured(new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK), SimpleStateProvider.simple(States.FUNGYSS_STEM), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE), 1)));

    private static <FC extends FeatureConfiguration> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> configuredFeature) {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), configuredFeature);
    }

    public static void load() {

    }

    private static SimpleWeightedRandomList.Builder<BlockState> weightedBlockStateBuilder() {
        return SimpleWeightedRandomList.builder();
    }

    public static final class FillerBlockTypes {
        private static final RuleTest DARKSTONE = new BlockMatchTest(ModBlocks.DARKSTONE.get());
    }

    public static final class States {
        private static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.defaultBlockState();
        private static final BlockState STONE = Blocks.STONE.defaultBlockState();
        private static final BlockState ARCANE_CRYSTAL_ORE = ModBlocks.ARCANE_CRYSTAL_ORE.get().defaultBlockState();
        private static final BlockState DEEPSLATE_ARCANE_CRYSTAL_ORE = ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get().defaultBlockState();
        private static final BlockState RUNIC_STONE = ModBlocks.RUNIC_STONE.get().defaultBlockState();
        private static final BlockState RUNIC_DEEPSLATE = ModBlocks.RUNIC_DEEPSLATE.get().defaultBlockState();
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
