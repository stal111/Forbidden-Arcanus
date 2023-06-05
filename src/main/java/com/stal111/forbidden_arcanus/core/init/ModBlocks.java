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
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.valhelsia.valhelsia_core.client.util.ValhelsiaRenderType;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaStandingSignBlock;
import net.valhelsia.valhelsia_core.common.block.ValhelsiaWallSignBlock;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.block.*;

/**
 * Mod Blocks <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlocks
 *
 * @author stal111
 */
public class ModBlocks implements RegistryClass {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final BlockRegistryObject<Block> DARKSTONE = HELPER.create("darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> ARCANE_CHISELED_DARKSTONE = HELPER.create("arcane_chiseled_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<SlabBlock> DARKSTONE_SLAB = HELPER.create("darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<StairBlock> DARKSTONE_STAIRS = HELPER.create("darkstone_stairs", () -> new StairBlock(() -> DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<WallBlock> DARKSTONE_WALL = HELPER.create("darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> GILDED_DARKSTONE = HELPER.create("gilded_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).sound(SoundType.GILDED_BLACKSTONE))).withItem();
    public static final BlockRegistryObject<Block> POLISHED_DARKSTONE = HELPER.create("polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<SlabBlock> POLISHED_DARKSTONE_SLAB = HELPER.create("polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<StairBlock> POLISHED_DARKSTONE_STAIRS = HELPER.create("polished_darkstone_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<WallBlock> POLISHED_DARKSTONE_WALL = HELPER.create("polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.create("polished_darkstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.STONE_PRESSURE_PLATE).strength(0.5F), BlockSetType.STONE)).withItem();
    public static final BlockRegistryObject<ButtonBlock> POLISHED_DARKSTONE_BUTTON = HELPER.create("polished_darkstone_button", () -> new ButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON).strength(0.5F), BlockSetType.STONE, 20, false)).withItem();
    public static final BlockRegistryObject<Block> CHISELED_POLISHED_DARKSTONE = HELPER.create("chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<RunicChiseledPolishedDarkstone> RUNIC_CHISELED_POLISHED_DARKSTONE = HELPER.create("runic_chiseled_polished_darkstone", () -> new RunicChiseledPolishedDarkstone(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = HELPER.create("arcane_chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> POLISHED_DARKSTONE_BRICKS = HELPER.create("polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<SlabBlock> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.create("polished_darkstone_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<StairBlock> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.create("polished_darkstone_brick_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<WallBlock> POLISHED_DARKSTONE_BRICK_WALL = HELPER.create("polished_darkstone_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.create("cracked_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> TILED_POLISHED_DARKSTONE_BRICKS = HELPER.create("tiled_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> ARCANE_POLISHED_DARKSTONE = HELPER.create("arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<SlabBlock> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.create("arcane_polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<StairBlock> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.create("arcane_polished_darkstone_stairs", () -> new StairBlock(() -> ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<WallBlock> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.create("arcane_polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.create("chiseled_arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<PillarBlock> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.create("arcane_polished_darkstone_pillar", () -> new PillarBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<RodBlock> ARCANE_POLISHED_DARKSTONE_ROD = HELPER.create("arcane_polished_darkstone_rod", () -> new RodBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<PedestalBlock> DARKSTONE_PEDESTAL = HELPER.create("darkstone_pedestal", () -> new PedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion())).withItem();
    public static final BlockRegistryObject<MagnetizedPedestalBlock> MAGNETIZED_DARKSTONE_PEDESTAL = HELPER.create("magnetized_darkstone_pedestal", () -> new MagnetizedPedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion())).withItem();
    public static final BlockRegistryObject<ClibanoCoreBlock> CLIBANO_CORE = HELPER.create("clibano_core", () -> new ClibanoCoreBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryObject<ClibanoHorizontalSideBlock> CLIBANO_SIDE_HORIZONTAL = HELPER.create("clibano_side_horizontal", () -> new ClibanoHorizontalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryObject<ClibanoVerticalSideBlock> CLIBANO_SIDE_VERTICAL = HELPER.create("clibano_side_vertical", () -> new ClibanoVerticalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryObject<ClibanoMainPartBlock> CLIBANO_MAIN_PART = HELPER.create("clibano_main_part", () -> new ClibanoMainPartBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryObject<ClibanoCornerBlock> CLIBANO_CORNER = HELPER.create("clibano_corner", () -> new ClibanoCornerBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryObject<ClibanoCenterBlock> CLIBANO_CENTER = HELPER.create("clibano_center", () -> new ClibanoCenterBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).lightLevel(state -> state.getValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE).getLightLevel())));

    public static final BlockRegistryObject<StellaArcanumBlock> STELLA_ARCANUM = HELPER.create("stella_arcanum", () -> new StellaArcanumBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> XPETRIFIED_ORE = HELPER.create("xpetrified_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> ARCANE_CRYSTAL_ORE = HELPER.create("arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(2, 5))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> DEEPSLATE_ARCANE_CRYSTAL_ORE = HELPER.create("deepslate_arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 5))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> RUNIC_STONE = HELPER.create("runic_stone", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(4, 8))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> RUNIC_DEEPSLATE = HELPER.create("runic_deepslate", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(4, 8))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DropExperienceBlock> RUNIC_DARKSTONE = HELPER.create("runic_darkstone", () -> new DropExperienceBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(6.0F, 4.0F), UniformInt.of(4, 8))).withItem();
    public static final BlockRegistryObject<Block> DARK_NETHER_STAR_BLOCK = HELPER.create("dark_nether_star_block", () -> new Block(Block.Properties.of(Materials.DARK_NETHER_STAR).requiresCorrectToolForDrops().strength(10.0F, 1200.0F))).withItem();
    public static final BlockRegistryObject<Block> PROCESSED_OBSIDIAN_BLOCK = HELPER.create("processed_obsidian_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN))).withItem();
    public static final BlockRegistryObject<Block> DEORUM_BLOCK = HELPER.create("deorum_block", () -> new Block(Block.Properties.copy(Blocks.GOLD_BLOCK))).withItem();
    public static final BlockRegistryObject<Block> STELLARITE_BLOCK = HELPER.create("stellarite_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<Block> ARCANE_CRYSTAL_BLOCK = HELPER.create("arcane_crystal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion())).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON);
    public static final BlockRegistryObject<Block> CORRUPTED_ARCANE_CRYSTAL_BLOCK = HELPER.create("corrupted_arcane_crystal_block", () -> new Block(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.0F, 3.0F))).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON);
    public static final BlockRegistryObject<Block> RUNE_BLOCK = HELPER.create("rune_block", () -> new Block(Block.Properties.of(Materials.RUNE).requiresCorrectToolForDrops().strength(5.0F, 6.0F))).withItem();
    public static final BlockRegistryObject<Block> DARK_RUNE_BLOCK = HELPER.create("dark_rune_block", () -> new Block(Block.Properties.of(Materials.RUNE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<GlassBlock> DEORUM_GLASS = HELPER.create("deorum_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<GlassBlock> RUNIC_GLASS = HELPER.create("runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<GlassBlock> DARK_RUNIC_GLASS = HELPER.create("dark_runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<IronBarsBlock> DEORUM_GLASS_PANE = HELPER.create("deorum_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<IronBarsBlock> RUNIC_GLASS_PANE = HELPER.create("runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<IronBarsBlock> DARK_RUNIC_GLASS_PANE = HELPER.create("dark_runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<LanternBlock> DEORUM_LANTERN = HELPER.create("deorum_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.LANTERN))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<LanternBlock> DEORUM_SOUL_LANTERN = HELPER.create("deorum_soul_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.SOUL_LANTERN))).withItem();

    public static final BlockRegistryObject<SoullessSandBlock> SOULLESS_SAND = HELPER.create("soulless_sand", () -> new SoullessSandBlock(Block.Properties.copy(Blocks.SOUL_SAND).speedFactor(1.0F))).withItem();
    public static final BlockRegistryObject<Block> SOULLESS_SANDSTONE = HELPER.create("soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryObject<Block> CUT_SOULLESS_SANDSTONE = HELPER.create("cut_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryObject<Block> POLISHED_SOULLESS_SANDSTONE = HELPER.create("polished_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryObject<SlabBlock> SOULLESS_SANDSTONE_SLAB = HELPER.create("soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryObject<SlabBlock> CUT_SOULLESS_SANDSTONE_SLAB = HELPER.create("cut_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryObject<SlabBlock> POLISHED_SOULLESS_SANDSTONE_SLAB = HELPER.create("polished_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryObject<StairBlock> SOULLESS_SANDSTONE_STAIRS = HELPER.create("soulless_sandstone_stairs", () -> new StairBlock(() -> SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS))).withItem();
    public static final BlockRegistryObject<StairBlock> POLISHED_SOULLESS_SANDSTONE_STAIRS = HELPER.create("polished_soulless_sandstone_stairs", () -> new StairBlock(() -> POLISHED_SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS))).withItem();
    public static final BlockRegistryObject<WallBlock> SOULLESS_SANDSTONE_WALL = HELPER.create("soulless_sandstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.SANDSTONE_WALL))).withItem();

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FungyssBlock> FUNGYSS = HELPER.create("fungyss", () -> new FungyssBlock(Block.Properties.copy(Blocks.WARPED_FUNGUS).sound(SoundType.GRASS))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<SaplingBlock> CHERRY_SAPLING = HELPER.create("cherry_sapling", () -> new SaplingBlock(new CherrywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<SaplingBlock> AURUM_SAPLING = HELPER.create("aurum_sapling", () -> new SaplingBlock(new MysterywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<GrowingEdelwoodBlock> GROWING_EDELWOOD = HELPER.create("growing_edelwood", () -> new GrowingEdelwoodBlock(Block.Properties.copy(Blocks.OAK_SAPLING))).withItem();
    public static final BlockRegistryObject<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.create("fungyss_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final BlockRegistryObject<LeavesBlock> CHERRY_LEAVES = HELPER.create("cherry_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final BlockRegistryObject<LeafCarpetBlock> CHERRY_LEAF_CARPET = HELPER.create("cherry_leaf_carpet", () -> new LeafCarpetBlock(CHERRY_LEAVES.getRegistryObject(), Block.Properties.copy(Blocks.OAK_LEAVES))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<CherryFlowerVinesBlock> CHERRY_FLOWER_VINES = HELPER.create("cherry_flower_vines", () -> new CherryFlowerVinesBlock(Block.Properties.of(Material.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<CherryFlowerVinesPlantBlock> CHERRY_FLOWER_VINES_PLANT = HELPER.create("cherry_flower_vines_plant", () -> new CherryFlowerVinesPlantBlock(Block.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.CAVE_VINES)));

    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final BlockRegistryObject<LeavesBlock> AURUM_LEAVES = HELPER.create("aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final BlockRegistryObject<LeavesBlock> NUGGETY_AURUM_LEAVES = HELPER.create("nuggety_aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES))).withItem();

    public static final BlockRegistryObject<RotatedPillarBlock> FUNGYSS_STEM = HELPER.create("fungyss_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM))).withItem();
    public static final BlockRegistryObject<RotatedPillarBlock> CHERRY_LOG = HELPER.create("cherry_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<ThinLogBlock> THIN_CHERRY_LOG = HELPER.create("thin_cherry_log", () -> new ThinLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<MysterywoodLogBlock> AURUM_LOG = HELPER.create("aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<EdelwoodLogBlock> EDELWOOD_LOG = HELPER.create("edelwood_log", () -> new EdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks())).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<CarvedEdelwoodLogBlock> CARVED_EDELWOOD_LOG = HELPER.create("carved_edelwood_log", () -> new CarvedEdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks())).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<EdelwoodBranchBlock> EDELWOOD_BRANCH = HELPER.create("edelwood_branch", () -> new EdelwoodBranchBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN))).withItem();
    public static final BlockRegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_LOG = HELPER.create("stripped_cherry_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<MysterywoodLogBlock> STRIPPED_AURUM_LOG = HELPER.create("stripped_aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.create("fungyss_hyphae", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM))).withItem();
    public static final BlockRegistryObject<RotatedPillarBlock> CHERRY_WOOD = HELPER.create("cherry_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<MysterywoodLogBlock> AURUM_WOOD = HELPER.create("aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<RotatedPillarBlock> STRIPPED_CHERRY_WOOD = HELPER.create("stripped_cherry_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<MysterywoodLogBlock> STRIPPED_AURUM_WOOD = HELPER.create("stripped_aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryObject<Block> FUNGYSS_PLANKS = HELPER.create("fungyss_planks", () -> new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryObject<Block> CHERRY_PLANKS = HELPER.create("cherry_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryObject<Block> CARVED_CHERRY_PLANKS = HELPER.create("carved_cherry_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryObject<Block> AURUM_PLANKS = HELPER.create("aurum_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryObject<Block> EDELWOOD_PLANKS = HELPER.create("edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryObject<Block> ARCANE_EDELWOOD_PLANKS = HELPER.create("arcane_edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryObject<SlabBlock> FUNGYSS_SLAB = HELPER.create("fungyss_slab", () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryObject<SlabBlock> CHERRY_SLAB = HELPER.create("cherry_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))).withItem();
    public static final BlockRegistryObject<SlabBlock> AURUM_SLAB = HELPER.create("aurum_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))).withItem();
    public static final BlockRegistryObject<SlabBlock> EDELWOOD_SLAB = HELPER.create("edelwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))).withItem();
    public static final BlockRegistryObject<StairBlock> FUNGYSS_STAIRS = HELPER.create("fungyss_stairs", () -> new StairBlock(() -> ModBlocks.FUNGYSS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryObject<StairBlock> CHERRY_STAIRS = HELPER.create("cherry_stairs", () -> new StairBlock(() -> ModBlocks.CHERRY_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))).withItem();
    public static final BlockRegistryObject<StairBlock> AURUM_STAIRS = HELPER.create("aurum_stairs", () -> new StairBlock(() -> ModBlocks.AURUM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))).withItem();
    public static final BlockRegistryObject<StairBlock> EDELWOOD_STAIRS = HELPER.create("edelwood_stairs", () -> new StairBlock(() -> ModBlocks.EDELWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))).withItem();

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> DEORUM_DOOR = HELPER.create("deorum_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion(), BlockSetType.GOLD)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> FUNGYSS_DOOR = HELPER.create("fungyss_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.FUNGYSS)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> CHERRY_DOOR = HELPER.create("cherry_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.CHERRY)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> AURUM_DOOR = HELPER.create("aurum_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.AURUM)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> EDELWOOD_DOOR = HELPER.create("edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.EDELWOOD)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<DoorBlock> ARCANE_EDELWOOD_DOOR = HELPER.create("arcane_edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.EDELWOOD)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> DEORUM_TRAPDOOR = HELPER.create("deorum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetType.GOLD)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> FUNGYSS_TRAPDOOR = HELPER.create("fungyss_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.FUNGYSS)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> CHERRY_TRAPDOOR = HELPER.create("cherry_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.CHERRY)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> AURUM_TRAPDOOR = HELPER.create("aurum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.AURUM)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> EDELWOOD_TRAPDOOR = HELPER.create("edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.EDELWOOD)).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<TrapDoorBlock> ARCANE_EDELWOOD_TRAPDOOR = HELPER.create("arcane_edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.EDELWOOD)).withItem();
    public static final BlockRegistryObject<FenceBlock> FUNGYSS_FENCE = HELPER.create("fungyss_fence", () -> new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryObject<FenceBlock> CHERRY_FENCE = HELPER.create("cherry_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))).withItem();
    public static final BlockRegistryObject<FenceBlock> AURUM_FENCE = HELPER.create("aurum_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))).withItem();
    public static final BlockRegistryObject<FenceBlock> EDELWOOD_FENCE = HELPER.create("edelwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))).withItem();
    public static final BlockRegistryObject<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.create("fungyss_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), ModWoodTypes.FUNGYSS)).withItem();
    public static final BlockRegistryObject<FenceGateBlock> CHERRY_FENCE_GATE = HELPER.create("cherry_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.CHERRY)).withItem();
    public static final BlockRegistryObject<FenceGateBlock> AURUM_FENCE_GATE = HELPER.create("aurum_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.AURUM)).withItem();
    public static final BlockRegistryObject<FenceGateBlock> EDELWOOD_FENCE_GATE = HELPER.create("edelwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.EDELWOOD)).withItem();
    public static final Pair<BlockRegistryObject<ValhelsiaStandingSignBlock>, BlockRegistryObject<ValhelsiaWallSignBlock>> FUNGYSS_SIGN  = HELPER.createSignBlock("fungyss", MaterialColor.WOOL, ModWoodTypes.FUNGYSS);
    public static final Pair<BlockRegistryObject<ValhelsiaStandingSignBlock>, BlockRegistryObject<ValhelsiaWallSignBlock>> CHERRY_SIGN = HELPER.createSignBlock("cherry", MaterialColor.COLOR_PINK, ModWoodTypes.CHERRY);
    public static final Pair<BlockRegistryObject<ValhelsiaStandingSignBlock>, BlockRegistryObject<ValhelsiaWallSignBlock>> AURUM_SIGN = HELPER.createSignBlock("aurum", MaterialColor.COLOR_BROWN, ModWoodTypes.AURUM);
    public static final Pair<BlockRegistryObject<ValhelsiaStandingSignBlock>, BlockRegistryObject<ValhelsiaWallSignBlock>> EDELWOOD_SIGN  = HELPER.createSignBlock("edelwood", MaterialColor.COLOR_BROWN, ModWoodTypes.EDELWOOD);
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<LadderBlock> EDELWOOD_LADDER = HELPER.create("edelwood_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER))).withItem();
    public static final BlockRegistryObject<ButtonBlock> FUNGYSS_BUTTON = HELPER.create("fungyss_button", () -> new ButtonBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), BlockSetTypes.FUNGYSS, 30, true)).withItem();
    public static final BlockRegistryObject<ButtonBlock> CHERRY_BUTTON = HELPER.create("cherry_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetTypes.CHERRY, 30, true)).withItem();
    public static final BlockRegistryObject<ButtonBlock> AURUM_BUTTON = HELPER.create("aurum_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetTypes.AURUM, 30, true)).withItem();
    public static final BlockRegistryObject<ButtonBlock> EDELWOOD_BUTTON = HELPER.create("edelwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetTypes.EDELWOOD, 30, true)).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> DEORUM_PRESSURE_PLATE = HELPER.create("deorum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), BlockSetType.GOLD)).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.create("fungyss_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), BlockSetTypes.FUNGYSS)).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> CHERRY_PRESSURE_PLATE = HELPER.create("cherry_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetTypes.CHERRY)).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> AURUM_PRESSURE_PLATE = HELPER.create("aurum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetTypes.AURUM)).withItem();
    public static final BlockRegistryObject<PressurePlateBlock> EDELWOOD_PRESSURE_PLATE = HELPER.create("edelwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetTypes.EDELWOOD)).withItem();

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<HephaestusForgeBlock> HEPHAESTUS_FORGE = HELPER.create("hephaestus_forge", () -> new HephaestusForgeBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F).noOcclusion())).withItem();

    public static final BlockRegistryObject<ArcaneDragonEggBlock> ARCANE_DRAGON_EGG = HELPER.create("arcane_dragon_egg", () -> new ArcaneDragonEggBlock(Block.Properties.copy(Blocks.DRAGON_EGG).lightLevel(value -> 5))).withItem();

    public static final BlockRegistryObject<ArcaneCrystalObeliskBlock> ARCANE_CRYSTAL_OBELISK = HELPER.create("arcane_crystal_obelisk", () -> new ArcaneCrystalObeliskBlock(Block.Properties.of(Material.STONE).strength(1.0F, 10.0F))).withItem();
    public static final BlockRegistryObject<ArcaneCrystalObeliskBlock> CORRUPTED_ARCANE_CRYSTAL_OBELISK = HELPER.create("corrupted_arcane_crystal_obelisk", () -> new ArcaneCrystalObeliskBlock(Block.Properties.of(Material.STONE).strength(1.0F, 10.0F))).withItem();

    public static final BlockRegistryObject<ObsidianSkullBlock> OBSIDIAN_SKULL = HELPER.create("obsidian_skull", () -> new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final BlockRegistryObject<ObsidianWallSkullBlock> OBSIDIAN_WALL_SKULL = HELPER.create("obsidian_wall_skull", () -> new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(OBSIDIAN_SKULL)));
    public static final BlockRegistryObject<ObsidianSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.create("eternal_obsidian_skull", () -> new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final BlockRegistryObject<ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_WALL_SKULL = HELPER.create("eternal_obsidian_wall_skull", () -> new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(ETERNAL_OBSIDIAN_SKULL)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<UtremJarBlock> UTREM_JAR = HELPER.create("utrem_jar", () -> new UtremJarBlock(Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.create("pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.create("corrupted_pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.copy(Blocks.GLASS)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<NipaBlock> NIPA = HELPER.create("nipa", () -> new NipaBlock(Block.Properties.copy(Blocks.LARGE_FERN).offsetType(BlockBehaviour.OffsetType.NONE))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<NoFluidOverlayBlock> PETRIFIED_ROOT = HELPER.create("petrified_root", () -> new NoFluidOverlayBlock(Block.Properties.copy(Blocks.STONE).noOcclusion())).withItem();
    public static final BlockRegistryObject<Block> BLACK_HOLE = HELPER.create("black_hole", () -> new BlackHoleBlock(Block.Properties.copy(Blocks.STONE).strength(2.0F, 8.0F).noOcclusion()));
    @RenderType(ValhelsiaRenderType.CUTOUT_MIPPED)
    public static final BlockRegistryObject<ChainBlock> DEORUM_CHAIN = HELPER.create("deorum_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerBlock> YELLOW_ORCHID = HELPER.create("yellow_orchid", () -> new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID))).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<GoldenOrchidBlock> GOLDEN_ORCHID = HELPER.create("golden_orchid", () -> new GoldenOrchidBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).randomTicks().offsetType(BlockBehaviour.OffsetType.XZ)));
    public static final BlockRegistryObject<MagicalFarmlandBlock> MAGICAL_FARMLAND = HELPER.create("magical_farmland", () -> new MagicalFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND).randomTicks())).withItem();
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<StrangeRootBlock> STRANGE_ROOT = HELPER.create("strange_root", () -> new StrangeRootBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<WhirlwindBlock> WHIRLWIND = HELPER.create("whirlwind", () -> new WhirlwindBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final BlockRegistryObject<UpwindBlock> UPWIND = HELPER.create("upwind", () -> new UpwindBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerPotBlock> POTTED_FUNGYSS = HELPER.create("potted_fungyss", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FUNGYSS, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerPotBlock> POTTED_CHERRY_SAPLING = HELPER.create("potted_cherry_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CHERRY_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerPotBlock> POTTED_AURUM_SAPLING = HELPER.create("potted_aurum_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerPotBlock> POTTED_GROWING_EDELWOOD = HELPER.create("potted_growing_edelwood", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GROWING_EDELWOOD, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));
    @RenderType(ValhelsiaRenderType.CUTOUT)
    public static final BlockRegistryObject<FlowerPotBlock> POTTED_YELLOW_ORCHID = HELPER.create("potted_yellow_orchid", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.YELLOW_ORCHID, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)));

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static class Materials {
        public static final Material DARK_NETHER_STAR = (new Material.Builder(MaterialColor.COLOR_PURPLE)).build();
        public static final Material RUNE = (new Material.Builder(MaterialColor.COLOR_MAGENTA)).build();
    }

    public static class BlockSetTypes {
        public static final BlockSetType FUNGYSS = BlockSetType.register(new BlockSetType("fungyss"));
        public static final BlockSetType CHERRY = BlockSetType.register(new BlockSetType("cherry"));
        public static final BlockSetType AURUM = BlockSetType.register(new BlockSetType("aurum"));
        public static final BlockSetType EDELWOOD = BlockSetType.register(new BlockSetType("edelwood"));

    }
}
