package com.stal111.forbidden_arcanus.datagen.worldgen.feature

import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures
import com.stal111.forbidden_arcanus.common.world.feature.config.BigFungyssFeatureConfig
import com.stal111.forbidden_arcanus.common.world.feature.config.MeteoriteConfiguration
import com.stal111.forbidden_arcanus.core.init.ModBlocks
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures
import net.minecraft.core.HolderSet
import net.minecraft.data.worldgen.BootstrapContext
import net.minecraft.data.worldgen.placement.PlacementUtils
import net.minecraft.tags.BlockTags
import net.minecraft.util.random.WeightedList
import net.minecraft.util.valueproviders.ConstantInt
import net.minecraft.world.level.block.HugeMushroomBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.configurations.*
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest
import net.valhelsia.dataforge.RegistryDataProvider
import java.util.*
import java.util.function.Supplier

object ModConfiguredFeatures : RegistryDataProvider<ConfiguredFeature<*, *>> {
    override fun bootstrap(context: BootstrapContext<ConfiguredFeature<*, *>>) {
        val stoneOreReplaceables: RuleTest = TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES)
        val deepslateOreReplaceables: RuleTest = TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES)

        val arcaneCrystalTargetList = listOf(
            OreConfiguration.target(
                stoneOreReplaceables,
                ARCANE_CRYSTAL_ORE.get()
            ), OreConfiguration.target(deepslateOreReplaceables, DEEPSLATE_ARCANE_CRYSTAL_ORE.get())
        )
        val runicStoneTargetList = listOf(
            OreConfiguration.target(stoneOreReplaceables, RUNIC_STONE.get()),
            OreConfiguration.target(deepslateOreReplaceables, RUNIC_DEEPSLATE.get()),
            OreConfiguration.target(
                BlockMatchTest(ModBlocks.DARKSTONE.get()), RUNIC_DARKSTONE.get()
            )
        )
        val darkstoneTargetList = listOf(
            OreConfiguration.target(
                stoneOreReplaceables,
                DARKSTONE.get()
            ), OreConfiguration.target(deepslateOreReplaceables, DARKSTONE.get())
        )
        val stellaArcanumTargetList = listOf(
            OreConfiguration.target(
                stoneOreReplaceables,
                STELLA_ARCANUM.get()
            ), OreConfiguration.target(deepslateOreReplaceables, STELLA_ARCANUM.get())
        )

        context.register(
            BuiltInFeatures.ARCANE_CRYSTAL_ORE, ConfiguredFeature(
                Feature.ORE, OreConfiguration(arcaneCrystalTargetList, 5)
            )
        )
        context.register(
            BuiltInFeatures.RUNIC_STONE, ConfiguredFeature(
                Feature.ORE, OreConfiguration(runicStoneTargetList, 5, 0.5f)
            )
        )
        context.register(
            BuiltInFeatures.RUNIC_STONE_LOWER, ConfiguredFeature(
                Feature.ORE, OreConfiguration(runicStoneTargetList, 7, 0.3f)
            )
        )
        context.register(
            BuiltInFeatures.DARKSTONE, ConfiguredFeature(
                Feature.ORE, OreConfiguration(darkstoneTargetList, 20)
            )
        )
        context.register(
            BuiltInFeatures.STELLA_ARCANUM, ConfiguredFeature(
                Feature.ORE, OreConfiguration(stellaArcanumTargetList, 3)
            )
        )
        context.register(
            BuiltInFeatures.AURUM, ConfiguredFeature(
                Feature.TREE, TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(ModBlocks.AURUM_LOG.get()),
                    FancyTrunkPlacer(3, 11, 0),
                    WeightedStateProvider(
                        WeightedList.builder<BlockState>().add(MYSTERYWOOD_LEAVES.get(), 4)
                            .add(NUGGETY_MYSTERYWOOD_LEAVES.get(), 1)
                    ),
                    FancyFoliagePlacer(
                        ConstantInt.of(2), ConstantInt.of(4), 4
                    ),
                    TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
                ).ignoreVines().build()
            )
        )
        context.register(
            BuiltInFeatures.YELLOW_ORCHID,
            ConfiguredFeature(
                Feature.SIMPLE_BLOCK, SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.YELLOW_ORCHID.get()))
            )
        )
        context.register(
            BuiltInFeatures.EDELWOOD,
            ConfiguredFeature(
                ModFeatures.EDELWOOD.get(),
                NoneFeatureConfiguration.INSTANCE
            )
        )
        context.register(
            BuiltInFeatures.BIG_FUNGYSS_0,
            ConfiguredFeature(
                ModFeatures.BIG_FUNGYSS.get(), BigFungyssFeatureConfig(
                    SimpleStateProvider.simple(FUNGYSS_BLOCK.get()),
                    SimpleStateProvider.simple(FUNGYSS_STEM.get()),
                    SimpleStateProvider.simple(FUNGYSS_HYPHAE.get()),
                    0
                )
            )
        )
        context.register(
            BuiltInFeatures.BIG_FUNGYSS_1,
            ConfiguredFeature(
                ModFeatures.BIG_FUNGYSS.get(), BigFungyssFeatureConfig(
                    SimpleStateProvider.simple(FUNGYSS_BLOCK.get()),
                    SimpleStateProvider.simple(FUNGYSS_STEM.get()),
                    SimpleStateProvider.simple(FUNGYSS_HYPHAE.get()),
                    1
                )
            )
        )
        context.register(
            BuiltInFeatures.MEGA_FUNGYSS_0,
            ConfiguredFeature(
                ModFeatures.MEGA_FUNGYSS.get(), BigFungyssFeatureConfig(
                    SimpleStateProvider.simple(FUNGYSS_BLOCK.get()),
                    SimpleStateProvider.simple(FUNGYSS_STEM.get()),
                    SimpleStateProvider.simple(FUNGYSS_HYPHAE.get()),
                    0
                )
            )
        )
        context.register(
            BuiltInFeatures.MEGA_FUNGYSS_1,
            ConfiguredFeature(
                ModFeatures.MEGA_FUNGYSS.get(), BigFungyssFeatureConfig(
                    SimpleStateProvider.simple(FUNGYSS_BLOCK.get()),
                    SimpleStateProvider.simple(FUNGYSS_STEM.get()),
                    SimpleStateProvider.simple(FUNGYSS_HYPHAE.get()),
                    1
                )
            )
        )

        context.register(
            BuiltInFeatures.METEORITE,
            ConfiguredFeature(
                Feature.SIMPLE_RANDOM_SELECTOR,
                SimpleRandomFeatureConfiguration(
                    HolderSet.direct(
                        PlacementUtils.inlinePlaced(
                            ModFeatures.METEORITE.get(),
                            MeteoriteConfiguration(
                                2,
                                WeightedStateProvider(
                                    WeightedList.builder<BlockState>()
                                        .add(ModBlocks.DARKSTONE.get().defaultBlockState(), 3)
                                        .add(ModBlocks.METEORITE.get().defaultBlockState(), 1),
                                ),
                                8,
                                0.4F,
                                0.3F,
                                3,
                                0.12f
                            )
                        ),
                        PlacementUtils.inlinePlaced(
                            ModFeatures.METEORITE.get(),
                            MeteoriteConfiguration(
                                3,
                                WeightedStateProvider(
                                    WeightedList.builder<BlockState>()
                                        .add(ModBlocks.DARKSTONE.get().defaultBlockState(), 3)
                                        .add(ModBlocks.METEORITE.get().defaultBlockState(), 1),
                                ),
                                12,
                                0.45F,
                                0.3F,
                                4,
                                0.12f
                            )
                        ),
                        PlacementUtils.inlinePlaced(
                            ModFeatures.METEORITE.get(),
                            MeteoriteConfiguration(
                                4,
                                WeightedStateProvider(
                                    WeightedList.builder<BlockState>()
                                        .add(ModBlocks.DARKSTONE.get().defaultBlockState(), 3)
                                        .add(ModBlocks.METEORITE.get().defaultBlockState(), 1),
                                ),
                                15,
                                0.45F,
                                0.3F,
                                5,
                                0.12f
                            )
                        )
                    )
                )
            )
        )
    }

    private val ARCANE_CRYSTAL_ORE = Supplier { ModBlocks.ARCANE_CRYSTAL_ORE.get().defaultBlockState() }
    private val DEEPSLATE_ARCANE_CRYSTAL_ORE = Supplier {
        ModBlocks.DEEPSLATE_ARCANE_CRYSTAL_ORE.get().defaultBlockState()
    }
    private val RUNIC_STONE = Supplier { ModBlocks.RUNIC_STONE.get().defaultBlockState() }
    private val RUNIC_DEEPSLATE = Supplier { ModBlocks.RUNIC_DEEPSLATE.get().defaultBlockState() }
    private val RUNIC_DARKSTONE = Supplier { ModBlocks.RUNIC_DARKSTONE.get().defaultBlockState() }
    private val DARKSTONE = Supplier { ModBlocks.DARKSTONE.get().defaultBlockState() }
    private val STELLA_ARCANUM = Supplier { ModBlocks.STELLA_ARCANUM.get().defaultBlockState() }
    private val MYSTERYWOOD_LEAVES = Supplier { ModBlocks.AURUM_LEAVES.get().defaultBlockState() }
    private val NUGGETY_MYSTERYWOOD_LEAVES = Supplier { ModBlocks.NUGGETY_AURUM_LEAVES.get().defaultBlockState() }
    private val FUNGYSS_BLOCK = Supplier {
        ModBlocks.FUNGYSS_BLOCK.get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)
    }
    private val FUNGYSS_STEM = Supplier { ModBlocks.FUNGYSS_STEM.get().defaultBlockState() }
    private val FUNGYSS_HYPHAE = Supplier { ModBlocks.FUNGYSS_HYPHAE.get().defaultBlockState() }
}
