package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.RodBlock;
import com.stal111.forbidden_arcanus.common.block.*;
import com.stal111.forbidden_arcanus.common.block.grower.MysterywoodTreeGrower;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianWallSkullBlock;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.valhelsia.valhelsia_core.api.client.ValhelsiaRenderType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.*;

/**
 * Mod Blocks <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlocks
 *
 * @author stal111
 */
public class ModBlocks implements RegistryClass {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final BlockRegistryEntry<ForbiddenomiconBlock> FORBIDDENOMICON = HELPER.register("forbiddenomicon", () -> new ForbiddenomiconBlock(BlockBehaviour.Properties.of())).withItem().renderType(ValhelsiaRenderType.CUTOUT);

    public static final BlockRegistryEntry<Block> DARKSTONE = HELPER.register("darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_CHISELED_DARKSTONE = HELPER.register("arcane_chiseled_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<SlabBlock> DARKSTONE_SLAB = HELPER.register("darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<StairBlock> DARKSTONE_STAIRS = HELPER.register("darkstone_stairs", () -> new StairBlock(() -> DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<WallBlock> DARKSTONE_WALL = HELPER.register("darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> GILDED_DARKSTONE = HELPER.register("gilded_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).sound(SoundType.GILDED_BLACKSTONE))).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_DARKSTONE = HELPER.register("polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_DARKSTONE_SLAB = HELPER.register("polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_DARKSTONE_STAIRS = HELPER.register("polished_darkstone_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<WallBlock> POLISHED_DARKSTONE_WALL = HELPER.register("polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.register("polished_darkstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.STONE_PRESSURE_PLATE).strength(0.5F), BlockSetType.STONE)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> POLISHED_DARKSTONE_BUTTON = HELPER.register("polished_darkstone_button", () -> new ButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON).strength(0.5F), BlockSetType.STONE, 20, false)).withItem();
    public static final BlockRegistryEntry<Block> CHISELED_POLISHED_DARKSTONE = HELPER.register("chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<RunicChiseledPolishedDarkstone> RUNIC_CHISELED_POLISHED_DARKSTONE = HELPER.register("runic_chiseled_polished_darkstone", () -> new RunicChiseledPolishedDarkstone(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = HELPER.register("arcane_chiseled_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_DARKSTONE_BRICKS = HELPER.register("polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.register("polished_darkstone_brick_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.register("polished_darkstone_brick_stairs", () -> new StairBlock(() -> POLISHED_DARKSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<WallBlock> POLISHED_DARKSTONE_BRICK_WALL = HELPER.register("polished_darkstone_brick_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.register("cracked_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> TILED_POLISHED_DARKSTONE_BRICKS = HELPER.register("tiled_polished_darkstone_bricks", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_POLISHED_DARKSTONE = HELPER.register("arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<SlabBlock> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.register("arcane_polished_darkstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<StairBlock> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.register("arcane_polished_darkstone_stairs", () -> new StairBlock(() -> ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<WallBlock> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.register("arcane_polished_darkstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.register("chiseled_arcane_polished_darkstone", () -> new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<PillarBlock> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.register("arcane_polished_darkstone_pillar", () -> new PillarBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<RodBlock> ARCANE_POLISHED_DARKSTONE_ROD = HELPER.register("arcane_polished_darkstone_rod", () -> new RodBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<PedestalBlock> DARKSTONE_PEDESTAL = HELPER.register("darkstone_pedestal", () -> new PedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion())).withItem();
    public static final BlockRegistryEntry<MagnetizedPedestalBlock> MAGNETIZED_DARKSTONE_PEDESTAL = HELPER.register("magnetized_darkstone_pedestal", () -> new MagnetizedPedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion())).withItem();
    public static final BlockRegistryEntry<ClibanoCoreBlock> CLIBANO_CORE = HELPER.register("clibano_core", () -> new ClibanoCoreBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F))).withItem();
    public static final BlockRegistryEntry<ClibanoHorizontalSideBlock> CLIBANO_SIDE_HORIZONTAL = HELPER.register("clibano_side_horizontal", () -> new ClibanoHorizontalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryEntry<ClibanoVerticalSideBlock> CLIBANO_SIDE_VERTICAL = HELPER.register("clibano_side_vertical", () -> new ClibanoVerticalSideBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryEntry<ClibanoMainPartBlock> CLIBANO_MAIN_PART = HELPER.register("clibano_main_part", () -> new ClibanoMainPartBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryEntry<ClibanoCornerBlock> CLIBANO_CORNER = HELPER.register("clibano_corner", () -> new ClibanoCornerBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final BlockRegistryEntry<ClibanoCenterBlock> CLIBANO_CENTER = HELPER.register("clibano_center", () -> new ClibanoCenterBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).lightLevel(state -> state.getValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE).getLightLevel())));

    public static final BlockRegistryEntry<StellaArcanumBlock> STELLA_ARCANUM = HELPER.register("stella_arcanum", () -> new StellaArcanumBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F))).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> XPETRIFIED_ORE = HELPER.register("xpetrified_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DropExperienceBlock> ARCANE_CRYSTAL_ORE = HELPER.register("arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(2, 5))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DropExperienceBlock> DEEPSLATE_ARCANE_CRYSTAL_ORE = HELPER.register("deepslate_arcane_crystal_ore", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 5))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_STONE = HELPER.register("runic_stone", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(4, 8))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_DEEPSLATE = HELPER.register("runic_deepslate", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(4, 8))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_DARKSTONE = HELPER.register("runic_darkstone", () -> new DropExperienceBlock(Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(6.0F, 4.0F), UniformInt.of(4, 8))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<Block> DARK_NETHER_STAR_BLOCK = HELPER.register("dark_nether_star_block", () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(10.0F, 1200.0F))).withItem();
    public static final BlockRegistryEntry<Block> PROCESSED_OBSIDIAN_BLOCK = HELPER.register("processed_obsidian_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN))).withItem();
    public static final BlockRegistryEntry<Block> DEORUM_BLOCK = HELPER.register("deorum_block", () -> new Block(Block.Properties.copy(Blocks.GOLD_BLOCK))).withItem();
    public static final BlockRegistryEntry<Block> STELLARITE_BLOCK = HELPER.register("stellarite_block", () -> new Block(Block.Properties.copy(Blocks.OBSIDIAN))).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_CRYSTAL_BLOCK = HELPER.register("arcane_crystal_block", () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion())).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<Block> CORRUPTED_ARCANE_CRYSTAL_BLOCK = HELPER.register("corrupted_arcane_crystal_block", () -> new Block(Block.Properties.of().requiresCorrectToolForDrops().strength(1.0F, 3.0F))).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON);
    public static final BlockRegistryEntry<Block> RUNE_BLOCK = HELPER.register("rune_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_MAGENTA).requiresCorrectToolForDrops().strength(5.0F, 6.0F))).withItem();
    public static final BlockRegistryEntry<Block> DARK_RUNE_BLOCK = HELPER.register("dark_rune_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F))).withItem();
    public static final BlockRegistryEntry<GlassBlock> DEORUM_GLASS = HELPER.register("deorum_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<GlassBlock> RUNIC_GLASS = HELPER.register("runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<GlassBlock> DARK_RUNIC_GLASS = HELPER.register("dark_runic_glass", () -> new GlassBlock(Block.Properties.copy(Blocks.GLASS))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<IronBarsBlock> DEORUM_GLASS_PANE = HELPER.register("deorum_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<IronBarsBlock> RUNIC_GLASS_PANE = HELPER.register("runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<IronBarsBlock> DARK_RUNIC_GLASS_PANE = HELPER.register("dark_runic_glass_pane", () -> new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<LanternBlock> DEORUM_LANTERN = HELPER.register("deorum_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.LANTERN))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<LanternBlock> DEORUM_SOUL_LANTERN = HELPER.register("deorum_soul_lantern", () -> new LanternBlock(Block.Properties.copy(Blocks.SOUL_LANTERN))).withItem().renderType(ValhelsiaRenderType.CUTOUT);

    public static final BlockRegistryEntry<SoullessSandBlock> SOULLESS_SAND = HELPER.register("soulless_sand", () -> new SoullessSandBlock(Block.Properties.copy(Blocks.SOUL_SAND).speedFactor(1.0F))).withItem();
    public static final BlockRegistryEntry<Block> SOULLESS_SANDSTONE = HELPER.register("soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryEntry<Block> CUT_SOULLESS_SANDSTONE = HELPER.register("cut_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_SOULLESS_SANDSTONE = HELPER.register("polished_soulless_sandstone", () -> new Block(Block.Properties.copy(Blocks.SANDSTONE))).withItem();
    public static final BlockRegistryEntry<SlabBlock> SOULLESS_SANDSTONE_SLAB = HELPER.register("soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryEntry<SlabBlock> CUT_SOULLESS_SANDSTONE_SLAB = HELPER.register("cut_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_SOULLESS_SANDSTONE_SLAB = HELPER.register("polished_soulless_sandstone_slab", () -> new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB))).withItem();
    public static final BlockRegistryEntry<StairBlock> SOULLESS_SANDSTONE_STAIRS = HELPER.register("soulless_sandstone_stairs", () -> new StairBlock(() -> SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS))).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_SOULLESS_SANDSTONE_STAIRS = HELPER.register("polished_soulless_sandstone_stairs", () -> new StairBlock(() -> POLISHED_SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS))).withItem();
    public static final BlockRegistryEntry<WallBlock> SOULLESS_SANDSTONE_WALL = HELPER.register("soulless_sandstone_wall", () -> new WallBlock(Block.Properties.copy(Blocks.SANDSTONE_WALL))).withItem();

    public static final BlockRegistryEntry<FungyssBlock> FUNGYSS = HELPER.register("fungyss", () -> new FungyssBlock(Block.Properties.copy(Blocks.WARPED_FUNGUS).sound(SoundType.GRASS))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<SaplingBlock> AURUM_SAPLING = HELPER.register("aurum_sapling", () -> new SaplingBlock(new MysterywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<GrowingEdelwoodBlock> GROWING_EDELWOOD = HELPER.register("growing_edelwood", () -> new GrowingEdelwoodBlock(Block.Properties.copy(Blocks.OAK_SAPLING))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD))).withItem();

    public static final BlockRegistryEntry<LeavesBlock> AURUM_LEAVES = HELPER.register("aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES))).withItem().renderType(ValhelsiaRenderType.CUTOUT_MIPPED);
    public static final BlockRegistryEntry<LeavesBlock> NUGGETY_AURUM_LEAVES = HELPER.register("nuggety_aurum_leaves", () -> new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES))).withItem().renderType(ValhelsiaRenderType.CUTOUT_MIPPED);

    public static final BlockRegistryEntry<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F).sound(SoundType.STEM))).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> AURUM_LOG = HELPER.register("aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryEntry<EdelwoodLogBlock> EDELWOOD_LOG = HELPER.register("edelwood_log", () -> new EdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN).randomTicks())).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<CarvedEdelwoodLogBlock> CARVED_EDELWOOD_LOG = HELPER.register("carved_edelwood_log", () -> new CarvedEdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN).randomTicks())).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<EdelwoodBranchBlock> EDELWOOD_BRANCH = HELPER.register("edelwood_branch", () -> new EdelwoodBranchBlock(Block.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<MysterywoodLogBlock> STRIPPED_AURUM_LOG = HELPER.register("stripped_aurum_log", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryEntry<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F).sound(SoundType.STEM))).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> AURUM_WOOD = HELPER.register("aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> STRIPPED_AURUM_WOOD = HELPER.register("stripped_aurum_wood", () -> new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG))).withItem();
    public static final BlockRegistryEntry<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryEntry<Block> AURUM_PLANKS = HELPER.register("aurum_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryEntry<Block> EDELWOOD_PLANKS = HELPER.register("edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_EDELWOOD_PLANKS = HELPER.register("arcane_edelwood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS))).withItem();
    public static final BlockRegistryEntry<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryEntry<SlabBlock> AURUM_SLAB = HELPER.register("aurum_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))).withItem();
    public static final BlockRegistryEntry<SlabBlock> EDELWOOD_SLAB = HELPER.register("edelwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB))).withItem();
    public static final BlockRegistryEntry<StairBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", () -> new StairBlock(() -> ModBlocks.FUNGYSS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryEntry<StairBlock> AURUM_STAIRS = HELPER.register("aurum_stairs", () -> new StairBlock(() -> ModBlocks.AURUM_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))).withItem();
    public static final BlockRegistryEntry<StairBlock> EDELWOOD_STAIRS = HELPER.register("edelwood_stairs", () -> new StairBlock(() -> ModBlocks.EDELWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS))).withItem();

    public static final BlockRegistryEntry<DoorBlock> DEORUM_DOOR = HELPER.register("deorum_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion(), BlockSetType.GOLD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DoorBlock> FUNGYSS_DOOR = HELPER.register("fungyss_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.FUNGYSS)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DoorBlock> AURUM_DOOR = HELPER.register("aurum_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.AURUM)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DoorBlock> EDELWOOD_DOOR = HELPER.register("edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.EDELWOOD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<DoorBlock> ARCANE_EDELWOOD_DOOR = HELPER.register("arcane_edelwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion(), BlockSetTypes.EDELWOOD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<TrapDoorBlock> DEORUM_TRAPDOOR = HELPER.register("deorum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetType.GOLD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<TrapDoorBlock> FUNGYSS_TRAPDOOR = HELPER.register("fungyss_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.FUNGYSS)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<TrapDoorBlock> AURUM_TRAPDOOR = HELPER.register("aurum_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.AURUM)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<TrapDoorBlock> EDELWOOD_TRAPDOOR = HELPER.register("edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.EDELWOOD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<TrapDoorBlock> ARCANE_EDELWOOD_TRAPDOOR = HELPER.register("arcane_edelwood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never), BlockSetTypes.EDELWOOD)).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD))).withItem();
    public static final BlockRegistryEntry<FenceBlock> AURUM_FENCE = HELPER.register("aurum_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))).withItem();
    public static final BlockRegistryEntry<FenceBlock> EDELWOOD_FENCE = HELPER.register("edelwood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE))).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), ModWoodTypes.FUNGYSS)).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> AURUM_FENCE_GATE = HELPER.register("aurum_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.AURUM)).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> EDELWOOD_FENCE_GATE = HELPER.register("edelwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), ModWoodTypes.EDELWOOD)).withItem();
//TODO
    //    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> FUNGYSS_SIGN  = HELPER.createSignBlock("fungyss", MapColor.WOOL, ModWoodTypes.FUNGYSS);
//    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> AURUM_SIGN = HELPER.createSignBlock("aurum", MapColor.COLOR_BROWN, ModWoodTypes.AURUM);
//    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> EDELWOOD_SIGN  = HELPER.createSignBlock("edelwood", MapColor.COLOR_BROWN, ModWoodTypes.EDELWOOD);
    public static final BlockRegistryEntry<LadderBlock> EDELWOOD_LADDER = HELPER.register("edelwood_ladder", () -> new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<ButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", () -> new ButtonBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), BlockSetTypes.FUNGYSS, 30, true)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> AURUM_BUTTON = HELPER.register("aurum_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetTypes.AURUM, 30, true)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> EDELWOOD_BUTTON = HELPER.register("edelwood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetTypes.EDELWOOD, 30, true)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> DEORUM_PRESSURE_PLATE = HELPER.register("deorum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE), BlockSetType.GOLD)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD), BlockSetTypes.FUNGYSS)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> AURUM_PRESSURE_PLATE = HELPER.register("aurum_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetTypes.AURUM)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> EDELWOOD_PRESSURE_PLATE = HELPER.register("edelwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE), BlockSetTypes.EDELWOOD)).withItem();

    public static final BlockRegistryEntry<HephaestusForgeBlock> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> new HephaestusForgeBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F).noOcclusion())).withItem().renderType(ValhelsiaRenderType.CUTOUT);

    public static final BlockRegistryEntry<ArcaneDragonEggBlock> ARCANE_DRAGON_EGG = HELPER.register("arcane_dragon_egg", () -> new ArcaneDragonEggBlock(Block.Properties.copy(Blocks.DRAGON_EGG).lightLevel(value -> 5))).withItem();

    public static final BlockRegistryEntry<ArcaneCrystalObeliskBlock> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", () -> new ArcaneCrystalObeliskBlock(Block.Properties.of().strength(1.0F, 10.0F))).withItem();
    public static final BlockRegistryEntry<ArcaneCrystalObeliskBlock> CORRUPTED_ARCANE_CRYSTAL_OBELISK = HELPER.register("corrupted_arcane_crystal_obelisk", () -> new ArcaneCrystalObeliskBlock(Block.Properties.of().strength(1.0F, 10.0F))).withItem();

    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> OBSIDIAN_SKULL = HELPER.registerSkull("obsidian", ObsidianSkullType.DEFAULT, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> CRACKED_OBSIDIAN_SKULL = HELPER.registerSkull("cracked_obsidian", ObsidianSkullType.CRACKED, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.CRACKED_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> FRAGMENTED_OBSIDIAN_SKULL = HELPER.registerSkull("fragmented_obsidian", ObsidianSkullType.FRAGMENTED, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.FRAGMENTED_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> FADING_OBSIDIAN_SKULL = HELPER.registerSkull("fading_obsidian", ObsidianSkullType.FADING, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.FADING_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> AUREALIC_OBSIDIAN_SKULL = HELPER.registerSkull("aurealic_obsidian", ObsidianSkullType.AUREALIC, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.AUREALIC_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<ObsidianSkullBlock, ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerSkull("eternal_obsidian", ObsidianSkullType.ETERNAL, ObsidianSkullBlock::new, ObsidianWallSkullBlock::new, BlockBehaviour.Properties.copy(Blocks.SKELETON_SKULL), BlockItems.ETERNAL_OBSIDIAN_SKULL_ITEM);

    public static final BlockRegistryEntry<UtremJarBlock> UTREM_JAR = HELPER.register("utrem_jar", () -> new UtremJarBlock(Block.Properties.copy(Blocks.GLASS))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.register("pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.copy(Blocks.GLASS))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.register("corrupted_pixie_utrem_jar", () -> new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.copy(Blocks.GLASS))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<NipaBlock> NIPA = HELPER.register("nipa", () -> new NipaBlock(Block.Properties.copy(Blocks.LARGE_FERN).offsetType(BlockBehaviour.OffsetType.NONE))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<NoFluidOverlayBlock> PETRIFIED_ROOT = HELPER.register("petrified_root", () -> new NoFluidOverlayBlock(Block.Properties.copy(Blocks.STONE).noOcclusion())).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<Block> BLACK_HOLE = HELPER.register("black_hole", () -> new BlackHoleBlock(Block.Properties.copy(Blocks.STONE).strength(2.0F, 8.0F).noOcclusion()));
    public static final BlockRegistryEntry<ChainBlock> DEORUM_CHAIN = HELPER.register("deorum_chain", () -> new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN))).withItem().renderType(ValhelsiaRenderType.CUTOUT_MIPPED);
    public static final BlockRegistryEntry<FlowerBlock> YELLOW_ORCHID = HELPER.register("yellow_orchid", () -> new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID))).withItem().renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<GoldenOrchidBlock> GOLDEN_ORCHID = HELPER.register("golden_orchid", () -> new GoldenOrchidBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).randomTicks().offsetType(BlockBehaviour.OffsetType.XZ))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<MagicalFarmlandBlock> MAGICAL_FARMLAND = HELPER.register("magical_farmland", () -> new MagicalFarmlandBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND).randomTicks())).withItem();
    public static final BlockRegistryEntry<StrangeRootBlock> STRANGE_ROOT = HELPER.register("strange_root", () -> new StrangeRootBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<WhirlwindBlock> WHIRLWIND = HELPER.register("whirlwind", () -> new WhirlwindBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<UpwindBlock> UPWIND = HELPER.register("upwind", () -> new UpwindBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final BlockRegistryEntry<DeskBlock> DESK = HELPER.register("desk", () -> new DeskBlock(BlockBehaviour.Properties.of())).withItem();
    public static final BlockRegistryEntry<ResearchDeskBlock> RESEARCH_DESK = HELPER.register("research_desk", () -> new ResearchDeskBlock(BlockBehaviour.Properties.of())).withItem();

    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_FUNGYSS = HELPER.register("potted_fungyss", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FUNGYSS, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_AURUM_SAPLING = HELPER.register("potted_aurum_sapling", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_GROWING_EDELWOOD = HELPER.register("potted_growing_edelwood", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GROWING_EDELWOOD, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING))).renderType(ValhelsiaRenderType.CUTOUT);
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_YELLOW_ORCHID = HELPER.register("potted_yellow_orchid", () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.YELLOW_ORCHID, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING))).renderType(ValhelsiaRenderType.CUTOUT);

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static class BlockSetTypes {
        public static final BlockSetType FUNGYSS = BlockSetType.register(new BlockSetType("fungyss"));
        public static final BlockSetType AURUM = BlockSetType.register(new BlockSetType("aurum"));
        public static final BlockSetType EDELWOOD = BlockSetType.register(new BlockSetType("edelwood"));

    }

    private static class BlockItems {
        public static final SkullRegistryEntry.SkullItemFactory OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.DEFAULT, skull, wallSkull, new Item.Properties().stacksTo(1).fireResistant());
        public static final SkullRegistryEntry.SkullItemFactory CRACKED_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.CRACKED, skull, wallSkull, new Item.Properties().stacksTo(1).fireResistant());
        public static final SkullRegistryEntry.SkullItemFactory FRAGMENTED_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.FRAGMENTED, skull, wallSkull, new Item.Properties().stacksTo(1).fireResistant());
        public static final SkullRegistryEntry.SkullItemFactory FADING_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.FADING, skull, wallSkull, new Item.Properties().stacksTo(1).fireResistant());
        public static final SkullRegistryEntry.SkullItemFactory AUREALIC_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.AUREALIC, skull, wallSkull, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant());
        public static final SkullRegistryEntry.SkullItemFactory ETERNAL_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(ObsidianSkullType.ETERNAL, skull, wallSkull, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }
}
