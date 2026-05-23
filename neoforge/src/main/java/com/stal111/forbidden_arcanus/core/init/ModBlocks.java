package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.*;
import com.stal111.forbidden_arcanus.common.block.clibano.*;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.grower.FATreeGrower;
import com.stal111.forbidden_arcanus.common.block.pedestal.MagnetizedPedestalBlock;
import com.stal111.forbidden_arcanus.common.block.pedestal.PedestalBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.skull.ObsidianSkullType;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.common.item.component.EffectGrantingRule;
import com.stal111.forbidden_arcanus.core.init.other.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.*;

import java.util.List;
import java.util.Optional;

/**
 * Mod Blocks <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlocks
 *
 * @author stal111
 */
public class ModBlocks implements RegistryClass {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final BlockRegistryEntry<ForbiddenomiconBlock> FORBIDDENOMICON = HELPER.register("forbiddenomicon", ForbiddenomiconBlock::new, () -> BlockBehaviour.Properties.of().requiredFeatures(ForbiddenArcanus.PREVIEW)).withItem();

    public static final BlockRegistryEntry<Block> DARKSTONE = HELPER.register("darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<SlabBlock> DARKSTONE_SLAB = HELPER.register("darkstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<StairBlock> DARKSTONE_STAIRS = HELPER.register("darkstone_stairs", (properties) -> new StairBlock(DARKSTONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<WallBlock> DARKSTONE_WALL = HELPER.register("darkstone_wall", WallBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_DARKSTONE = HELPER.register("polished_darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_DARKSTONE_SLAB = HELPER.register("polished_darkstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_DARKSTONE_STAIRS = HELPER.register("polished_darkstone_stairs", (properties) -> new StairBlock(POLISHED_DARKSTONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<WallBlock> POLISHED_DARKSTONE_WALL = HELPER.register("polished_darkstone_wall", WallBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.register("polished_darkstone_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetType.STONE, properties), () -> Block.Properties.ofLegacyCopy(Blocks.STONE_PRESSURE_PLATE).strength(0.5F)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> POLISHED_DARKSTONE_BUTTON = HELPER.register("polished_darkstone_button", (properties) -> new ButtonBlock(BlockSetType.STONE, 20, properties), () -> Block.Properties.ofLegacyCopy(Blocks.STONE_BUTTON).strength(0.5F)).withItem();
    public static final BlockRegistryEntry<Block> CHISELED_POLISHED_DARKSTONE = HELPER.register("chiseled_polished_darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> GILDED_CHISELED_POLISHED_DARKSTONE = HELPER.register("gilded_chiseled_polished_darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_DARKSTONE_BRICKS = HELPER.register("polished_darkstone_bricks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.register("polished_darkstone_brick_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.register("polished_darkstone_brick_stairs", (properties) -> new StairBlock(POLISHED_DARKSTONE_BRICKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<WallBlock> POLISHED_DARKSTONE_BRICK_WALL = HELPER.register("polished_darkstone_brick_wall", WallBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.register("cracked_polished_darkstone_bricks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> TILED_POLISHED_DARKSTONE_BRICKS = HELPER.register("tiled_polished_darkstone_bricks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_POLISHED_DARKSTONE = HELPER.register("arcane_polished_darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<SlabBlock> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.register("arcane_polished_darkstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<StairBlock> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.register("arcane_polished_darkstone_stairs", (properties) -> new StairBlock(ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<WallBlock> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.register("arcane_polished_darkstone_wall", WallBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.register("chiseled_arcane_polished_darkstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<PillarBlock> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.register("arcane_polished_darkstone_pillar", PillarBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<PedestalBlock> DARKSTONE_PEDESTAL = HELPER.register("darkstone_pedestal", PedestalBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()).withItem();
    public static final BlockRegistryEntry<MagnetizedPedestalBlock> MAGNETIZED_DARKSTONE_PEDESTAL = HELPER.register("magnetized_darkstone_pedestal", MagnetizedPedestalBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()).withItem();
    public static final BlockRegistryEntry<MortarBlock> MORTAR = HELPER.register("mortar", MortarBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).requiredFeatures(ForbiddenArcanus.PREVIEW).strength(4.5F, 8.0F).noOcclusion()).withItem();
    public static final BlockRegistryEntry<ClibanoCoreBlock> CLIBANO_CORE = HELPER.register("clibano_core", ClibanoCoreBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).requiredFeatures(ForbiddenArcanus.PREVIEW).strength(4.5F, 8.0F)).withItem();
    public static final BlockRegistryEntry<ClibanoSideBlock> CLIBANO_SIDE_HORIZONTAL = HELPER.register("clibano_side_horizontal", ClibanoSideBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F));
    public static final BlockRegistryEntry<ClibanoSideBlock> CLIBANO_SIDE_VERTICAL = HELPER.register("clibano_side_vertical", ClibanoSideBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F));
    public static final BlockRegistryEntry<ClibanoMainPartBlock> CLIBANO_MAIN_PART = HELPER.register("clibano_main_part", ClibanoMainPartBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F));
    public static final BlockRegistryEntry<ClibanoCornerBlock> CLIBANO_CORNER = HELPER.register("clibano_corner", ClibanoCornerBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F));
    public static final BlockRegistryEntry<ClibanoCenterBlock> CLIBANO_CENTER = HELPER.register("clibano_center", ClibanoCenterBlock::new, () -> Block.Properties.ofLegacyCopy(Blocks.STONE).strength(4.5F, 8.0F).lightLevel(state -> Optional.ofNullable(state.getValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE).getFireType()).map(ClibanoFireType::getLightLevel).orElse(0)));

    public static final BlockRegistryEntry<Block> METEORITE = HELPER.register("meteorite", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(ModBlocks.DARKSTONE.get())).withItem();
    public static final BlockRegistryEntry<StellaArcanumBlock> STELLA_ARCANUM = HELPER.register("stella_arcanum", StellaArcanumBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F)).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> ARCANE_CRYSTAL_ORE = HELPER.register("arcane_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 5), properties), () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F)).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> DEEPSLATE_ARCANE_CRYSTAL_ORE = HELPER.register("deepslate_arcane_crystal_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 5), properties), () -> Block.Properties.of().mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_STONE = HELPER.register("runic_stone", (properties) -> new DropExperienceBlock(UniformInt.of(4, 8), properties), () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F)).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_DEEPSLATE = HELPER.register("runic_deepslate", (properties) -> new DropExperienceBlock(UniformInt.of(4, 8), properties), () -> Block.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE)).withItem();
    public static final BlockRegistryEntry<DropExperienceBlock> RUNIC_DARKSTONE = HELPER.register("runic_darkstone", (properties) -> new DropExperienceBlock(UniformInt.of(4, 8), properties), () -> Block.Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(6.0F, 4.0F)).withItem();
    public static final BlockRegistryEntry<Block> OBSIDIANSTEEL_BLOCK = HELPER.register("obsidiansteel_block", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OBSIDIAN)).withItem();
    public static final BlockRegistryEntry<Block> DEORUM_BLOCK = HELPER.register("deorum_block", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.GOLD_BLOCK)).withItem();
    public static final BlockRegistryEntry<Block> STELLARITE_BLOCK = HELPER.register("stellarite_block", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OBSIDIAN)).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_CRYSTAL_BLOCK = HELPER.register("arcane_crystal_block", Block::new, () -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion()).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON);
    public static final BlockRegistryEntry<Block> CORRUPTED_ARCANE_CRYSTAL_BLOCK = HELPER.register("corrupted_arcane_crystal_block", Block::new, () -> BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.0F, 3.0F)).withItem().toolType(ToolType.PICKAXE).toolTier(ToolTier.IRON);
    public static final BlockRegistryEntry<Block> RUNE_BLOCK = HELPER.register("rune_block", Block::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_MAGENTA).requiresCorrectToolForDrops().strength(5.0F, 6.0F)).withItem();
    public static final BlockRegistryEntry<TransparentBlock> DEORUM_GLASS = HELPER.register("deorum_glass", TransparentBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS)).withItem();
    public static final BlockRegistryEntry<TransparentBlock> RUNIC_GLASS = HELPER.register("runic_glass", TransparentBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS)).withItem();
    public static final BlockRegistryEntry<IronBarsBlock> DEORUM_GLASS_PANE = HELPER.register("deorum_glass_pane", IronBarsBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS_PANE)).withItem();
    public static final BlockRegistryEntry<IronBarsBlock> RUNIC_GLASS_PANE = HELPER.register("runic_glass_pane", IronBarsBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.GLASS_PANE)).withItem();
    public static final BlockRegistryEntry<LanternBlock> DEORUM_LANTERN = HELPER.register("deorum_lantern", LanternBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.LANTERN)).withItem();
    public static final BlockRegistryEntry<LanternBlock> DEORUM_SOUL_LANTERN = HELPER.register("deorum_soul_lantern", LanternBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_LANTERN)).withItem();

    public static final BlockRegistryEntry<SoullessSandBlock> SOULLESS_SAND = HELPER.register("soulless_sand", SoullessSandBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SOUL_SAND).speedFactor(1.0F)).withItem();
    public static final BlockRegistryEntry<Block> SOULLESS_SANDSTONE = HELPER.register("soulless_sandstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE)).withItem();
    public static final BlockRegistryEntry<Block> CUT_SOULLESS_SANDSTONE = HELPER.register("cut_soulless_sandstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE)).withItem();
    public static final BlockRegistryEntry<Block> POLISHED_SOULLESS_SANDSTONE = HELPER.register("polished_soulless_sandstone", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE)).withItem();
    public static final BlockRegistryEntry<SlabBlock> SOULLESS_SANDSTONE_SLAB = HELPER.register("soulless_sandstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_SLAB)).withItem();
    public static final BlockRegistryEntry<SlabBlock> CUT_SOULLESS_SANDSTONE_SLAB = HELPER.register("cut_soulless_sandstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_SLAB)).withItem();
    public static final BlockRegistryEntry<SlabBlock> POLISHED_SOULLESS_SANDSTONE_SLAB = HELPER.register("polished_soulless_sandstone_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_SLAB)).withItem();
    public static final BlockRegistryEntry<StairBlock> SOULLESS_SANDSTONE_STAIRS = HELPER.register("soulless_sandstone_stairs", (properties) -> new StairBlock(SOULLESS_SANDSTONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_STAIRS)).withItem();
    public static final BlockRegistryEntry<StairBlock> POLISHED_SOULLESS_SANDSTONE_STAIRS = HELPER.register("polished_soulless_sandstone_stairs", (properties) -> new StairBlock(POLISHED_SOULLESS_SANDSTONE.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_STAIRS)).withItem();
    public static final BlockRegistryEntry<WallBlock> SOULLESS_SANDSTONE_WALL = HELPER.register("soulless_sandstone_wall", WallBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SANDSTONE_WALL)).withItem();

    public static final BlockRegistryEntry<FungyssBlock> FUNGYSS = HELPER.register("fungyss", FungyssBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.WARPED_FUNGUS).sound(SoundType.GRASS)).withItem();
    public static final BlockRegistryEntry<SaplingBlock> AURUM_SAPLING = HELPER.register("aurum_sapling", (properties) -> new SaplingBlock(FATreeGrower.AURUM, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SAPLING)).withItem();
    public static final BlockRegistryEntry<GrowingEdelwoodBlock> GROWING_EDELWOOD = HELPER.register("growing_edelwood", GrowingEdelwoodBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SAPLING)).withItem();
    public static final BlockRegistryEntry<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", HugeMushroomBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD)).withItem();

    public static final BlockRegistryEntry<TintedParticleLeavesBlock> AURUM_LEAVES = HELPER.register("aurum_leaves", properties -> new TintedParticleLeavesBlock(0.0F, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LEAVES)).withItem();
    public static final BlockRegistryEntry<TintedParticleLeavesBlock> NUGGETY_AURUM_LEAVES = HELPER.register("nuggety_aurum_leaves", properties -> new TintedParticleLeavesBlock(0.0F, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LEAVES)).withItem();

    public static final BlockRegistryEntry<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", RotatedPillarBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F).sound(SoundType.STEM)).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> AURUM_LOG = HELPER.register("aurum_log", MysterywoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG)).withItem();
    public static final BlockRegistryEntry<EdelwoodLogBlock> EDELWOOD_LOG = HELPER.register("edelwood_log", EdelwoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN).randomTicks()).withItem();
    public static final BlockRegistryEntry<CarvedEdelwoodLogBlock> CARVED_EDELWOOD_LOG = HELPER.register("carved_edelwood_log", CarvedEdelwoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN).randomTicks()).withItem();
    public static final BlockRegistryEntry<EdelwoodBranchBlock> EDELWOOD_BRANCH = HELPER.register("edelwood_branch", EdelwoodBranchBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_BROWN)).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> STRIPPED_AURUM_LOG = HELPER.register("stripped_aurum_log", MysterywoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG)).withItem();
    public static final BlockRegistryEntry<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", RotatedPillarBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F).sound(SoundType.STEM)).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> AURUM_WOOD = HELPER.register("aurum_wood", MysterywoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG)).withItem();
    public static final BlockRegistryEntry<MysterywoodLogBlock> STRIPPED_AURUM_WOOD = HELPER.register("stripped_aurum_wood", MysterywoodLogBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_LOG)).withItem();
    public static final BlockRegistryEntry<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", Block::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<Block> AURUM_PLANKS = HELPER.register("aurum_planks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PLANKS)).withItem();
    public static final BlockRegistryEntry<Block> EDELWOOD_PLANKS = HELPER.register("edelwood_planks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PLANKS)).withItem();
    public static final BlockRegistryEntry<Block> ARCANE_EDELWOOD_PLANKS = HELPER.register("arcane_edelwood_planks", Block::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PLANKS)).withItem();
    public static final BlockRegistryEntry<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", SlabBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<SlabBlock> AURUM_SLAB = HELPER.register("aurum_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SLAB)).withItem();
    public static final BlockRegistryEntry<SlabBlock> EDELWOOD_SLAB = HELPER.register("edelwood_slab", SlabBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_SLAB)).withItem();
    public static final BlockRegistryEntry<StairBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", (properties) -> new StairBlock(ModBlocks.FUNGYSS_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<StairBlock> AURUM_STAIRS = HELPER.register("aurum_stairs", (properties) -> new StairBlock(ModBlocks.AURUM_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_STAIRS)).withItem();
    public static final BlockRegistryEntry<StairBlock> EDELWOOD_STAIRS = HELPER.register("edelwood_stairs", (properties) -> new StairBlock(ModBlocks.EDELWOOD_PLANKS.get().defaultBlockState(), properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_STAIRS)).withItem();

    public static final BlockRegistryEntry<DoorBlock> DEORUM_DOOR = HELPER.register("deorum_door", (properties) -> new DoorBlock(BlockSetType.GOLD, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion()).withItem();
    public static final BlockRegistryEntry<DoorBlock> FUNGYSS_DOOR = HELPER.register("fungyss_door", (properties) -> new DoorBlock(BlockSetTypes.FUNGYSS, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion()).withItem();
    public static final BlockRegistryEntry<DoorBlock> AURUM_DOOR = HELPER.register("aurum_door", (properties) -> new DoorBlock(BlockSetTypes.AURUM, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion()).withItem();
    public static final BlockRegistryEntry<DoorBlock> EDELWOOD_DOOR = HELPER.register("edelwood_door", (properties) -> new DoorBlock(BlockSetTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion()).withItem();
    public static final BlockRegistryEntry<DoorBlock> ARCANE_EDELWOOD_DOOR = HELPER.register("arcane_edelwood_door", (properties) -> new DoorBlock(BlockSetTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion()).withItem();
    public static final BlockRegistryEntry<TrapDoorBlock> DEORUM_TRAPDOOR = HELPER.register("deorum_trapdoor", (properties) -> new TrapDoorBlock(BlockSetType.GOLD, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(ModBlocks::never)).withItem();
    public static final BlockRegistryEntry<TrapDoorBlock> FUNGYSS_TRAPDOOR = HELPER.register("fungyss_trapdoor", (properties) -> new TrapDoorBlock(BlockSetTypes.FUNGYSS, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)).withItem();
    public static final BlockRegistryEntry<TrapDoorBlock> AURUM_TRAPDOOR = HELPER.register("aurum_trapdoor", (properties) -> new TrapDoorBlock(BlockSetTypes.AURUM, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)).withItem();
    public static final BlockRegistryEntry<TrapDoorBlock> EDELWOOD_TRAPDOOR = HELPER.register("edelwood_trapdoor", (properties) -> new TrapDoorBlock(BlockSetTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)).withItem();
    public static final BlockRegistryEntry<TrapDoorBlock> ARCANE_EDELWOOD_TRAPDOOR = HELPER.register("arcane_edelwood_trapdoor", (properties) -> new TrapDoorBlock(BlockSetTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.of().strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)).withItem();
    public static final BlockRegistryEntry<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", FenceBlock::new, () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<FenceBlock> AURUM_FENCE = HELPER.register("aurum_fence", FenceBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE)).withItem();
    public static final BlockRegistryEntry<FenceBlock> EDELWOOD_FENCE = HELPER.register("edelwood_fence", FenceBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE)).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", (properties) -> new FenceGateBlock(ModWoodTypes.FUNGYSS, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> AURUM_FENCE_GATE = HELPER.register("aurum_fence_gate", (properties) -> new FenceGateBlock(ModWoodTypes.AURUM, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE_GATE)).withItem();
    public static final BlockRegistryEntry<FenceGateBlock> EDELWOOD_FENCE_GATE = HELPER.register("edelwood_fence_gate", (properties) -> new FenceGateBlock(ModWoodTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_FENCE_GATE)).withItem();
    //TODO
    //    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> FUNGYSS_SIGN  = HELPER.createSignBlock("fungyss", MapColor.WOOL, ModWoodTypes.FUNGYSS);
//    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> AURUM_SIGN = HELPER.createSignBlock("aurum", MapColor.COLOR_BROWN, ModWoodTypes.AURUM);
//    public static final Pair<BlockRegistryEntry<ValhelsiaStandingSignBlock>, BlockRegistryEntry<ValhelsiaWallSignBlock>> EDELWOOD_SIGN  = HELPER.createSignBlock("edelwood", MapColor.COLOR_BROWN, ModWoodTypes.EDELWOOD);
    public static final BlockRegistryEntry<LadderBlock> EDELWOOD_LADDER = HELPER.register("edelwood_ladder", LadderBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.LADDER)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", (properties) -> new ButtonBlock(BlockSetTypes.FUNGYSS, 30, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> AURUM_BUTTON = HELPER.register("aurum_button", (properties) -> new ButtonBlock(BlockSetTypes.AURUM, 30, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_BUTTON)).withItem();
    public static final BlockRegistryEntry<ButtonBlock> EDELWOOD_BUTTON = HELPER.register("edelwood_button", (properties) -> new ButtonBlock(BlockSetTypes.EDELWOOD, 30, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_BUTTON)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetTypes.FUNGYSS, properties), () -> BlockBehaviour.Properties.of().mapColor(MapColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> AURUM_PRESSURE_PLATE = HELPER.register("aurum_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetTypes.AURUM, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PRESSURE_PLATE)).withItem();
    public static final BlockRegistryEntry<PressurePlateBlock> EDELWOOD_PRESSURE_PLATE = HELPER.register("edelwood_pressure_plate", (properties) -> new PressurePlateBlock(BlockSetTypes.EDELWOOD, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.OAK_PRESSURE_PLATE)).withItem();

    public static final BlockRegistryEntry<HephaestusForgeBlock> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", (properties) -> new HephaestusForgeBlock(properties), () -> Block.Properties.ofLegacyCopy(Blocks.OBSIDIAN).overrideDescription(HephaestusForgeBlock.DESCRIPTION_ID).strength(38.0F, 1200.0F).noOcclusion());

    public static final BlockRegistryEntry<ArcaneDragonEggBlock> ARCANE_DRAGON_EGG = HELPER.register("arcane_dragon_egg", ArcaneDragonEggBlock::new, () -> Block.Properties.ofLegacyCopy(Blocks.DRAGON_EGG).lightLevel(value -> 5)).withItem();

    public static final BlockRegistryEntry<ArcaneCrystalObeliskBlock> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", ArcaneCrystalObeliskBlock::new, () -> Block.Properties.of().strength(1.0F, 10.0F).pushReaction(PushReaction.BLOCK)).withItem();
    public static final BlockRegistryEntry<ArcaneCrystalObeliskBlock> CORRUPTED_ARCANE_CRYSTAL_OBELISK = HELPER.register("corrupted_arcane_crystal_obelisk", ArcaneCrystalObeliskBlock::new, () -> Block.Properties.of().strength(1.0F, 10.0F).pushReaction(PushReaction.BLOCK)).withItem();

    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> OBSIDIAN_SKULL = HELPER.registerSkull("obsidian", ObsidianSkullType.DEFAULT, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL), BlockItems.OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> CRACKED_OBSIDIAN_SKULL = HELPER.registerSkull("cracked_obsidian", ObsidianSkullType.CRACKED, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL), BlockItems.CRACKED_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> FRAGMENTED_OBSIDIAN_SKULL = HELPER.registerSkull("fragmented_obsidian", ObsidianSkullType.FRAGMENTED, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL), BlockItems.FRAGMENTED_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> FADING_OBSIDIAN_SKULL = HELPER.registerSkull("fading_obsidian", ObsidianSkullType.FADING, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL), BlockItems.FADING_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> AUREALIC_OBSIDIAN_SKULL = HELPER.registerSkull("aurealic_obsidian", ObsidianSkullType.AUREALIC, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).requiredFeatures(ForbiddenArcanus.PREVIEW), BlockItems.AUREALIC_OBSIDIAN_SKULL_ITEM);
    public static final SkullRegistryEntry<SkullBlock, WallSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerSkull("eternal_obsidian", ObsidianSkullType.ETERNAL, SkullBlock::new, WallSkullBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SKELETON_SKULL).requiredFeatures(ForbiddenArcanus.PREVIEW), BlockItems.ETERNAL_OBSIDIAN_SKULL_ITEM);

    public static final BlockRegistryEntry<UtremJarBlock> UTREM_JAR = HELPER.register("utrem_jar", UtremJarBlock::new, () -> Block.Properties.ofLegacyCopy(Blocks.GLASS).overrideDescription(UtremJarBlock.DESCRIPTION_ID));
    public static final BlockRegistryEntry<EssenceUtremJarBlock> ESSENCE_UTREM_JAR = HELPER.register("essence_utrem_jar", EssenceUtremJarBlock::new, () -> Block.Properties.ofLegacyCopy(Blocks.GLASS).overrideDescription(UtremJarBlock.DESCRIPTION_ID).lightLevel(state -> state.getValue(ModBlockStateProperties.ESSENCE_TYPE).getLightEmission()));

    public static final BlockRegistryEntry<Block> BLACK_HOLE = HELPER.register("black_hole", BlackHoleBlock::new, () -> Block.Properties.ofLegacyCopy(Blocks.STONE).strength(2.0F, 8.0F).noOcclusion());
    public static final BlockRegistryEntry<ChainBlock> DEORUM_CHAIN = HELPER.register("deorum_chain", ChainBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.IRON_CHAIN)).withItem();
    public static final BlockRegistryEntry<FlowerBlock> YELLOW_ORCHID = HELPER.register("yellow_orchid", (properties) -> new FlowerBlock(MobEffects.GLOWING, 10, properties), () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.BLUE_ORCHID)).withItem();
    public static final BlockRegistryEntry<FarmlandBlock> MAGICAL_FARMLAND = HELPER.register("magical_farmland", FarmlandBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.FARMLAND).randomTicks()).withItem();
    public static final BlockRegistryEntry<WhirlwindBlock> WHIRLWIND = HELPER.register("whirlwind", WhirlwindBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SHORT_GRASS));
    public static final BlockRegistryEntry<UpwindBlock> UPWIND = HELPER.register("upwind", UpwindBlock::new, () -> BlockBehaviour.Properties.ofLegacyCopy(Blocks.SHORT_GRASS));
    public static final BlockRegistryEntry<DeskBlock> DESK = HELPER.register("desk", DeskBlock::new, () -> BlockBehaviour.Properties.of().requiredFeatures(ForbiddenArcanus.PREVIEW)).withItem();
    public static final BlockRegistryEntry<ResearchDeskBlock> RESEARCH_DESK = HELPER.register("research_desk", ResearchDeskBlock::new, () -> BlockBehaviour.Properties.of().requiredFeatures(ForbiddenArcanus.PREVIEW)).withItem();
    public static final BlockRegistryEntry<WandDeskBlock> WAND_DESK = HELPER.register("wand_desk", WandDeskBlock::new, () -> BlockBehaviour.Properties.of().requiredFeatures(ForbiddenArcanus.PREVIEW)).withItem();
    public static final BlockRegistryEntry<HeavyCoreBlock> QUANTUM_CORE = HELPER.register("quantum_core", HeavyCoreBlock::new, () -> BlockBehaviour.Properties.of().explosionResistance(1200.0F)).withItem();
    public static final BlockRegistryEntry<QuantumInjectorBlock> QUANTUM_INJECTOR = HELPER.register("quantum_injector", QuantumInjectorBlock::new, () -> BlockBehaviour.Properties.of().explosionResistance(1200.0F)).withItem();

    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_FUNGYSS = HELPER.register("potted_fungyss", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, FUNGYSS, properties), () -> Block.Properties.ofLegacyCopy(Blocks.POTTED_OAK_SAPLING));
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_AURUM_SAPLING = HELPER.register("potted_aurum_sapling", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, AURUM_SAPLING, properties), () -> Block.Properties.ofLegacyCopy(Blocks.POTTED_OAK_SAPLING));
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_GROWING_EDELWOOD = HELPER.register("potted_growing_edelwood", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, GROWING_EDELWOOD, properties), () -> Block.Properties.ofLegacyCopy(Blocks.POTTED_OAK_SAPLING));
    public static final BlockRegistryEntry<FlowerPotBlock> POTTED_YELLOW_ORCHID = HELPER.register("potted_yellow_orchid", (properties) -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.YELLOW_ORCHID, properties), () -> Block.Properties.ofLegacyCopy(Blocks.POTTED_OAK_SAPLING));

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static class BlockSetTypes {

        public static final BlockSetType FUNGYSS = BlockSetType.register(new BlockSetType("fungyss"));
        public static final BlockSetType AURUM = BlockSetType.register(new BlockSetType("aurum"));
        public static final BlockSetType EDELWOOD = BlockSetType.register(new BlockSetType("edelwood"));

    }

    private static class BlockItems {

        public static final SkullRegistryEntry.SkullItemFactory OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.DEFAULT).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE)));
        public static final SkullRegistryEntry.SkullItemFactory CRACKED_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, CRACKED_OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.CRACKED).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE)));
        public static final SkullRegistryEntry.SkullItemFactory FRAGMENTED_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, FRAGMENTED_OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.FRAGMENTED).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE)));
        public static final SkullRegistryEntry.SkullItemFactory FADING_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, FADING_OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.FADING).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE)));
        public static final SkullRegistryEntry.SkullItemFactory AUREALIC_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, AUREALIC_OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).rarity(Rarity.UNCOMMON).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.AUREALIC).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE_IF_HAS_AUREAL)));
        public static final SkullRegistryEntry.SkullItemFactory ETERNAL_OBSIDIAN_SKULL_ITEM = (skull, wallSkull) -> new ObsidianSkullItem(skull, wallSkull, new Item.Properties().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ETERNAL_OBSIDIAN_SKULL.skull().getKey().identifier())).equippableUnswappable(EquipmentSlot.HEAD).rarity(Rarity.RARE).fireResistant().component(ModDataComponents.OBSIDIAN_SKULL_TYPE, ObsidianSkullType.ETERNAL).component(ModDataComponents.GRANTS_EFFECTS, List.of(EffectGrantingRule.GRANT_FIRE_RESISTANCE)));
    }
}
