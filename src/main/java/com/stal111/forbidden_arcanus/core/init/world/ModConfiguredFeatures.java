package com.stal111.forbidden_arcanus.core.init.world;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig;
import com.stal111.forbidden_arcanus.common.world.feature.config.CherryTreeConfiguration;
import com.stal111.forbidden_arcanus.common.world.feature.foliageplacers.CherryFoliagePlacer;
import com.stal111.forbidden_arcanus.common.world.feature.trunkplacers.CherryTrunkPlacer;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.datapack.DatapackRegistryHelper;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.Supplier;

/**
 * Mod Configured Features <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures
 *
 * @author stal111
 */
public class ModConfiguredFeatures extends DatapackRegistryClass<ConfiguredFeature<?, ?>> {

    public static final DatapackRegistryHelper<ConfiguredFeature<?, ?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.CONFIGURED_FEATURE);

    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCANE_CRYSTAL_ORE = HELPER.createKey("ore_arcane_crystal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RUNIC_STONE = HELPER.createKey("ore_rune");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARKSTONE = HELPER.createKey("ore_darkstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARCANE_GILDED_DARKSTONE = HELPER.createKey("ore_arcane_gilded_darkstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STELLA_ARCANUM = HELPER.createKey("ore_stella_arcanum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> XPETRIFIED_ORE = HELPER.createKey("ore_xpetrified");


    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_CHERRY = HELPER.createKey("small_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CHERRY = HELPER.createKey("large_cherry");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM = HELPER.createKey("aurum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_ORCHID = HELPER.createKey("flower_yellow_orchid");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EDELWOOD = HELPER.createKey("edelwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_ROOT = HELPER.createKey("petrified_root");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_FUNGYSS_0 = HELPER.createKey("big_fungyss_0");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_FUNGYSS_1 = HELPER.createKey("big_fungyss_1");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGYSS_0 = HELPER.createKey("mega_fungyss_0");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_FUNGYSS_1 = HELPER.createKey("mega_fungyss_1");

    public ModConfiguredFeatures(BootstapContext<ConfiguredFeature<?, ?>> context) {
        super(context);
    }

    private static SimpleWeightedRandomList.Builder<BlockState> weightedBlockStateBuilder() {
        return SimpleWeightedRandomList.builder();
    }

    @Override
    public void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneOreReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateOreReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> arcaneCrystalTargetList = List.of(OreConfiguration.target(stoneOreReplaceables, States.ARCANE_CRYSTAL_ORE.get()), OreConfiguration.target(deepslateOreReplaceables, States.DEEPSLATE_ARCANE_CRYSTAL_ORE.get()));
        List<OreConfiguration.TargetBlockState> runicStoneTargetList = List.of(OreConfiguration.target(stoneOreReplaceables, States.RUNIC_STONE.get()), OreConfiguration.target(deepslateOreReplaceables, States.RUNIC_DEEPSLATE.get()), OreConfiguration.target(new BlockMatchTest(ModBlocks.DARKSTONE.get()), States.RUNIC_DARKSTONE.get()));
        List<OreConfiguration.TargetBlockState> darkstoneTargetList = List.of(OreConfiguration.target(stoneOreReplaceables, States.DARKSTONE.get()), OreConfiguration.target(deepslateOreReplaceables, States.DARKSTONE.get()));
        List<OreConfiguration.TargetBlockState> stellaArcanumTargetList = List.of(OreConfiguration.target(stoneOreReplaceables, States.STELLA_ARCANUM.get()), OreConfiguration.target(deepslateOreReplaceables, States.STELLA_ARCANUM.get()));
        List<OreConfiguration.TargetBlockState> xpetrifiedTargetList = List.of(OreConfiguration.target(stoneOreReplaceables, States.XPETRIFIED_ORE.get()), OreConfiguration.target(deepslateOreReplaceables, States.XPETRIFIED_ORE.get()));

        context.register(ARCANE_CRYSTAL_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(arcaneCrystalTargetList, 5)));
        context.register(RUNIC_STONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(runicStoneTargetList, 3)));
        context.register(DARKSTONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(darkstoneTargetList, 20)));
        context.register(ARCANE_GILDED_DARKSTONE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(FillerBlockTypes.DARKSTONE, States.ARCANE_GILDED_DARKSTONE.get(), 4)));
        context.register(STELLA_ARCANUM, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(stellaArcanumTargetList, 3)));
        context.register(XPETRIFIED_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(xpetrifiedTargetList, 3)));
        context.register(SMALL_CHERRY, new ConfiguredFeature<>(Feature.TREE, new CherryTreeConfiguration(BlockStateProvider.simple(ModBlocks.CHERRY_LOG.get()), BlockStateProvider.simple(ModBlocks.THIN_CHERRY_LOG.get()), new CherryTrunkPlacer(3, 1, 1), BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.get()), new CherryFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))));
        context.register(LARGE_CHERRY, new ConfiguredFeature<>(Feature.TREE, new CherryTreeConfiguration(BlockStateProvider.simple(ModBlocks.CHERRY_LOG.get()), BlockStateProvider.simple(ModBlocks.THIN_CHERRY_LOG.get()), new CherryTrunkPlacer(4, 2, 2), BlockStateProvider.simple(ModBlocks.CHERRY_LEAVES.get()), new CherryFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)), new TwoLayersFeatureSize(1, 0, 2))));
        context.register(AURUM, new ConfiguredFeature<>(Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(BlockStateProvider.simple(ModBlocks.AURUM_LOG.get()), new FancyTrunkPlacer(3, 11, 0), new WeightedStateProvider(weightedBlockStateBuilder().add(States.MYSTERYWOOD_LEAVES.get(), 4).add(States.NUGGETY_MYSTERYWOOD_LEAVES.get(), 1)), new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4), new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines().build()));
        context.register(YELLOW_ORCHID, new ConfiguredFeature<>(Feature.FLOWER, new RandomPatchConfiguration(64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.YELLOW_ORCHID.get()))))));
        context.register(EDELWOOD, new ConfiguredFeature<>(ModFeatures.EDELWOOD.get(), NoneFeatureConfiguration.INSTANCE));
        context.register(PETRIFIED_ROOT, new ConfiguredFeature<>(ModFeatures.PETRIFIED_ROOT.get(), new BlockStateConfiguration(States.PETRIFIED_ROOT.get())));
        context.register(BIG_FUNGYSS_0, new ConfiguredFeature<>(ModFeatures.BIG_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 0)));
        context.register(BIG_FUNGYSS_1, new ConfiguredFeature<>(ModFeatures.BIG_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 1)));
        context.register(MEGA_FUNGYSS_0, new ConfiguredFeature<>(ModFeatures.MEGA_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 0)));
        context.register(MEGA_FUNGYSS_1, new ConfiguredFeature<>(ModFeatures.MEGA_FUNGYSS.get(), new BigFungyssFeatureConfig(SimpleStateProvider.simple(States.FUNGYSS_BLOCK.get()), SimpleStateProvider.simple(States.FUNGYSS_STEM.get()), SimpleStateProvider.simple(States.FUNGYSS_HYPHAE.get()), 1)));

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
        private static final Supplier<BlockState> ARCANE_GILDED_DARKSTONE = () -> ModBlocks.GILDED_DARKSTONE.get().defaultBlockState();
        private static final Supplier<BlockState> STELLA_ARCANUM = () -> ModBlocks.STELLA_ARCANUM.get().defaultBlockState();
        private static final Supplier<BlockState> XPETRIFIED_ORE = () -> ModBlocks.XPETRIFIED_ORE.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_LOG = () -> ModBlocks.CHERRY_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_LEAVES = () -> ModBlocks.CHERRY_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> CHERRYWOOD_SAPLING = () -> ModBlocks.CHERRY_SAPLING.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_LOG = () -> ModBlocks.AURUM_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_LEAVES = () -> ModBlocks.AURUM_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> NUGGETY_MYSTERYWOOD_LEAVES = () -> ModBlocks.NUGGETY_AURUM_LEAVES.get().defaultBlockState();
        private static final Supplier<BlockState> MYSTERYWOOD_SAPLING = () -> ModBlocks.AURUM_SAPLING.get().defaultBlockState();
        private static final Supplier<BlockState> YELLOW_ORCHID = () -> ModBlocks.YELLOW_ORCHID.get().defaultBlockState();
        private static final Supplier<BlockState> EDELWOOD_LOG = () -> ModBlocks.EDELWOOD_LOG.get().defaultBlockState();
        private static final Supplier<BlockState> PETRIFIED_ROOT = () -> ModBlocks.PETRIFIED_ROOT.get().defaultBlockState();
        private static final Supplier<BlockState> FUNGYSS_BLOCK = () -> ModBlocks.FUNGYSS_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
        private static final Supplier<BlockState> FUNGYSS_STEM = () -> ModBlocks.FUNGYSS_STEM.get().defaultBlockState();
        private static final Supplier<BlockState> FUNGYSS_HYPHAE = () -> ModBlocks.FUNGYSS_HYPHAE.get().defaultBlockState();
    }
}
