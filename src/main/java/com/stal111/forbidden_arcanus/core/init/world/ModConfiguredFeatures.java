package com.stal111.forbidden_arcanus.core.init.world;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.common.world.feature.config.CherryTreeConfiguration;
import com.stal111.forbidden_arcanus.common.world.feature.foliageplacers.CherryFoliagePlacer;
import com.stal111.forbidden_arcanus.common.world.feature.trunkplacers.CherryTrunkPlacer;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
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
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.RegistryHelper;

import java.util.OptionalInt;
import java.util.function.Supplier;

/**
 * Mod Configured Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 */
public class ModConfiguredFeatures implements RegistryClass {

    public static final RegistryHelper<ConfiguredFeature<?, ?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registry.CONFIGURED_FEATURE_REGISTRY);

    public static final Supplier<ImmutableList<OreConfiguration.TargetBlockState>> ORE_ARCANE_CRYSTAL_TARGET_LIST = () -> ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.ARCANE_CRYSTAL_ORE.get()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.DEEPSLATE_ARCANE_CRYSTAL_ORE.get()));
    public static final Supplier<ImmutableList<OreConfiguration.TargetBlockState>> ORE_RUNIC_STONE_TARGET_LIST = () -> ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.RUNIC_STONE.get()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.RUNIC_DEEPSLATE.get()), OreConfiguration.target(new BlockMatchTest(ModBlocks.DARKSTONE.get()), States.RUNIC_DARKSTONE.get()));
    public static final Supplier<ImmutableList<OreConfiguration.TargetBlockState>> ORE_DARKSTONE_TARGET_LIST = () -> ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.DARKSTONE.get()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.DARKSTONE.get()));
    public static final Supplier<ImmutableList<OreConfiguration.TargetBlockState>> ORE_STELLA_ARCANUM_TARGET_LIST = () -> ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.STELLA_ARCANUM.get()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.STELLA_ARCANUM.get()));
    public static final Supplier<ImmutableList<OreConfiguration.TargetBlockState>> ORE_XPETRIFIED_TARGET_LIST = () -> ImmutableList.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, States.XPETRIFIED_ORE.get()), OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, States.XPETRIFIED_ORE.get()));

    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ARCANE_CRYSTAL_ORE = HELPER.register("ore_arcane_crystal", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_ARCANE_CRYSTAL_TARGET_LIST.get(), WorldGenConfig.ARCANE_CRYSTAL_ORE_MAX_VEIN_SIZE.get())));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> RUNIC_STONE = HELPER.register("ore_rune", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_RUNIC_STONE_TARGET_LIST.get(), WorldGenConfig.RUNIC_STONE_MAX_VEIN_SIZE.get())));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> DARKSTONE = HELPER.register("ore_darkstone", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_DARKSTONE_TARGET_LIST.get(), WorldGenConfig.DARKSTONE_MAX_VEIN_SIZE.get())));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> ARCANE_GILDED_DARKSTONE = HELPER.register("ore_arcane_gilded_darkstone", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FillerBlockTypes.DARKSTONE, States.ARCANE_GILDED_DARKSTONE.get(), WorldGenConfig.ARCANE_GILDED_DARKSTONE_MAX_VEIN_SIZE.get())));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> STELLA_ARCANUM = HELPER.register("ore_stella_arcanum", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_STELLA_ARCANUM_TARGET_LIST.get(), WorldGenConfig.STELLA_ARCANUM_MAX_VEIN_SIZE.get())));
    public static final RegistryObject<ConfiguredFeature<OreConfiguration, ?>> XPETRIFIED_ORE = HELPER.register("ore_xpetrified", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(ORE_XPETRIFIED_TARGET_LIST.get(), WorldGenConfig.XPETRIFIED_ORE_MAX_VEIN_SIZE.get())));

    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> SMALL_CHERRYWOOD = HELPER.register("small_cherrywood", () -> new ConfiguredFeature<>(Feature.TREE, new CherryTreeConfiguration(BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LOG.get()), BlockStateProvider.simple(ModBlocks.THIN_CHERRYWOOD_LOG.get()), new CherryTrunkPlacer(3, 1, 1), BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LEAVES.get()), new CherryFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))));
    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> LARGE_CHERRYWOOD = HELPER.register("large_cherrywood", () -> new ConfiguredFeature<>(Feature.TREE, new CherryTreeConfiguration(BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LOG.get()), BlockStateProvider.simple(ModBlocks.THIN_CHERRYWOOD_LOG.get()), new CherryTrunkPlacer(4, 2, 2), BlockStateProvider.simple(ModBlocks.CHERRYWOOD_LEAVES.get()), new CherryFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))));

    public static final RegistryObject<ConfiguredFeature<TreeConfiguration, ?>> MYSTERYWOOD = HELPER.register("mysterywood", () -> new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.MYSTERYWOOD_LOG.get()), new FancyTrunkPlacer(3, 11, 0), new WeightedStateProvider(weightedBlockStateBuilder().add(States.MYSTERYWOOD_LEAVES.get(), 4).add(States.NUGGETY_MYSTERYWOOD_LEAVES.get(), 1)), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build()));

    public static final RegistryObject<ConfiguredFeature<RandomPatchConfiguration, ?>> YELLOW_ORCHID = HELPER.register("flower_yellow_orchid", () -> new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.YELLOW_ORCHID.get()))))));
    public static final RegistryObject<ConfiguredFeature<NoneFeatureConfiguration, ?>> EDELWOOD = HELPER.register("edelwood", () -> new ConfiguredFeature<>(ModFeatures.EDELWOOD.get(), NoneFeatureConfiguration.INSTANCE));
    public static final RegistryObject<ConfiguredFeature<BlockStateConfiguration, ?>> PETRIFIED_ROOT = HELPER.register("petrified_root", () -> new ConfiguredFeature<>(ModFeatures.PETRIFIED_ROOT.get(), new BlockStateConfiguration(States.PETRIFIED_ROOT.get())));

   // public static final ConfiguredFeature<?, ?> CHERRYWOOD_TREES = register("trees_cherrywood", CHERRYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));
   // public static final ConfiguredFeature<?, ?> MYSTERYWOOD_TREES = register("trees_mysterywood", MYSTERYWOOD.decorated(Features.Decorators.HEIGHTMAP_SQUARE).decorated(FeatureDecorator.COUNT_EXTRA.configured(new FrequencyWithExtraChanceDecoratorConfiguration(0, 0.1F, 1))).decorated(ModFeatures.DIMENSION_PLACEMENT.get().configured(new DimensionConfig(WorldGenConfig.treeList))));

    public static final RegistryObject<ConfiguredFeature<BigFungyssFeatureConfig, ?>> BIG_FUNGYSS_0 = HELPER.register("big_fungyss_0", () -> new ConfiguredFeature<>(ModFeatures.BIG_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 0)));
    public static final RegistryObject<ConfiguredFeature<BigFungyssFeatureConfig, ?>> BIG_FUNGYSS_1 = HELPER.register("big_fungyss_1", () -> new ConfiguredFeature<>(ModFeatures.BIG_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 1)));
    public static final RegistryObject<ConfiguredFeature<BigFungyssFeatureConfig, ?>> MEGA_FUNGYSS_0 = HELPER.register("mega_fungyss_0", () -> new ConfiguredFeature<>(ModFeatures.MEGA_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 0)));
    public static final RegistryObject<ConfiguredFeature<BigFungyssFeatureConfig, ?>> MEGA_FUNGYSS_1 = HELPER.register("mega_fungyss_1", () -> new ConfiguredFeature<>(ModFeatures.MEGA_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 1)));

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(String name, F feature, FC configuration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name).toString(), new ConfiguredFeature<>(feature, configuration));

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
        private static final Supplier<BlockState> ARCANE_CRYSTAL_ORE = () -> ModBlocks.ARCANE_CRYSTAL_ORE.get().defaultBlockState();
        private static final Supplier<BlockState> DEEPSLATE_ARCANE_CRYSTAL_ORE = () -> ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get().defaultBlockState();
        private static final Supplier<BlockState> RUNIC_STONE = () -> ModBlocks.RUNIC_STONE.get().defaultBlockState();
        private static final Supplier<BlockState> RUNIC_DEEPSLATE = () -> ModBlocks.RUNIC_DEEPSLATE.get().defaultBlockState();
        private static final Supplier<BlockState> RUNIC_DARKSTONE = () -> ModBlocks.RUNIC_DARKSTONE.get().defaultBlockState();
        private static final Supplier<BlockState> DARKSTONE = () -> ModBlocks.DARKSTONE.get().defaultBlockState();
        private static final Supplier<BlockState> ARCANE_GILDED_DARKSTONE = () -> ModBlocks.ARCANE_GILDED_DARKSTONE.get().defaultBlockState();
        private static final Supplier<BlockState> STELLA_ARCANUM = () -> ModBlocks.STELLA_ARCANUM.get().defaultBlockState();
        private static final Supplier<BlockState> XPETRIFIED_ORE = () -> ModBlocks.XPETRIFIED_ORE.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_LOG = () -> ModBlocks.CHERRYWOOD_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_LEAVES = () -> ModBlocks.CHERRYWOOD_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_SAPLING = () -> ModBlocks.CHERRYWOOD_SAPLING.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_LOG = () -> ModBlocks.MYSTERYWOOD_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_LEAVES = () -> ModBlocks.MYSTERYWOOD_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> NUGGETY_MYSTERYWOOD_LEAVES = () -> ModBlocks.NUGGETY_MYSTERYWOOD_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_SAPLING = () -> ModBlocks.MYSTERYWOOD_SAPLING.get().defaultBlockState();
        private static final Supplier<BlockState> YELLOW_ORCHID = () -> ModBlocks.YELLOW_ORCHID.get().defaultBlockState();
        private static final Supplier<BlockState> EDELWOOD_LOG = () -> ModBlocks.EDELWOOD_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> PETRIFIED_ROOT = () -> ModBlocks.PETRIFIED_ROOT.get().defaultBlockState();
        private static final Supplier<BlockState> FUNGYSS_BLOCK = () -> ModBlocks.FUNGYSS_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
        private static final Supplier<BlockState> FUNGYSS_STEM = () -> ModBlocks.FUNGYSS_STEM.get().defaultBlockState();
        private static final Supplier<BlockState> FUNGYSS_HYPHAE = () -> ModBlocks.FUNGYSS_HYPHAE.get().defaultBlockState();
    }
}
