package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.trees.CherrywoodTree;
import com.stal111.forbidden_arcanus.block.trees.MysterywoodTree;
import com.stal111.forbidden_arcanus.item.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.potion.Effects;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(Main.MOD_ID)
public class ModBlocks {

	public static final Block dark_beacon = null;
	public static final Block arcane_base_block = null;
	public static final Block chiseled_arcane_base_block = null;
	public static final Block arcane_base_block_stairs = null;
	public static final Block arcane_base_block_slab = null;
	public static final Block arcane_base_block_wall = null;
	public static final Block arcane_base_block_pillar = null;
	public static final Block arcane_base_block_rod = null;
	public static final Block arcane_glass = null;
	public static final Block arcane_glass_pane = null;
	public static final Block runic_tenebris_frame = null;
	public static final Block runic_tenebris_core = null;
	public static final Block dark_stone = null;
	public static final Block dark_runestone = null;
	public static final Block arcane_dark_stone = null;
	public static final Block dark_stone_bricks = null;
	public static final Block dark_stone_brick_stairs = null;
	public static final Block dark_stone_brick_slab = null;
	public static final Block dark_stone_brick_wall = null;
	public static final Block carved_dark_stone_bricks = null;
	public static final Block runic_carved_dark_stone_bricks = null;
	public static final Block white_runic_carved_dark_stone_bricks = null;
	public static final Block arcane_carved_dark_stone_bricks = null;
	public static final Block runic_glass = null;
	public static final Block runic_glass_pane = null;
	public static final Block dark_runic_glass = null;
	public static final Block dark_runic_glass_pane = null;
	public static final Block dark_nether_star_block = null;
	public static final Block runestone = null;
	public static final Block arcane_crystal_ore = null;
	public static final Block arcane_crystal_block = null;
	public static final Block arcane_crystal_dust_wire = null;
	public static final Block end_crystal_gem = null;
	public static final Block bottle_block = null;
	public static final Block pixi_in_a_bottle_block = null;
	public static final Block corrupt_pixi_in_a_bottle_block = null;
	public static final Block arcane_gold_block = null;
	public static final Block arcane_gold_pressure_plate = null;
	public static final Block arcane_gold_door = null;
	public static final Block arcane_dragon_egg = null;
	public static final Block candle = null;
	public static final Block candle_lamp = null;
	public static final Block edelwood_log = null;
	public static final Block edelwood_planks = null;
	public static final Block edelwood_stairs = null;
	public static final Block edelwood_slab = null;
	public static final Block edelwood_fence = null;
	public static final Block edelwood_fence_gate = null;
	public static final Block edelwood_pressure_plate = null;
	public static final Block edelwood_button = null;
	public static final Block edelwood_trapdoor = null;
	public static final Block edelwood_door = null;
	public static final Block edelwood_sign = null;
	public static final Block edelwood_wall_sign = register("edelwood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
	public static final Block edelwood_ladder = null;
	public static final Block arcane_edelwood_planks = null;
	public static final Block cherrywood_log = null;
	public static final Block cherrywood = null;
	public static final Block stripped_cherrywood_log = null;
	public static final Block stripped_cherrywood = null;
	public static final Block cherrywood_sapling = null;
	public static final Block cherrywood_leaves = null;
	public static final Block cherrywood_planks = null;
	public static final Block cherrywood_stairs = null;
	public static final Block cherrywood_slab = null;
	public static final Block cherrywood_fence = null;
	public static final Block cherrywood_fence_gate = null;
	public static final Block cherrywood_pressure_plate = null;
	public static final Block cherrywood_button = null;
	public static final Block cherrywood_trapdoor = null;
	public static final Block cherrywood_door = null;
	public static final Block cherrywood_sign = null;
	public static final Block cherrywood_wall_sign = register("cherrywood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
	public static final Block carved_cherrywood_planks = null;
	public static final Block mysterywood_log = null;
	public static final Block mysterywood = null;
	public static final Block stripped_mysterywood_log = null;
	public static final Block stripped_mysterywood = null;
	public static final Block mysterywood_leaves = null;
	public static final Block mysterywood_sapling = null;
	public static final Block mysterywood_planks = null;
	public static final Block mysterywood_stairs = null;
	public static final Block mysterywood_slab = null;
	public static final Block mysterywood_fence = null;
	public static final Block mysterywood_fence_gate = null;
	public static final Block mysterywood_pressure_plate = null;
	public static final Block mysterywood_button = null;
	public static final Block mysterywood_trapdoor = null;
	public static final Block mysterywood_door = null;
	public static final Block mysterywood_sign = null;
	public static final Block mysterywood_wall_sign = register("mysterywood_wall_sign", new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN)), false);
	public static final Block yellow_orchid = null;
	public static final Block golden_orchid = null;
	public static final Block potted_cherrywood_sapling = null;
	public static final Block potted_mysterywood_sapling = null;
	public static final Block potted_yellow_orchid = null;
	public static final Block magical_farmland = null;
	public static final Block soulless_sand = null;
	public static final Block soulless_sandstone = null;
	public static final Block cut_soulless_sandstone = null;
	public static final Block smooth_soulless_sandstone = null;
	public static final Block soulless_sandstone_slab = null;
	public static final Block cut_soulless_sandstone_slab = null;
	public static final Block soulless_sandstone_stairs = null;
	public static final Block soulless_sandstone_wall = null;
	
