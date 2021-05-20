package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.*;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.registry.block.BlockRegistryHelper;
import net.valhelsia.valhelsia_core.util.ValhelsiaRenderType;

/**
 * Newer Mod Blocks
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.NewerModBlocks
 *
 * @author stal111
 * @version 16.2.0
 * @since 2.0.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NewModBlocks {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final RegistryObject<Block> DARKSTONE = HELPER.register("darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_SLAB = HELPER.register("darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_STAIRS = HELPER.register("darkstone_stairs", new StairsBlock(() -> DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_WALL = HELPER.register("darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_GILDED_DARKSTONE = HELPER.register("arcane_gilded_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE = HELPER.register("polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_SLAB = HELPER.register("polished_darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_STAIRS = HELPER.register("polished_darkstone_stairs", new StairsBlock(() -> POLISHED_DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_WALL = HELPER.register("polished_darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.register("polished_darkstone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.from(Blocks.STONE_PRESSURE_PLATE).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BUTTON = HELPER.register("polished_darkstone_button", new StoneButtonBlock(Block.Properties.from(Blocks.STONE_BUTTON).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> CHISELED_POLISHED_DARKSTONE = HELPER.register("chiseled_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> RUNIC_CHISELED_POLISHED_DARKSTONE = HELPER.register("runic_chiseled_polished_darkstone", new RunicChiseledPolishedDarkstone(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = HELPER.register("arcane_chiseled_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICKS = HELPER.register("polished_darkstone_bricks", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.register("polished_darkstone_brick_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.register("polished_darkstone_brick_stairs", new StairsBlock(() -> POLISHED_DARKSTONE_BRICKS.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_WALL = HELPER.register("polished_darkstone_brick_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.register("cracked_polished_darkstone_bricks", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE = HELPER.register("arcane_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.register("arcane_polished_darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.register("arcane_polished_darkstone_stairs", new StairsBlock(() -> ARCANE_POLISHED_DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.register("arcane_polished_darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.register("chiseled_arcane_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.register("arcane_polished_darkstone_pillar", new PillarBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_ROD = HELPER.register("arcane_polished_darkstone_rod", new ArcanePolishedDarkstoneRod(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_GLASS = HELPER.register("arcane_glass", new GlassBlock(Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_GLASS_PANE = HELPER.register("arcane_glass_pane", new PaneBlock(Block.Properties.from(Blocks.GLASS_PANE)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<ObsidianSkullBlock> OBSIDIAN_SKULL = HELPER.registerNoItem("obsidian_skull", new ObsidianSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL).lootFrom(OBSIDIAN_SKULL)));
    public static final RegistryObject<ObsidianSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerNoItem("eternal_obsidian_skull", new ObsidianSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("eternal_obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.from(Blocks.SKELETON_SKULL).lootFrom(ETERNAL_OBSIDIAN_SKULL)));
    public static final RegistryObject<UtremJarBlock> UTREM_JAR = HELPER.registerNoItem("utrem_jar", new UtremJarBlock(Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.register("pixie_utrem_jar", new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.register("corrupted_pixie_utrem_jar", new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.from(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FungyssBlock> FUNGYSS = HELPER.register("fungyss", new FungyssBlock(Block.Properties.from(Blocks.WARPED_FUNGUS).sound(SoundType.PLANT)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", new HugeMushroomBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.BLUE).hardnessAndResistance(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F).sound(SoundType.HYPHAE)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F).sound(SoundType.HYPHAE)));
    public static final RegistryObject<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairsBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", new StairsBlock(() -> NewModBlocks.FUNGYSS_PLANKS.get().getDefaultState(), AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WoodButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", new WoodButtonBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOL).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<NipaBlock> NIPA = HELPER.register("nipa", new NipaBlock(Block.Properties.from(Blocks.LARGE_FERN)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> BLACK_HOLE = HELPER.registerNoItem("black_hole", new BlackHoleBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F).notSolid()), ValhelsiaRenderType.CUTOUT);

}