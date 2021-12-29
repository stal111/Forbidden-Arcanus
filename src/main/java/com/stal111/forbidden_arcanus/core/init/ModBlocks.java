package com.stal111.forbidden_arcanus.core.init;

import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.RodBlock;
import com.stal111.forbidden_arcanus.common.block.*;
import com.stal111.forbidden_arcanus.common.block.grower.CherrywoodTreeGrower;
import com.stal111.forbidden_arcanus.common.block.grower.MysterywoodTreeGrower;
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
import net.valhelsia.valhelsia_core.core.registry.block.BlockRegistryHelper;

/**
 * Mod Blocks <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlocks
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    public static final BlockRegistryHelper HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getBlockHelper();

    public static final RegistryObject<Block> DARKSTONE = HELPER.register("darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_CHISELED_DARKSTONE = HELPER.register("arcane_chiseled_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_SLAB = HELPER.register("darkstone_slab", new SlabBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_STAIRS = HELPER.register("darkstone_stairs", new StairBlock(() -> DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_WALL = HELPER.register("darkstone_wall", new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_GILDED_DARKSTONE = HELPER.register("arcane_gilded_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).sound(SoundType.GILDED_BLACKSTONE)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE = HELPER.register("polished_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_SLAB = HELPER.register("polished_darkstone_slab", new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_STAIRS = HELPER.register("polished_darkstone_stairs", new StairBlock(() -> POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_WALL = HELPER.register("polished_darkstone_wall", new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_PRESSURE_PLATE = HELPER.register("polished_darkstone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.copy(Blocks.STONE_PRESSURE_PLATE).strength(0.5F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BUTTON = HELPER.register("polished_darkstone_button", new StoneButtonBlock(Block.Properties.copy(Blocks.STONE_BUTTON).strength(0.5F)));
    public static final RegistryObject<Block> CHISELED_POLISHED_DARKSTONE = HELPER.register("chiseled_polished_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> RUNIC_CHISELED_POLISHED_DARKSTONE = HELPER.register("runic_chiseled_polished_darkstone", new RunicChiseledPolishedDarkstone(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = HELPER.register("arcane_chiseled_polished_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICKS = HELPER.register("polished_darkstone_bricks", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_SLAB = HELPER.register("polished_darkstone_brick_slab", new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_STAIRS = HELPER.register("polished_darkstone_brick_stairs", new StairBlock(() -> POLISHED_DARKSTONE_BRICKS.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_WALL = HELPER.register("polished_darkstone_brick_wall", new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = HELPER.register("cracked_polished_darkstone_bricks", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE = HELPER.register("arcane_polished_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_SLAB = HELPER.register("arcane_polished_darkstone_slab", new SlabBlock(Block.Properties.copy(Blocks.STONE_SLAB).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_STAIRS = HELPER.register("arcane_polished_darkstone_stairs", new StairBlock(() -> ARCANE_POLISHED_DARKSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.STONE_STAIRS).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_WALL = HELPER.register("arcane_polished_darkstone_wall", new WallBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = HELPER.register("chiseled_arcane_polished_darkstone", new Block(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<PillarBlock> ARCANE_POLISHED_DARKSTONE_PILLAR = HELPER.register("arcane_polished_darkstone_pillar", new PillarBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<RodBlock> ARCANE_POLISHED_DARKSTONE_ROD = HELPER.register("arcane_polished_darkstone_rod", new RodBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_PEDESTAL = HELPER.register("darkstone_pedestal", new PedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_DARKSTONE_PEDESTAL = HELPER.register("arcane_darkstone_pedestal", new PedestalBlock(Block.Properties.copy(Blocks.STONE).strength(4.5F, 8.0F).noOcclusion()));

    public static final RegistryObject<StellaArcanumBlock> STELLA_ARCANUM = HELPER.register("stella_arcanum", new StellaArcanumBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F)));
    public static final RegistryObject<OreBlock> XPETRIFIED_ORE = HELPER.register("xpetrified_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<OreBlock> ARCANE_CRYSTAL_ORE = HELPER.register("arcane_crystal_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(2, 5)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<OreBlock> DEEPSLATE_ARCANE_CRYSTAL_ORE = HELPER.register("deepslate_arcane_crystal_ore", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(2, 5)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<OreBlock> RUNIC_STONE = HELPER.register("runic_stone", new OreBlock(Block.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3.0F), UniformInt.of(4, 8)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<OreBlock> RUNIC_DEEPSLATE = HELPER.register("runic_deepslate", new OreBlock(Block.Properties.of(Material.STONE, MaterialColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.5F, 3.0F).sound(SoundType.DEEPSLATE), UniformInt.of(4, 8)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<OreBlock> RUNIC_DARKSTONE = HELPER.register("runic_darkstone", new OreBlock(Block.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(6.0F, 4.0F), UniformInt.of(4, 8)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> DARK_NETHER_STAR_BLOCK = HELPER.register("dark_nether_star_block", new Block(Block.Properties.of(Materials.DARK_NETHER_STAR).requiresCorrectToolForDrops().strength(10.0F, 1200.0F)));
    public static final RegistryObject<Block> PROCESSED_OBSIDIAN_BLOCK = HELPER.register("processed_obsidian_block", new Block(Block.Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = HELPER.register("arcane_gold_block", new Block(Block.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> STELLARITE_BLOCK = HELPER.register("stellarite_block", new Block(Block.Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistryObject<Block> ARCANE_CRYSTAL_BLOCK = HELPER.register("arcane_crystal_block", new Block(Block.Properties.of(Material.STONE, MaterialColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().strength(1.0F, 3.0F).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> RUNE_BLOCK = HELPER.register("rune_block", new Block(Block.Properties.of(Materials.RUNE).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> DARK_RUNE_BLOCK = HELPER.register("dark_rune_block", new Block(Block.Properties.of(Materials.RUNE, MaterialColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5.0F, 6.0F)));
    public static final RegistryObject<Block> ARCANE_GOLDEN_GLASS = HELPER.register("arcane_golden_glass", new GlassBlock(Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> RUNIC_GLASS = HELPER.register("runic_glass", new GlassBlock(Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> DARK_RUNIC_GLASS = HELPER.register("dark_runic_glass", new GlassBlock(Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_GOLDEN_GLASS_PANE = HELPER.register("arcane_golden_glass_pane", new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> RUNIC_GLASS_PANE = HELPER.register("runic_glass_pane", new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> DARK_RUNIC_GLASS_PANE = HELPER.register("dark_runic_glass_pane", new IronBarsBlock(Block.Properties.copy(Blocks.GLASS_PANE)), ValhelsiaRenderType.CUTOUT);

    public static final RegistryObject<SoullessSandBlock> SOULLESS_SAND = HELPER.register("soulless_sand", new SoullessSandBlock(Block.Properties.copy(Blocks.SOUL_SAND).speedFactor(1.0F)));
    public static final RegistryObject<Block> SOULLESS_SANDSTONE = HELPER.register("soulless_sandstone", new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> CUT_SOULLESS_SANDSTONE = HELPER.register("cut_soulless_sandstone", new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<Block> POLISHED_SOULLESS_SANDSTONE = HELPER.register("polished_soulless_sandstone", new Block(Block.Properties.copy(Blocks.SANDSTONE)));
    public static final RegistryObject<SlabBlock> SOULLESS_SANDSTONE_SLAB = HELPER.register("soulless_sandstone_slab", new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<SlabBlock> CUT_SOULLESS_SANDSTONE_SLAB = HELPER.register("cut_soulless_sandstone_slab", new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<SlabBlock> POLISHED_SOULLESS_SANDSTONE_SLAB = HELPER.register("polished_soulless_sandstone_slab", new SlabBlock(Block.Properties.copy(Blocks.SANDSTONE_SLAB)));
    public static final RegistryObject<StairBlock> SOULLESS_SANDSTONE_STAIRS = HELPER.register("soulless_sandstone_stairs", new StairBlock(() -> SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS)));
    public static final RegistryObject<StairBlock> POLISHED_SOULLESS_SANDSTONE_STAIRS = HELPER.register("polished_soulless_sandstone_stairs", new StairBlock(() -> POLISHED_SOULLESS_SANDSTONE.get().defaultBlockState(), Block.Properties.copy(Blocks.SANDSTONE_STAIRS)));
    public static final RegistryObject<WallBlock> SOULLESS_SANDSTONE_WALL = HELPER.register("soulless_sandstone_wall", new WallBlock(Block.Properties.copy(Blocks.SANDSTONE_WALL)));

    public static final RegistryObject<FungyssBlock> FUNGYSS = HELPER.register("fungyss", new FungyssBlock(Block.Properties.copy(Blocks.WARPED_FUNGUS).sound(SoundType.GRASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<SaplingBlock> CHERRYWOOD_SAPLING = HELPER.register("cherrywood_sapling", new SaplingBlock(new CherrywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<SaplingBlock> MYSTERYWOOD_SAPLING = HELPER.register("mysterywood_sapling", new SaplingBlock(new MysterywoodTreeGrower(), Block.Properties.copy(Blocks.OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<GrowingEdelwoodBlock> GROWING_EDELWOOD = HELPER.register("growing_edelwood", new GrowingEdelwoodBlock(Block.Properties.copy(Blocks.OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<HugeMushroomBlock> FUNGYSS_BLOCK = HELPER.register("fungyss_block", new HugeMushroomBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BLUE).strength(0.2F).sound(SoundType.WOOD)));
    public static final RegistryObject<LeavesBlock> CHERRYWOOD_LEAVES = HELPER.register("cherrywood_leaves", new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<LeavesBlock> MYSTERYWOOD_LEAVES = HELPER.register("mysterywood_leaves", new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<LeavesBlock> NUGGETY_MYSTERYWOOD_LEAVES = HELPER.register("nuggety_mysterywood_leaves", new LeavesBlock(Block.Properties.copy(Blocks.OAK_LEAVES)));

    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_STEM = HELPER.register("fungyss_stem", new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<RotatedPillarBlock> CHERRYWOOD_LOG = HELPER.register("cherrywood_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> MYSTERYWOOD_LOG = HELPER.register("mysterywood_log", new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<EdelwoodLogBlock> EDELWOOD_LOG = HELPER.register("edelwood_log", new EdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<CarvedEdelwoodLogBlock> CARVED_EDELWOOD_LOG = HELPER.register("carved_edelwood_log", new CarvedEdelwoodLogBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN).randomTicks()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<EdelwoodBranchBlock> EDELWOOD_BRANCH = HELPER.register("edelwood_branch", new EdelwoodBranchBlock(Block.Properties.copy(Blocks.OAK_LOG).color(MaterialColor.COLOR_BROWN)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRYWOOD_LOG = HELPER.register("stripped_cherrywood_log", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> STRIPPED_MYSTERYWOOD_LOG = HELPER.register("stripped_mysterywood_log", new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> FUNGYSS_HYPHAE = HELPER.register("fungyss_hyphae", new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F).sound(SoundType.STEM)));
    public static final RegistryObject<RotatedPillarBlock> CHERRYWOOD = HELPER.register("cherrywood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> MYSTERYWOOD = HELPER.register("mysterywood", new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_CHERRYWOOD = HELPER.register("stripped_cherrywood", new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<MysterywoodLogBlock> STRIPPED_MYSTERYWOOD = HELPER.register("stripped_mysterywood", new MysterywoodLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> FUNGYSS_PLANKS = HELPER.register("fungyss_planks", new Block(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHERRYWOOD_PLANKS = HELPER.register("cherrywood_planks", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> CARVED_CHERRYWOOD_PLANKS = HELPER.register("carved_cherrywood_planks", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> MYSTERYWOOD_PLANKS = HELPER.register("mysterywood_planks", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> EDELWOOD_PLANKS = HELPER.register("edelwood_planks", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_EDELWOOD_PLANKS = HELPER.register("arcane_edelwood_planks", new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<SlabBlock> FUNGYSS_SLAB = HELPER.register("fungyss_slab", new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<SlabBlock> CHERRYWOOD_SLAB = HELPER.register("cherrywood_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<SlabBlock> MYSTERYWOOD_SLAB = HELPER.register("mysterywood_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<SlabBlock> EDELWOOD_SLAB = HELPER.register("edelwood_slab", new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
    public static final RegistryObject<StairBlock> FUNGYSS_STAIRS = HELPER.register("fungyss_stairs", new StairBlock(() -> ModBlocks.FUNGYSS_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<StairBlock> CHERRYWOOD_STAIRS = HELPER.register("cherrywood_stairs", new StairBlock(() -> ModBlocks.CHERRYWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<StairBlock> MYSTERYWOOD_STAIRS = HELPER.register("mysterywood_stairs", new StairBlock(() -> ModBlocks.MYSTERYWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
    public static final RegistryObject<StairBlock> EDELWOOD_STAIRS = HELPER.register("edelwood_stairs", new StairBlock(() -> ModBlocks.EDELWOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));

    public static final RegistryObject<DoorBlock> ARCANE_GOLD_DOOR = HELPER.register("arcane_gold_door", new DoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<DoorBlock> FUNGYSS_DOOR = HELPER.register("fungyss_door", new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<DoorBlock> CHERRYWOOD_DOOR = HELPER.register("cherrywood_door", new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<DoorBlock> MYSTERYWOOD_DOOR = HELPER.register("mysterywood_door", new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<DoorBlock> EDELWOOD_DOOR = HELPER.register("edelwood_door", new DoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<TrapDoorBlock> ARCANE_GOLD_TRAPDOOR = HELPER.register("arcane_gold_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.GOLD).requiresCorrectToolForDrops().strength(3.0F).sound(SoundType.METAL).noOcclusion().isValidSpawn(ModBlocks::never)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<TrapDoorBlock> FUNGYSS_TRAPDOOR = HELPER.register("fungyss_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<TrapDoorBlock> CHERRYWOOD_TRAPDOOR = HELPER.register("cherrywood_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<TrapDoorBlock> MYSTERYWOOD_TRAPDOOR = HELPER.register("mysterywood_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<TrapDoorBlock> EDELWOOD_TRAPDOOR = HELPER.register("edelwood_trapdoor", new TrapDoorBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn(ModBlocks::never)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FenceBlock> FUNGYSS_FENCE = HELPER.register("fungyss_fence", new FenceBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceBlock> CHERRYWOOD_FENCE = HELPER.register("cherrywood_fence", new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceBlock> MYSTERYWOOD_FENCE = HELPER.register("mysterywood_fence", new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceBlock> EDELWOOD_FENCE = HELPER.register("edelwood_fence", new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
    public static final RegistryObject<FenceGateBlock> FUNGYSS_FENCE_GATE = HELPER.register("fungyss_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<FenceGateBlock> CHERRYWOOD_FENCE_GATE = HELPER.register("cherrywood_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<FenceGateBlock> MYSTERYWOOD_FENCE_GATE = HELPER.register("mysterywood_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final RegistryObject<FenceGateBlock> EDELWOOD_FENCE_GATE = HELPER.register("edelwood_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE)));
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> FUNGYSS_SIGN  = HELPER.createSignBlock("fungyss", MaterialColor.WOOL, ModWoodTypes.FUNGYSS);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> CHERRYWOOD_SIGN  = HELPER.createSignBlock("cherrywood", MaterialColor.COLOR_PINK, ModWoodTypes.CHERRYWOOD);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> MYSTERYWOOD_SIGN  = HELPER.createSignBlock("mysterywood", MaterialColor.COLOR_BROWN, ModWoodTypes.MYSTERYWOOD);
    public static final Pair<RegistryObject<ValhelsiaStandingSignBlock>, RegistryObject<ValhelsiaWallSignBlock>> EDELWOOD_SIGN  = HELPER.createSignBlock("edelwood", MaterialColor.COLOR_BROWN, ModWoodTypes.EDELWOOD);
    public static final RegistryObject<LadderBlock> EDELWOOD_LADDER = HELPER.register("edelwood_ladder", new LadderBlock(BlockBehaviour.Properties.copy(Blocks.LADDER)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<WoodButtonBlock> FUNGYSS_BUTTON = HELPER.register("fungyss_button", new WoodButtonBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<WoodButtonBlock> CHERRYWOOD_BUTTON = HELPER.register("cherrywood_button", new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<WoodButtonBlock> MYSTERYWOOD_BUTTON = HELPER.register("mysterywood_button", new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<WoodButtonBlock> EDELWOOD_BUTTON = HELPER.register("edelwood_button", new WoodButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON)));
    public static final RegistryObject<PressurePlateBlock> ARCANE_GOLD_PRESSURE_PLATE = HELPER.register("arcane_gold_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> FUNGYSS_PRESSURE_PLATE = HELPER.register("fungyss_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.WOOL).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<PressurePlateBlock> CHERRYWOOD_PRESSURE_PLATE = HELPER.register("cherrywood_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> MYSTERYWOOD_PRESSURE_PLATE = HELPER.register("mysterywood_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistryObject<PressurePlateBlock> EDELWOOD_PRESSURE_PLATE = HELPER.register("edelwood_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PRESSURE_PLATE)));

    public static final RegistryObject<HephaestusForgeBlock> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", new HephaestusForgeBlock(Block.Properties.copy(Blocks.OBSIDIAN).strength(38.0F, 1200.0F).noOcclusion()), ValhelsiaRenderType.CUTOUT);

    public static final RegistryObject<ArcaneDragonEggBlock> ARCANE_DRAGON_EGG = HELPER.register("arcane_dragon_egg", new ArcaneDragonEggBlock(Block.Properties.copy(Blocks.DRAGON_EGG).lightLevel(value -> 5)));

    public static final RegistryObject<Block> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", new ArcaneCrystalObeliskBlock(Block.Properties.of(Material.STONE).strength(1.0F, 10.0F)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<ObsidianSkullBlock> OBSIDIAN_SKULL = HELPER.registerNoItem("obsidian_skull", new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(OBSIDIAN_SKULL)));
    public static final RegistryObject<ObsidianSkullBlock> ETERNAL_OBSIDIAN_SKULL = HELPER.registerNoItem("eternal_obsidian_skull", new ObsidianSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL)));
    public static final RegistryObject<ObsidianWallSkullBlock> ETERNAL_OBSIDIAN_WALL_SKULL = HELPER.registerNoItem("eternal_obsidian_wall_skull", new ObsidianWallSkullBlock(Block.Properties.copy(Blocks.SKELETON_SKULL).lootFrom(ETERNAL_OBSIDIAN_SKULL)));
    public static final RegistryObject<UtremJarBlock> UTREM_JAR = HELPER.registerNoItem("utrem_jar", new UtremJarBlock(Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> PIXIE_UTREM_JAR = HELPER.register("pixie_utrem_jar", new PixieUtremJarBlock(ModItems.PIXIE, Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<PixieUtremJarBlock> CORRUPTED_PIXIE_UTREM_JAR = HELPER.register("corrupted_pixie_utrem_jar", new PixieUtremJarBlock(ModItems.CORRUPTED_PIXIE, Block.Properties.copy(Blocks.GLASS)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<NipaBlock> NIPA = HELPER.register("nipa", new NipaBlock(Block.Properties.copy(Blocks.LARGE_FERN)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<NoFluidOverlayBlock> PETRIFIED_ROOT = HELPER.register("petrified_root", new NoFluidOverlayBlock(Block.Properties.copy(Blocks.STONE).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<Block> BLACK_HOLE = HELPER.registerNoItem("black_hole", new BlackHoleBlock(Block.Properties.copy(Blocks.STONE).strength(2.0F, 8.0F).noOcclusion()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<ChainBlock> ARCANE_GOLDEN_CHAIN = HELPER.register("arcane_golden_chain", new ChainBlock(BlockBehaviour.Properties.copy(Blocks.CHAIN)), ValhelsiaRenderType.CUTOUT_MIPPED);
    public static final RegistryObject<FlowerBlock> YELLOW_ORCHID = HELPER.register("yellow_orchid", new FlowerBlock(MobEffects.GLOWING, 10, BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<GoldenOrchidBlock> GOLDEN_ORCHID = HELPER.registerNoItem("golden_orchid", new GoldenOrchidBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_ORCHID).randomTicks()), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FarmBlock> MAGICAL_FARMLAND = HELPER.register("magical_farmland", new FarmBlock(BlockBehaviour.Properties.copy(Blocks.FARMLAND).randomTicks()));
    public static final RegistryObject<StrangeRootBlock> STRANGE_ROOT = HELPER.registerNoItem("strange_root", new StrangeRootBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)), ValhelsiaRenderType.CUTOUT);

    public static final RegistryObject<FlowerPotBlock> POTTED_CHERRYWOOD_SAPLING = HELPER.registerNoItem("potted_cherrywood_sapling", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, CHERRYWOOD_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FlowerPotBlock> POTTED_MYSTERYWOOD_SAPLING = HELPER.registerNoItem("potted_mysterywood_sapling", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, MYSTERYWOOD_SAPLING, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);
    public static final RegistryObject<FlowerPotBlock> POTTED_YELLOW_ORCHID = HELPER.registerNoItem("potted_yellow_orchid", new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.YELLOW_ORCHID, Block.Properties.copy(Blocks.POTTED_OAK_SAPLING)), ValhelsiaRenderType.CUTOUT);

    private static boolean never(BlockState state, BlockGetter level, BlockPos pos, EntityType<?> entityType) {
        return false;
    }

    public static class Materials {
        public static final Material DARK_NETHER_STAR = (new Material.Builder(MaterialColor.COLOR_PURPLE)).build();
        public static final Material RUNE = (new Material.Builder(MaterialColor.COLOR_MAGENTA)).build();
    }
}
