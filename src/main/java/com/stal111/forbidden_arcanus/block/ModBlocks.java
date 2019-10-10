package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.trees.CherrywoodTree;
import com.stal111.forbidden_arcanus.block.trees.MysterywoodTree;
import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(Main.MOD_ID)
public class ModBlocks {

	public static final Block
			dark_beacon = null,
			arcane_base_block = null,
			chiseled_arcane_base_block = null,
			arcane_base_block_stairs = null,
			arcane_base_block_slab = null,
			arcane_base_block_wall = null,
			arcane_base_block_pillar = null,
			arcane_base_block_rod = null,
			arcane_glass = null,
			arcane_glass_pane = null,
			runic_tenebris_frame = null,
			runic_tenebris_core = null,
			dark_stone = null,
			dark_runestone = null,
			arcane_dark_stone = null,
			dark_stone_bricks = null,
			dark_stone_brick_stairs = null,
			dark_stone_brick_slab = null,
			dark_stone_brick_wall = null,
			carved_dark_stone_bricks = null,
			runic_carved_dark_stone_bricks = null,
			white_runic_carved_dark_stone_bricks = null,
			arcane_carved_dark_stone_bricks = null,
			runic_glass = null,
			runic_glass_pane = null,
			dark_runic_glass = null,
			dark_runic_glass_pane = null,
			dark_nether_star_block = null,
			runestone = null,
			arcane_crystal_ore = null,
			arcane_crystal_block = null,
			arcane_crystal_dust_wire = null,
			end_crystal_gem = null,
			bottle_block = null,
			pixi_in_a_bottle_block = null,
			corrupt_pixi_in_a_bottle_block = null,
			arcane_gold_block = null,
			arcane_gold_pressure_plate = null,
			arcane_gold_door = null,
			arcane_dragon_egg = null,
			candle = null,
			candle_lamp = null,
			edelwood_log = null,
			edelwood_planks = null,
			edelwood_stairs = null,
			edelwood_slab = null,
			edelwood_fence = null,
			edelwood_fence_gate = null,
			edelwood_pressure_plate = null,
			edelwood_button = null,
			edelwood_trapdoor = null,
			edelwood_door = null,
			edelwood_sign = null,
			edelwood_wall_sign = null,
			edelwood_ladder = null,
			arcane_edelwood_planks = null,
			cherrywood_log = null,
			cherrywood = null,
			stripped_cherrywood_log = null,
			stripped_cherrywood = null,
			cherrywood_sapling = null,
			cherrywood_leaves = null,
			cherrywood_planks = null,
			cherrywood_stairs = null,
			cherrywood_slab = null,
			cherrywood_fence = null,
			cherrywood_fence_gate = null,
			cherrywood_pressure_plate = null,
			cherrywood_button = null,
			cherrywood_trapdoor = null,
			cherrywood_door = null,
			cherrywood_sign = null,
			cherrywood_wall_sign = null,
			carved_cherrywood_planks = null,
			mysterywood_log = null,
			mysterywood = null,
			stripped_mysterywood_log = null,
			stripped_mysterywood = null,
			mysterywood_leaves = null,
			mysterywood_sapling = null,
			mysterywood_planks = null,
			mysterywood_stairs = null,
			mysterywood_slab = null,
			mysterywood_fence = null,
			mysterywood_fence_gate = null,
			mysterywood_pressure_plate = null,
			mysterywood_button = null,
			mysterywood_trapdoor = null,
			mysterywood_door = null,
			mysterywood_sign = null,
			mysterywood_wall_sign = null,
			yellow_orchid = null,
			golden_orchid = null,
			potted_cherrywood_sapling = null,
			potted_mysterywood_sapling = null,
			potted_yellow_orchid = null,
			magical_farmland = null,
			soulless_sand = null,
			soulless_sandstone = null,
			cut_soulless_sandstone = null,
			smooth_soulless_sandstone = null,
			soulless_sandstone_slab = null,
			cut_soulless_sandstone_slab = null,
			soulless_sandstone_stairs = null,
			soulless_sandstone_wall = null;
	
