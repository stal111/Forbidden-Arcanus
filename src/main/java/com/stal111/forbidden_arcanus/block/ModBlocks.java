package com.stal111.forbidden_arcanus.block;

import java.util.ArrayList;
import java.util.List;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tile.DarkBeaconTileEntity;
import com.stal111.forbidden_arcanus.block.tree.CherrywoodTree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class ModBlocks {
	
	public static List<Block> blockList = new ArrayList<Block>();
	
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
			dark_nether_star_block = null,
			runestone = null,
			arcane_crystal_ore = null,
			arcane_crystal_block = null,
			end_crystal_gem = null,
			bottle_block = null,
			pixi_in_a_bottle_block = null,
			corrupt_pixi_in_a_bottle_block = null,
			arcane_gold_block = null,
			arcane_gold_pressure_plate = null,
			arcane_gold_door = null,
			arcane_dragon_egg = null,
			edelwood_log = null,
			edelwood_planks = null,
			edelwood_stairs = null,
			edelwood_slab = null,
			edelwood_fence = null,
			edelwood_fence_gate = null,
			edelwood_pressure_plate = null,
			edelwood_button = null,
			edelwood_door = null,
			edelwood_trapdoor = null,
			arcane_edelwood_planks = null,
			cherrywood_log = null,
			cherrywood = null,
			cherrywood_sapling = null,
			cherrywood_leaves = null,
			cherrywood_planks = null,
			cherrywood_stairs = null,
			cherrywood_slab = null,
			cherrywood_fence = null,
			cherrywood_fence_gate = null,
			cherrywood_pressure_plate = null,
			cherrywood_button = null,
			cherrywood_door = null,
			cherrywood_trapdoor = null,
			carved_cherrywood_planks = null,
			mysterywood_log = null,
			mysterywood = null,
			mysterywood_leaves = null,
			mysterywood_sapling = null,
			mysterywood_planks = null,
			mysterywood_stairs = null,
			mysterywood_slab = null,
			mysterywood_fence = null,
			mysterywood_fence_gate = null,
			mysterywood_pressure_plate = null,
			mysterywood_button = null,
			mysterywood_door = null,
			mysterywood_trapdoor = null;
	
	public static final TileEntityType<DarkBeaconTileEntity> tile_dark_beacon = null;
	
	public static final Block ARCANE_BASE_BLOCK_STAIRS = new BasicBlock("arcane_base", Block.Properties.create(Material.ROCK));
	
	public static void register(RegistryEvent.Register<Block> registry) {
		registerAll(registry,
				new CutoutBlock("dark_beacon", Block.Properties.from(Blocks.BEACON)),
				new BasicBlock("arcane_base_block", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new BasicBlock("chiseled_arcane_base_block", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new ModStairsBlock("arcane_base_block_stairs", addProperties(Material.ROCK, 2.0F, 15.0F), ARCANE_BASE_BLOCK_STAIRS.getDefaultState()),
				new ModSlabBlock("arcane_base_block_slab", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new ModWallBlock("arcane_base_block_wall", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new PillarBlock("arcane_base_block_pillar", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new ArcaneBaseBlockRodBlock("arcane_base_block_rod", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new ModGlassBlock("arcane_glass", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new ModGlassPaneBlock("arcane_glass_pane", addProperties(Material.GLASS, 1.0F, 15.0F)),
				new RunicTenebrisFrameBlock("runic_tenebris_frame", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new RunicTenebrisFrameBlock("runic_tenebris_core", addProperties(Material.ROCK, 2.0F, 15.0F)),
				new BasicBlock("dark_stone", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new ModOreBlock("dark_runestone", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("arcane_dark_stone", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("dark_stone_bricks", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new ModStairsBlock("dark_stone_brick_stairs", addProperties(Material.ROCK, 1.0F, 10.0F), ARCANE_BASE_BLOCK_STAIRS.getDefaultState()),
				new ModSlabBlock("dark_stone_brick_slab", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new ModWallBlock("dark_stone_brick_wall", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("carved_dark_stone_bricks", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new RunicCarvedDarkStoneBricksBlock("runic_carved_dark_stone_bricks", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("white_runic_carved_dark_stone_bricks", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("arcane_carved_dark_stone_bricks", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new ModGlassBlock("runic_glass", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new ModGlassPaneBlock("runic_glass_pane", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new BasicBlock("dark_nether_star_block", Block.Properties.from(Blocks.DIAMOND_BLOCK)),
				new ModOreBlock("runestone", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new ModOreBlock("arcane_crystal_ore", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new BasicBlock("arcane_crystal_block", addProperties(Material.ROCK, 1.0F, 10.0F)),
				new CutoutBlock("end_crystal_gem", addProperties(Material.GLASS, 1.0F, 5.0F).lightValue(15)),
				new BottleBlock("bottle_block", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new BottleBlock("pixi_in_a_bottle_block", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new BottleBlock("corrupt_pixi_in_a_bottle_block", addProperties(Material.GLASS, 1.0F, 1.5F)),
				new BeaconBaseBlock("arcane_gold_block", Block.Properties.from(Blocks.GOLD_BLOCK)),
				new ModPressurePlateBlock("arcane_gold_pressure_plate", BlockPressurePlate.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.GOLD_BLOCK)),
				new ModDoorBlock("arcane_gold_door", Block.Properties.from(Blocks.GOLD_BLOCK)),
				new ArcaneDragonEggBlock("arcane_dragon_egg", Block.Properties.from(Blocks.DRAGON_EGG)),
				new EdelwoodLogBlock("edelwood_log", MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)),
				new BasicBlock("edelwood_planks", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModStairsBlock("edelwood_stairs", Block.Properties.from(Blocks.OAK_PLANKS), ARCANE_BASE_BLOCK_STAIRS.getDefaultState()),
				new ModSlabBlock("edelwood_slab", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceBlock("edelwood_fence", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceGateBlock("edelwood_fence_gate", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModPressurePlateBlock("edelwood_pressure_plate", BlockPressurePlate.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModButtonBlock("edelwood_button", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModDoorBlock("edelwood_door", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModTrapdoorBlock("edelwood_trapdoor", Block.Properties.from(Blocks.OAK_PLANKS)),
				new BasicBlock("arcane_edelwood_planks", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModLogBlock("cherrywood_log", MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)),
				new ModLogBlock("cherrywood", MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG)),
				new ModSaplingBlock("cherrywood_sapling", new CherrywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING)),
				new ModLeavesBlock("cherrywood_leaves", Block.Properties.from(Blocks.OAK_LEAVES)),
				new BasicBlock("cherrywood_planks", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModStairsBlock("cherrywood_stairs", Block.Properties.from(Blocks.OAK_PLANKS), ARCANE_BASE_BLOCK_STAIRS.getDefaultState()),
				new ModSlabBlock("cherrywood_slab", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceBlock("cherrywood_fence", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceGateBlock("cherrywood_fence_gate", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModPressurePlateBlock("cherrywood_pressure_plate", BlockPressurePlate.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModButtonBlock("cherrywood_button", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModDoorBlock("cherrywood_door", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModTrapdoorBlock("cherrywood_trapdoor", Block.Properties.from(Blocks.OAK_PLANKS)),
				new BasicBlock("carved_cherrywood_planks", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModLogBlock("mysterywood_log", MaterialColor.ADOBE, Block.Properties.from(Blocks.OAK_LOG)),
				new ModLogBlock("mysterywood", MaterialColor.ADOBE, Block.Properties.from(Blocks.OAK_LOG)),
				new ModLeavesBlock("mysterywood_leaves", Block.Properties.from(Blocks.OAK_LEAVES)),
				new ModSaplingBlock("mysterywood_sapling", new CherrywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING)),
				new BasicBlock("mysterywood_planks", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModStairsBlock("mysterywood_stairs", Block.Properties.from(Blocks.OAK_PLANKS), ARCANE_BASE_BLOCK_STAIRS.getDefaultState()),
				new ModSlabBlock("mysterywood_slab", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceBlock("mysterywood_fence", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModFenceGateBlock("mysterywood_fence_gate", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModPressurePlateBlock("mysterywood_pressure_plate", BlockPressurePlate.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModButtonBlock("mysterywood_button", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModDoorBlock("mysterywood_door", Block.Properties.from(Blocks.OAK_PLANKS)),
				new ModTrapdoorBlock("mysterywood_trapdoor", Block.Properties.from(Blocks.OAK_PLANKS))
				);	
	}
		
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> registry) {
		registerAll(registry,
				TileEntityType.Builder.create(DarkBeaconTileEntity::new).build(null).setRegistryName(Main.MODID, "tile_dark_beacon"));
	}
	
	
	public static void registerAll(RegistryEvent.Register<Block> registry, Block... blocks) {
		for (Block block : blocks) {
			blockList.add(block);
			registry.getRegistry().register(block);
		}
	}
	
	public static void registerAll(RegistryEvent.Register<TileEntityType<?>> registry, TileEntityType<?>... tiles) {
		for (TileEntityType<?> tile : tiles) {
			registry.getRegistry().register(tile);
		}
	}
	
	public static void registerItemBlocks(RegistryEvent.Register<Item> registry) {
		Item.Properties properties = new Item.Properties().group(Main.FORBIDDEN_ARCANUS);
		for (Block block : blockList) {
			Item item = new ItemBlock(block, properties);
			item.setRegistryName(block.getRegistryName());
			registry.getRegistry().register(item);
		}
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

}
