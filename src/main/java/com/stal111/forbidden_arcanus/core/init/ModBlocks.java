package com.stal111.forbidden_arcanus.core.init;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.RodBlock;
import com.stal111.forbidden_arcanus.common.block.*;
import com.stal111.forbidden_arcanus.common.block.grower.CherrywoodTreeGrower;
import com.stal111.forbidden_arcanus.common.block.grower.MysterywoodTreeGrower;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.block.RenderType;

/**
 * Mod Blocks <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlocks
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks implements RegistryClass {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final RegistryObject<Block> DARKSTONE = HELPER.register("darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_CHISELED_DARKSTONE = HELPER.register("arcane_chiseled_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_SLAB = HELPER.register("darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_STAIRS = HELPER.register("darkstone_stairs", () -> new StairBlock(() -> DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_WALL = HELPER.register("darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> GILDED_DARKSTONE = HELPER.register("gilded_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE = HELPER.register("polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_SLAB = HELPER.register("polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_STAIRS = HELPER.register("polished_darkstone_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_WALL = HELPER.register("polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.register("polished_darkstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.STONE_PRESSURE_PLATE).strength(0.5F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BUTTON = HELPER.register("polished_darkstone_button", () -> new StoneButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON).strength(0.5F)));
    public static final RegistryObject<Block> CHISELED_POLISHED_DARKSTONE = HELPER.register("chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> RUNIC_CHISELED_POLISHED_DARKSTONE = HELPER.register("runic_chiseled_polished_darkstone", () -> new RunicChiseledPolishedDarkstone(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = HELPER.register("arcane_chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICKS = HELPER.register("polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.register("polished_darkstone_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.register("polished_darkstone_brick_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_WALL = HELPER.register("polished_darkstone_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.register("cracked_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> TILED_POLISHED_DARKSTONE_BRICKS = HELPER.register("tiled_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE = HELPER.register("arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.register("arcane_polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.register("arcane_polished_darkstone_stairs", () -> new StairBlock(() -> ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.register("arcane_polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.register("chiseled_arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<PillarBlock> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.register("arcane_polished_darkstone_pillar", () -> new PillarBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<RodBlock> ARCANE_POLISHED_DARKSTONE_ROD = HELPER.register("arcane_polished_darkstone_rod", () -> new RodBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_PEDESTAL = HELPER.register("darkstone_pedestal", () -> new PedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()));
    public static final RegistryObject<MagnetizedPedestalBlock> MAGNETIZED_DARKSTONE_PEDESTAL = HELPER.register("magnetized_darkstone_pedestal", () -> new MagnetizedPedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()));
    public static final RegistryObject<ClibanoCoreBlock> CLIBANO_CORE = HELPER.register("clibano_core", () -> new ClibanoCoreBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<ClibanoHorizontalSideBlock> CLIBANO_SIDE_HORIZONTAL = HELPER.registerNoItem("clibano_side_horizontal", () -> new ClibanoHorizontalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<ClibanoVerticalSideBlock> CLIBANO_SIDE_VERTICAL = HELPER.registerNoItem("clibano_side_vertical", () -> new ClibanoVerticalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<ClibanoMainPartBlock> CLIBANO_MAIN_PART = HELPER.registerNoItem("clibano_main_part", () -> new ClibanoMainPartBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<ClibanoCornerBlock> CLIBANO_CORNER = HELPER.registerNoItem("clibano_corner", () -> new ClibanoCornerBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<ClibanoCenterBlock> CLIBANO_CENTER = HELPER.registerNoItem("clibano_center", () -> new ClibanoCenterBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).lightLevel(state -> state.getValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE).getLightLevel())));

    public static final RegistryObject<StellaArcanumBlock> STELLA_ARCANUM = HELPER.register("stella_arcanum", () -> new StellaArcanumBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> XPETRIFIED_ORE = HELPER.register("xpetrified_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> ARCANE_CRYSTAL_ORE = HELPER.register("arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(2, 5)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> DEEPSLATE_ARCANE_CRYSTAL_ORE = HELPER.register("deepslate_arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 5)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> RUNIC_STONE = HELPER.register("runic_stone", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(4, 8)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> RUNIC_DEEPSLATE = HELPER.register("runic_deepslate", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(4, 8)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DropExperienceBlock> RUNIC_DARKSTONE = HELPER.register("runic_darkstone", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(6.0F, 4.0F), UniformInt.of(4, 8)));
    public static final RegistryObject<Block> DARK_NETHER_STAR_BLOCK = HELPER.register("dark_nether_star_block", () -> new Block(Block.Properties.of(Materials.DARK_NETHER_STAR).requiresCorrectToolForDrops().strength(10.0F, 1200.0F)));
    public static final RegistryObject<Block> PROCESSED_OBSIDIAN_BLOCK = HELPER.register("processed_obsidian_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistryObject<Block> DEORUM_BLOCK = HELPER.register("deorum_block", () -> new Block(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> STELLARITE_BLOCK = HELPER.register("stellarite_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> ARCANE_CRYSTAL_BLOCK = HELPER.register("arcane_crystal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion()));
    public static final RegistryObject<Block> RUNE_BLOCK = HELPER.register("rune_block", () -> new Block(Block.Properties.of(Materials.RUNE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> DARK_RUNE_BLOCK = HELPER.register("dark_rune_block", () -> new Block(Block.Properties.of(Materials.RUNE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> DEORUM_GLASS = HELPER.register("deorum_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> RUNIC_GLASS = HELPER.register("runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> DARK_RUNIC_GLASS = HELPER.register("dark_runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> DEORUM_GLASS_PANE = HELPER.register("deorum_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> RUNIC_GLASS_PANE = HELPER.register("runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> DARK_RUNIC_GLASS_PANE = HELPER.register("dark_runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<LanternBlock> DEORUM_LANTERN = HELPER.register("deorum_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.LANTERN)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<LanternBlock> DEORUM_SOUL_LANTERN = HELPER.register("deorum_soul_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.SOUL_LANTERN)));

    public static final RegistryObject<SoullessSandBlock> SOULLESS_SAND = HELPER.register("soulless_sand", () -> new SoullessSandBlock(Block.Properties.copy(Blocks.SOUL_SAND).speedFactor(1.0F)));
    public static final RegistryObject<Block> SOULLESS_SANDSTONE = HELPER.register("soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> CUT_SOULLESS_SANDSTONE = HELPER.register("cut_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> POLISHED_SOULLESS_SANDSTONE = HELPER.register("polished_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<SlabBlock> SOULLESS_SANDSTONE_SLAB = HELPER.register("soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<SlabBlock> CUT_SOULLESS_SANDSTONE_SLAB = HELPER.register("cut_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<SlabBlock> POLISHED_SOULLESS_SANDSTONE_SLAB = HELPER.register("polished_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<StairBlock> SOULLESS_SANDSTONE_STAIRS = HELPER.register("soulless_sandstone_stairs", () -> new StairBlock(() -> SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS)));
    public static final RegistryObject<StairBlock> POLISHED_SOULLESS_SANDSTONE_STAIRS = HELPER.register("polished_soulless_sandstone_stairs", () -> new StairBlock(() -> POLISHED_SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS)));
    public static final RegistryObject<WallBlock> SOULLESS_SANDSTONE_WALL = HELPER.register("soulless_sandstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.SANDSTONE_WALL)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FungyssBlock> FUNGYSS = HELPER.register("fungyss", () -> new FungyssBlock(Block.Properties.copy(Blocks.WARPED_FUNGUS).sound(SoundType.GRASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<SaplingBlock> CHERRY_SAPLING = HELPER.register("cherry_sapling", () -> new SaplingBlock(new CherrywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<SaplingBlock> AURUM_SAPLING = HELPER.register("aurum_sapling", () -> new SaplingBlock(new MysterywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<GrowingEdelwoodBlock> GROWING_EDELWOOD = HELPER.register("growing_edelwood", () -> new GrowingEdelwoodBlock(Block.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD)));
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final RegistryObject<LeavesBlock> CHERRY_LEAVES = HELPER.register("cherry_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final RegistryObject<LeafCarpetBlock> CHERRY_LEAF_CARPET = HELPER.register("cherry_leaf_carpet", () -> new LeafCarpetBlock(CHERRY_LEAVES, Block.Properties.copy(Blocks.OAK_LEAVES)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<CherryFlowerVinesBlock> CHERRY_FLOWER_VINES = HELPER.register("cherry_flower_vines", () -> new CherryFlowerVinesBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<CherryFlowerVinesPlantBlock> CHERRY_FLOWER_VINES_PLANT = HELPER.registerNoItem("cherry_flower_vines_plant", () -> new CherryFlowerVinesPlantBlock(Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CAVE_VINES)));

    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final RegistryObject<LeavesBlock> AURUM_LEAVES = HELPER.register("aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final RegistryObject<LeavesBlock> NUGGETY_AURUM_LEAVES = HELPER.register("nuggety_aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));

    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<RotatedPillarBlock> CHERRY_LOG = HELPER.register("cherry_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<ThinLogBlock> THIN_CHERRY_LOG = HELPER.register("thin_cherry_log", () -> new ThinLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> AURUM_LOG = HELPER.register("aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<EdelwoodLogBlock> EDELWOOD_LOG = HELPER.register("edelwood_log", () -> new EdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<CarvedEdelwoodLogBlock> CARVED_EDELWOOD_LOG = HELPER.register("carved_edelwood_log", () -> new CarvedEdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<EdelwoodBranchBlock> EDELWOOD_BRANCH = HELPER.register("edelwood_branch", () -> new EdelwoodBranchBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_LOG = HELPER.register("stripped_cherry_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> STRIPPED_AURUM_LOG = HELPER.register("stripped_aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<RotatedPillarBlock> CHERRY_WOOD = HELPER.register("cherry_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> AURUM_WOOD = HELPER.register("aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_WOOD = HELPER.register("stripped_cherry_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> STRIPPED_AURUM_WOOD = HELPER.register("stripped_aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHERRY_PLANKS = HELPER.register("cherry_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> CARVED_CHERRY_PLANKS = HELPER.register("carved_cherry_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> AURUM_PLANKS = HELPER.register("aurum_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> EDELWOOD_PLANKS = HELPER.register("edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_EDELWOOD_PLANKS = HELPER.register("arcane_edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> CHERRY_SLAB = HELPER.register("cherry_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<SlabBlock> AURUM_SLAB = HELPER.register("aurum_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<SlabBlock> EDELWOOD_SLAB = HELPER.register("edelwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<StairBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", () -> new StairBlock(() -> ModBlocks.FUNGYSS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairBlock> CHERRY_STAIRS = HELPER.register("cherry_stairs", () -> new StairBlock(() -> ModBlocks.CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<StairBlock> AURUM_STAIRS = HELPER.register("aurum_stairs", () -> new StairBlock(() -> ModBlocks.AURUM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<StairBlock> EDELWOOD_STAIRS = HELPER.register("edelwood_stairs", () -> new StairBlock(() -> ModBlocks.EDELWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> DEORUM_DOOR = HELPER.register("deorum_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> FUNGYSS_DOOR = HELPER.register("fungyss_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> CHERRY_DOOR = HELPER.register("cherry_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> AURUM_DOOR = HELPER.register("aurum_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> EDELWOOD_DOOR = HELPER.register("edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<DoorBlock> ARCANE_EDELWOOD_DOOR = HELPER.register("arcane_edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> DEORUM_TRAPDOOR = HELPER.register("deorum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(ModBlocks::never)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> FUNGYSS_TRAPDOOR = HELPER.register("fungyss_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> CHERRY_TRAPDOOR = HELPER.register("cherry_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> AURUM_TRAPDOOR = HELPER.register("aurum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> EDELWOOD_TRAPDOOR = HELPER.register("edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<TrapDoorBlock> ARCANE_EDELWOOD_TRAPDOOR = HELPER.register("arcane_edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)));
    public static final RegistryObject<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> CHERRY_FENCE = HELPER.register("cherry_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceBlock> AURUM_FENCE = HELPER.register("aurum_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceBlock> EDELWOOD_FENCE = HELPER.register("edelwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> CHERRY_FENCE_GATE = HELPER.register("cherry_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<FenceGateBlock> AURUM_FENCE_GATE = HELPER.register("aurum_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<FenceGateBlock> EDELWOOD_FENCE_GATE = HELPER.register("edelwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> FUNGYSS_SIGN  = HELPER.createSignBlock("fungyss", MaterialColor.WOOL, ModWoodTypes.FUNGYSS);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> CHERRY_SIGN = HELPER.createSignBlock("cherry", MaterialColor.COLOR_PINK, ModWoodTypes.CHERRY);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> AURUM_SIGN = HELPER.createSignBlock("aurum", MaterialColor.COLOR_BROWN, ModWoodTypes.AURUM);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> EDELWOOD_SIGN  = HELPER.createSignBlock("edelwood", MaterialColor.COLOR_BROWN, ModWoodTypes.EDELWOOD);
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<LadderBlock> EDELWOOD_LADDER = HELPER.register("edelwood_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER)));
    public static final RegistryObject<WoodButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WoodButtonBlock> CHERRY_BUTTON = HELPER.register("cherry_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<WoodButtonBlock> AURUM_BUTTON = HELPER.register("aurum_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<WoodButtonBlock> EDELWOOD_BUTTON = HELPER.register("edelwood_button", () -> new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<PressurePlateBlock> DEORUM_PRESSURE_PLATE = HELPER.register("deorum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> CHERRY_PRESSURE_PLATE = HELPER.register("cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> AURUM_PRESSURE_PLATE = HELPER.register("aurum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> EDELWOOD_PRESSURE_PLATE = HELPER.register("edelwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<HephaestusForgeBlock> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> new HephaestusForgeBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F).noOcclusion()));

    public static final RegistryObject<ArcaneDragonEggBlock> ARCANE_DRAGON_EGG = HELPER.register("arcane_dragon_egg", () -> new ArcaneDragonEggBlock(Block.Properties.copy(Blocks.DRAGON_EGG).lightLevel(value -> 5)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<Block> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", () -> new ArcaneCrystalObeliskBlock(Block.Properties.of(Material.STONE).strength(1.0F, 10.0F)));
    public static final RegistryObject<ObsidianSkullBlock> OBSIDIAN_SKULL = HELPER.registerNoItem("obsidian_skull", () -> new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("obsidian_wall_skull", () -> new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(OBSIDIAN_SKULL)));
    public static final RegistryObject<ObsidianSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerNoItem("eternal_obsidian_skull", () -> new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("eternal_obsidian_wall_skull", () -> new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(ETERNAL_OBSIDIAN_SKULL)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<UtremJarBlock> UTREM_JAR = HELPER.registerNoItem("utrem_jar", () -> new UtremJarBlock(Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.register("pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.register("corrupted_pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<NipaBlock> NIPA = HELPER.register("nipa", () -> new NipaBlock(Block.Properties.copy(Blocks.LARGE_FERN).offsetType(BlockBehaviour.OffsetType.NONE)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<NoFluidOverlayBlock> PETRIFIED_ROOT = HELPER.register("petrified_root", () -> new NoFluidOverlayBlock(Block.Properties.copy(Blocks.STONE).noOcclusion()));
    public static final RegistryObject<Block> BLACK_HOLE = HELPER.registerNoItem("black_hole", () -> new BlackHoleBlock(Block.Properties.copy(Blocks.STONE).strength(2.0F, 8.0F).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final RegistryObject<ChainBlock> DEORUM_CHAIN = HELPER.register("deorum_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerBlock> YELLOW_ORCHID = HELPER.register("yellow_orchid", () -> new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<GoldenOrchidBlock> GOLDEN_ORCHID = HELPER.registerNoItem("golden_orchid", () -> new GoldenOrchidBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).randomTicks().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final RegistryObject<MagicalFarmlandBlock> MAGICAL_FARMLAND = HELPER.register("magical_farmland", () -> new MagicalFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND).randomTicks()));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<StrangeRootBlock> STRANGE_ROOT = HELPER.registerNoItem("strange_root", () -> new StrangeRootBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerPotBlock> POTTED_FUNGYSS = HELPER.registerNoItem("potted_fungyss", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FUNGYSS, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerPotBlock> POTTED_CHERRY_SAPLING = HELPER.registerNoItem("potted_cherry_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CHERRY_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerPotBlock> POTTED_AURUM_SAPLING = HELPER.registerNoItem("potted_aurum_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerPotBlock> POTTED_GROWING_EDELWOOD = HELPER.registerNoItem("potted_growing_edelwood", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GROWING_EDELWOOD, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_ORCHID = HELPER.registerNoItem("potted_yellow_orchid", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.YELLOW_ORCHID, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static class Materials {
        public static final Material DARK_NETHER_STAR = (new Material.Builder(MaterialColor.COLOR_PURPLE)).build();
        public static final Material RUNE = (new Material.Builder(MaterialColor.COLOR_MAGENTA)).build();
    }
}