	public static void register(RegistryEvent.Register<Block> registry) {
		register("dark_beacon", new DarkBeaconBlock(from(Blocks.BEACON)), Rarity.EPIC);
		register("arcane_base_block", new Block(addProperties(Material.ROCK, 2F, 15F)));
		register("chiseled_arcane_base_block", new Block(addProperties(Material.ROCK, 2F, 15F)));
		register("arcane_base_block_stairs", new ModStairsBlock(addProperties(Material.ROCK, 2.0F, 15.0F), Blocks.STONE.getDefaultState()));
		register("arcane_base_block_slab", new SlabBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("arcane_base_block_wall", new WallBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("arcane_base_block_pillar", new PillarBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("arcane_base_block_rod", new ArcaneBaseBlockRodBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("arcane_glass", new GlassBlock(addProperties(Material.GLASS, 0.3F, 0.3F, SoundType.GLASS)));
		register("arcane_glass_pane", new ModGlassPaneBlock());
		register("runic_tenebris_frame", new RunicTenebrisFrameBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("runic_tenebris_core", new RunicTenebrisCoreBlock(addProperties(Material.ROCK, 2.0F, 15.0F)));
		register("dark_stone", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("dark_stone_button", new ModStoneButtonBlock(Block.Properties.from(Blocks.STONE_BUTTON)));
		register("dark_stone_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.STONE_PRESSURE_PLATE)));
		register("dark_runestone", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)));
		register("arcane_dark_stone", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("dark_stone_brick_stairs", new ModStairsBlock(addProperties(Material.ROCK, 1.5F, 6.0F), Blocks.STONE.getDefaultState()));
		register("dark_stone_brick_slab", new SlabBlock(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("dark_stone_brick_wall", new WallBlock(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("runic_carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("arcane_carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F)));
		register("runic_glass", new GlassBlock(addProperties(Material.GLASS, 0.3F, 0.3F, SoundType.GLASS)));
		register("runic_glass_pane", new ModGlassPaneBlock());
		register("dark_runic_glass", new GlassBlock(addProperties(Material.GLASS, 0.3F, 0.3F, SoundType.GLASS)));
		register("dark_runic_glass_pane", new ModGlassPaneBlock());
		register("dark_nether_star_block", new Block(Block.Properties.from(Blocks.DIAMOND_BLOCK)));
		register("runestone", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)));
		register("arcane_crystal_ore", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)));
		register("arcane_crystal_block", new Block(addProperties(Material.ROCK, 1.0F, 10.0F)));
		register("end_crystal_gem", new CutoutBlock(addProperties(Material.GLASS, 1.0F, 5.0F).lightValue(15)));
		register("bottle_block", new BottleBlock(from(Blocks.GLASS)));
		register("pixi_in_a_bottle_block", new BottleBlock(from(Blocks.GLASS).lightValue(14)));
		register("corrupt_pixi_in_a_bottle_block", new BottleBlock(from(Blocks.GLASS).lightValue(9)));
		register("arcane_gold_block", new BeaconBaseBlock(Block.Properties.from(Blocks.GOLD_BLOCK)));
		register("arcane_gold_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.GOLD_BLOCK)));
		register("arcane_gold_door", new ModDoorBlock(Block.Properties.from(Blocks.GOLD_BLOCK)));
		register("arcane_dragon_egg", new ArcaneDragonEggBlock(Block.Properties.from(Blocks.DRAGON_EGG)));
		register("candle", new CandleBlock(addProperties(Material.MISCELLANEOUS)));
		register("candle_lamp", new CandleLampBlock(addProperties(Material.ROCK)));
		register("edelwood_log", new EdelwoodLogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)));
		register("edelwood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState()));
		register("edelwood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)));
		register("edelwood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)));
		register("edelwood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("edelwood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)));
		register("edelwood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
		register("edelwood_ladder", new ModLadderBlock(Block.Properties.from(Blocks.LADDER)));
		register("arcane_edelwood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_log", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)));
		register("cherrywood", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)));
		register("stripped_cherrywood_log", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)));
		register("stripped_cherrywood", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)));
		register("cherrywood_sapling", new ModSaplingBlock(new CherrywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING)));
		register("cherrywood_leaves", new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));
		register("cherrywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState()));
		register("cherrywood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)));
		register("cherrywood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)));
		register("cherrywood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("cherrywood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)));
		register("cherrywood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
		register("carved_cherrywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_log", new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11)));
		register("mysterywood", new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11)));
		register("stripped_mysterywood_log", new LogBlock(MaterialColor.ADOBE, from(Blocks.OAK_LOG).lightValue(11)));
		register("stripped_mysterywood", new LogBlock(MaterialColor.ADOBE, from(Blocks.OAK_LOG).lightValue(11)));
		register("mysterywood_leaves", new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)));
		register("mysterywood_sapling", new ModSaplingBlock(new MysterywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING)));
		register("mysterywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState()));
		register("mysterywood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE)));
		register("mysterywood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON)));
		register("mysterywood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS)));
		register("mysterywood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)));
		register("mysterywood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
		register("yellow_orchid", new FlowerBlock(Effects.GLOWING, 20, Block.Properties.from(Blocks.BLUE_ORCHID)));
		register("golden_orchid", new GoldenOrchidBlock(Block.Properties.from(Blocks.BLUE_ORCHID).tickRandomly()), false);
		register("magical_farmland", new ModFarmlandBlock(Block.Properties.from(Blocks.FARMLAND)));
		register("soulless_sand", new SoullessSandBlock(Block.Properties.from(Blocks.SOUL_SAND)));
		register("soulless_sandstone", new Block(from(Blocks.SANDSTONE)));
		register("cut_soulless_sandstone", new Block(from(Blocks.SANDSTONE)));
		register("smooth_soulless_sandstone", new Block(from(Blocks.SANDSTONE)));
		register("soulless_sandstone_slab", new SlabBlock(from(Blocks.SANDSTONE)));
		register("cut_soulless_sandstone_slab", new SlabBlock(from(Blocks.SANDSTONE)));
		register("soulless_sandstone_stairs", new StairsBlock(Blocks.SANDSTONE::getDefaultState, from(Blocks.SANDSTONE)));
		register("soulless_sandstone_wall", new WallBlock(from(Blocks.SANDSTONE_WALL)));
		register("potted_cherrywood_sapling", new FlowerPotBlock(null,(Supplier<? extends Block>) ModBlocks.cherrywood_sapling, Block.Properties.create(Material.MISCELLANEOUS)), false);
		register("potted_mysterywood_sapling", new FlowerPotBlock(null, (Supplier<? extends Block>) ModBlocks.mysterywood_sapling, Block.Properties.create(Material.MISCELLANEOUS)), false);
		register("potted_yellow_orchid", new FlowerPotBlock(null, (Supplier<? extends Block>) ModBlocks.yellow_orchid, addProperties(Material.MISCELLANEOUS)), false);
	}
	
	private static <T extends Block> T register(String name, T block) {
		return register(name, block, true);
	}

	private static <T extends Block> T register(String name, T block, boolean hasItem) {
		if (hasItem) {
			BlockItem item = new BlockItem(block, new Item.Properties());
			return register(name, block, item, Rarity.COMMON);
		}
		block.setRegistryName(ModUtils.location(name));
		ForgeRegistries.BLOCKS.register(block);
		return block;
	}

	private static <T extends Block> T register(String name, T block, Rarity rarity) {
		BlockItem item = new BlockItem(block, new Item.Properties());
		return register(name, block, item, rarity);
	}

	private static <T extends Block> T register(String name, T block, BlockItem item, Rarity rarity) {
		block.setRegistryName(ModUtils.location(name));
		ForgeRegistries.BLOCKS.register(block);
		if (item != null) {
			item = new BlockItem(block, new Item.Properties().group(Main.FORBIDDEN_ARCANUS).rarity(rarity));
			item.setRegistryName(ModUtils.location(name));
			ForgeRegistries.ITEMS.register(item);
		}
		return block;
	}
	
	public static Block.Properties addProperties(Material material) {
		Block.Properties properties = Block.Properties.create(material);
		return properties;
		
	}
	
	public static Block.Properties addProperties(Material material, SoundType soundType) {
		Block.Properties properties = Block.Properties.create(material).sound(soundType);
		return properties;
		
	}
	
	public static Block.Properties addProperties(Material material, float hardness, float resistance) {
		Block.Properties properties = Block.Properties.create(material).hardnessAndResistance(hardness, resistance);
		return properties;
		
	}
	
	public static Block.Properties addProperties(Material material, float hardness, float resistance, SoundType soundType) {
		Block.Properties properties = Block.Properties.create(material).hardnessAndResistance(hardness, resistance).sound(soundType);
		return properties;
		
	}

	private static Block.Properties from(Block block) {
		return Block.Properties.from(block);
	}

}