	public static void register(RegistryEvent.Register<Block> registry) {
		registry.getRegistry().registerAll(
				register("dark_beacon", new DarkBeaconBlock(from(Blocks.BEACON)), Rarity.EPIC),
		register("arcane_base_block", new Block(addProperties(Material.ROCK, 2F, 15F))),
		register("chiseled_arcane_base_block", new Block(addProperties(Material.ROCK, 2F, 15F))),
		register("arcane_base_block_stairs", new ModStairsBlock(addProperties(Material.ROCK, 2.0F, 15.0F), Blocks.STONE.getDefaultState())),
		register("arcane_base_block_slab", new SlabBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("arcane_base_block_wall", new WallBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("arcane_base_block_pillar", new PillarBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("arcane_base_block_rod", new ArcaneBaseBlockRodBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("arcane_glass", new GlassBlock(from(Blocks.GLASS))),
		register("arcane_glass_pane", new ModGlassPaneBlock()),
		register("runic_tenebris_frame", new RunicTenebrisFrameBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("runic_tenebris_core", new RunicTenebrisCoreBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
		register("dark_stone", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("dark_stone_button", new ModStoneButtonBlock(Block.Properties.from(Blocks.STONE_BUTTON))),
		register("dark_stone_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.STONE_PRESSURE_PLATE))),
		register("dark_runestone", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F))),
		register("arcane_dark_stone", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("dark_stone_brick_stairs", new ModStairsBlock(addProperties(Material.ROCK, 1.5F, 6.0F), Blocks.STONE.getDefaultState())),
		register("dark_stone_brick_slab", new SlabBlock(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("dark_stone_brick_wall", new WallBlock(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("runic_carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("arcane_carved_dark_stone_bricks", new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
		register("runic_glass", new GlassBlock(from(Blocks.GLASS))),
		register("runic_glass_pane", new ModGlassPaneBlock()),
		register("dark_runic_glass", new GlassBlock(from(Blocks.GLASS))),
		register("dark_runic_glass_pane", new ModGlassPaneBlock()),
		register("dark_nether_star_block", new Block(Block.Properties.from(Blocks.DIAMOND_BLOCK))),
		register("runestone", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F))),
		register("arcane_crystal_ore", new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F))),
		register("arcane_crystal_block", new Block(addProperties(Material.ROCK, 1.0F, 10.0F))),
		register("end_crystal_gem", new CutoutBlock(addProperties(Material.GLASS, 1.0F, 5.0F).lightValue(15))),
		register("bottle_block", new BottleBlock(from(Blocks.GLASS))),
		register("pixi_in_a_bottle_block", new BottleBlock(from(Blocks.GLASS).lightValue(14))),
		register("corrupt_pixi_in_a_bottle_block", new BottleBlock(from(Blocks.GLASS).lightValue(9))),
		register("arcane_gold_block", new BeaconBaseBlock(Block.Properties.from(Blocks.GOLD_BLOCK))),
		register("arcane_gold_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.GOLD_BLOCK))),
		register("arcane_gold_door", new ModDoorBlock(Block.Properties.from(Blocks.GOLD_BLOCK))),
		register("arcane_dragon_egg", new ArcaneDragonEggBlock(Block.Properties.from(Blocks.DRAGON_EGG))),
		register("candle", new CandleBlock(addProperties(Material.MISCELLANEOUS))),
		//register("candelabra", new CandelabraBlock(addProperties(Material.IRON))),
		register("candle_lamp", new CandleLampBlock(addProperties(Material.ROCK))),
		register("edelwood_log", new EdelwoodLogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG))),
		register("edelwood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("edelwood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState())),
		register("edelwood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("edelwood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("edelwood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("edelwood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE))),
		register("edelwood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON))),
		register("edelwood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("edelwood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
				registerSign("edelwood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)), edelwood_wall_sign, Rarity.COMMON),
				edelwood_wall_sign,
				register("edelwood_ladder", new ModLadderBlock(Block.Properties.from(Blocks.LADDER))),
		register("arcane_edelwood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_log", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG))),
		register("cherrywood", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG))),
		register("stripped_cherrywood_log", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG))),
		register("stripped_cherrywood", new LogBlock(MaterialColor.PINK, Block.Properties.from(Blocks.OAK_LOG))),
		register("cherrywood_sapling", new ModSaplingBlock(new CherrywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING))),
		register("cherrywood_leaves", new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES))),
		register("cherrywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState())),
		register("cherrywood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE))),
		register("cherrywood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON))),
		register("cherrywood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("cherrywood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
				registerSign("cherrywood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)), cherrywood_wall_sign, Rarity.COMMON),
				cherrywood_wall_sign,
		register("carved_cherrywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_log", new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
		register("mysterywood", new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
		register("stripped_mysterywood_log", new LogBlock(MaterialColor.ADOBE, from(Blocks.OAK_LOG).lightValue(11))),
		register("stripped_mysterywood", new LogBlock(MaterialColor.ADOBE, from(Blocks.OAK_LOG).lightValue(11))),
		register("mysterywood_leaves", new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES))),
		register("mysterywood_sapling", new ModSaplingBlock(new MysterywoodTree(), Block.Properties.from(Blocks.OAK_SAPLING))),
		register("mysterywood_planks", new Block(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_stairs", new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS), Blocks.STONE.getDefaultState())),
		register("mysterywood_slab", new SlabBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_fence", new FenceBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_fence_gate", new FenceGateBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_pressure_plate", new ModPressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.from(Blocks.OAK_PRESSURE_PLATE))),
		register("mysterywood_button", new ModWoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON))),
		register("mysterywood_trapdoor", new ModTrapDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
		register("mysterywood_door", new ModDoorBlock(Block.Properties.from(Blocks.OAK_PLANKS))),
				registerSign("mysterywood_sign", new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN)), mysterywood_wall_sign, Rarity.COMMON),
				mysterywood_wall_sign,
		register("yellow_orchid", new FlowerBlock(Effects.GLOWING, 20, Block.Properties.from(Blocks.BLUE_ORCHID))),
		register("golden_orchid", new GoldenOrchidBlock(Block.Properties.from(Blocks.BLUE_ORCHID).tickRandomly()), false),
		register("magical_farmland", new ModFarmlandBlock(Block.Properties.from(Blocks.FARMLAND))),
		register("soulless_sand", new SoullessSandBlock(Block.Properties.from(Blocks.SOUL_SAND))),
		register("soulless_sandstone", new Block(from(Blocks.SANDSTONE))),
		register("cut_soulless_sandstone", new Block(from(Blocks.SANDSTONE))),
		register("smooth_soulless_sandstone", new Block(from(Blocks.SANDSTONE))),
		register("soulless_sandstone_slab", new SlabBlock(from(Blocks.SANDSTONE))),
		register("cut_soulless_sandstone_slab", new SlabBlock(from(Blocks.SANDSTONE))),
		register("soulless_sandstone_stairs", new StairsBlock(Blocks.SANDSTONE::getDefaultState, from(Blocks.SANDSTONE))),
		register("soulless_sandstone_wall", new WallBlock(from(Blocks.SANDSTONE_WALL))),
		register("potted_cherrywood_sapling", new FlowerPotBlock(null,(Supplier<? extends Block>) ModBlocks.cherrywood_sapling, Block.Properties.create(Material.MISCELLANEOUS)), false),
		register("potted_mysterywood_sapling", new FlowerPotBlock(null, (Supplier<? extends Block>) ModBlocks.mysterywood_sapling, Block.Properties.create(Material.MISCELLANEOUS)), false),
		register("potted_yellow_orchid", new FlowerPotBlock(null, (Supplier<? extends Block>) ModBlocks.yellow_orchid, addProperties(Material.MISCELLANEOUS)), false));
	}
	
	private static <T extends Block> T register(String name, T block) {
		return register(name, block, true);
	}

	private static <T extends Block> T register(String name, T block, boolean hasItem) {
		if (hasItem) {
			return register(name, block, new BlockItem(block, new Item.Properties()), Rarity.COMMON);
		}
		block.setRegistryName(ModUtils.location(name));
		return block;
	}

	private static <T extends Block> T register(String name, T block, Rarity rarity) {
		return register(name, block, new BlockItem(block, new Item.Properties()), rarity);
	}

	private static <T extends Block> T register(String name, T block, BlockItem item) {
		block.setRegistryName(ModUtils.location(name));
		if (item != null) {
			register(name, block, item, Rarity.COMMON);
		}
		return block;
	}

	private static <T extends Block> T register(String name, T block, BlockItem item, Rarity rarity) {
		block.setRegistryName(ModUtils.location(name));
		if (item != null) {
			if (!(item instanceof WallOrFloorItem)) {
				item = new BlockItem(block, new Item.Properties().group(Main.FORBIDDEN_ARCANUS).rarity(rarity));
			}
			item.setRegistryName(ModUtils.location(name));
			ForgeRegistries.ITEMS.register(item);
		}
		return block;
	}

	private static <T extends Block> T registerSign(String name, T block, Block wallSignBlock, Rarity rarity) {
		block.setRegistryName(ModUtils.location(name));
		ForgeRegistries.ITEMS.register(new SignItem(ModItems.properties().maxStackSize(16).rarity(rarity), block, wallSignBlock).setRegistryName(ModUtils.location(name)));
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
