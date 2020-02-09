package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.*;
import com.stal111.forbidden_arcanus.block.trees.CherrywoodTree;
import com.stal111.forbidden_arcanus.block.trees.MysterywoodTree;
import com.stal111.forbidden_arcanus.util.ModRenderType;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.potion.Effects;

public enum ModBlocks {

    DARK_BEACON(new DarkBeaconBlock(from(Blocks.BEACON)), ModRenderType.CUTOUT),
    ARCANE_BASE_BLOCK(new Block(addProperties(Material.ROCK, 2F, 15F))),
    CHISELED_ARCANE_BASE_BLOCK(new Block(addProperties(Material.ROCK, 2F, 15F))),
    ARCANE_BASE_BLOCK_SLAB(new SlabBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
    ARCANE_BASE_BLOCK_STAIRS(new StairsBlock(ARCANE_BASE_BLOCK.getBlock().getDefaultState(), addProperties(Material.ROCK, 2.0F, 15.0F))),
    ARCANE_BASE_BLOCK_WALL(new WallBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
    ARCANE_BASE_BLOCK_PILLAR(new PillarBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
    ARCANE_BASE_BLOCK_ROD(new ArcaneBaseBlockRodBlock(addProperties(Material.ROCK, 2.0F, 15.0F))),
    ARCANE_GLASS(new GlassBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    ARCANE_GLASS_PANE(new PaneBlock(from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT),
    RUNIC_TENEBRIS_FRAME(new RunicTenebrisFrameBlock(addProperties(Material.ROCK, 2.0F, 15.0F).func_226896_b_())),
    RUNIC_TENEBRIS_CORE(new RunicTenebrisCoreBlock(addProperties(Material.ROCK, 2.0F, 15.0F).func_226896_b_()), ModRenderType.CUTOUT),
    DARK_STONE(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_RUNESTONE(new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)), ModRenderType.CUTOUT),
    DARK_STONE_SLAB(new SlabBlock(addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_STAIRS(new StairsBlock(DARK_STONE.getBlock().getDefaultState(), addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_BUTTON(new StoneButtonBlock((from(Blocks.STONE_BUTTON)))),
    DARK_STONE_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.STONE_PRESSURE_PLATE))),
    ARCANE_DARK_STONE(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_BRICKS(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    CHISELED_DARK_STONE_BRICKS(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    RUNIC_CHISELED_DARK_STONE_BRICKS(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    ARCANE_CHISELED_DARK_STONE_BRICKS(new Block(addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_BRICK_SLAB(new SlabBlock(addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_BRICK_STAIRS(new StairsBlock(DARK_STONE_BRICKS.getBlock().getDefaultState(), addProperties(Material.ROCK, 1.5F, 6.0F))),
    DARK_STONE_BRICK_WALL(new WallBlock(addProperties(Material.ROCK, 1.5F, 6.0F))),
    RUNIC_GLASS(new GlassBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    RUNIC_GLASS_PANE(new PaneBlock(from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT),
    DARK_RUNIC_GLASS(new GlassBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    DARK_RUNIC_GLASS_PANE(new PaneBlock(from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT),
    RUNESTONE(new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)), ModRenderType.CUTOUT),
    ARCANE_CRYSTAL_ORE(new ModOreBlock(addProperties(Material.ROCK, 3.0F, 3.0F)), ModRenderType.CUTOUT),
    ARCANE_CRYSTAL_BLOCK(new TranslucentBlock(addProperties(Material.ROCK, 1.0F, 10.0F).func_226896_b_()), ModRenderType.TRANSLUCENT),
    ARCANE_CRYSTAL_OBELISK(new ArcaneCrystalObeliskBlock(addProperties(Material.ROCK, 1.0F, 10.0F))),
    DARK_NETHER_STAR_BLOCk(new Block(from(Blocks.DIAMOND_BLOCK))),
    END_CRYSTAL_GEM(new CutoutBlock(addProperties(Material.GLASS, 1.0F, 5.0F).lightValue(15).func_226896_b_()), ModRenderType.TRANSLUCENT),
    BOTTLE_BLOCK(new BottleBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    PIXIE_IN_A_BOTTLE_BLOCK(new BottleBlock(from(Blocks.GLASS).lightValue(14)), ModRenderType.CUTOUT),
    CORRUPTED_PIXIE_IN_A_BOTTLE_BLOCK(new BottleBlock(from(Blocks.GLASS).lightValue(9)), ModRenderType.CUTOUT),
    ARCANE_GOLD_BLOCK(new BeaconBaseBlock(from(Blocks.GOLD_BLOCK))),
    ARCANE_GOLD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.GOLD_BLOCK))),
    ARCANE_GOLD_DOOR(new DoorBlock(from(Blocks.GOLD_BLOCK).func_226896_b_()), ModRenderType.CUTOUT),
    ARCANE_DRAGON_EGG(new ArcaneDragonEggBlock(from(Blocks.DRAGON_EGG))),
    CANDLE(new CandleBlock(addProperties(Material.MISCELLANEOUS)), ModRenderType.CUTOUT),
    STONE_CANDELABRA(new CandelabraBlock(addProperties(Material.ROCK, 1.5F, 6.0F)), false, ModRenderType.CUTOUT),
    WALL_STONE_CANDELABRA(new WallCandelabraBlock(addProperties(Material.ROCK, 1.5F, 6.0F)), false, ModRenderType.CUTOUT),
    HANGING_STONE_CANDELABRA(new HangingCandelabraBlock(addProperties(Material.ROCK, 1.5F, 6.0F).func_226896_b_()), false, ModRenderType.CUTOUT),
    IRON_CANDELABRA(new CandelabraBlock(addProperties(Material.IRON, 5.0F, 6.0F)), false, ModRenderType.CUTOUT),
    WALL_IRON_CANDELABRA(new WallCandelabraBlock(addProperties(Material.IRON, 5.0F, 6.0F)), false, ModRenderType.CUTOUT),
    HANGING_IRON_CANDELABRA(new HangingCandelabraBlock(addProperties(Material.IRON, 5.0F, 6.0F).func_226896_b_()), false, ModRenderType.CUTOUT),
    ARCANE_GOLDEN_CANDELABRA(new CandelabraBlock(addProperties(Material.IRON, 3.0F, 6.0F)), false, ModRenderType.CUTOUT),
    WALL_ARCANE_GOLDEN_CANDELABRA(new WallCandelabraBlock(addProperties(Material.IRON, 3.0F, 6.0F)), false, ModRenderType.CUTOUT),
    HANGING_ARCANE_GOLDEN_CANDELABRA(new HangingCandelabraBlock(addProperties(Material.IRON, 3.0F, 6.0F).func_226896_b_()), false, ModRenderType.CUTOUT),
    IRON_CHAIN(new ChainBlock(addProperties(Material.IRON, 5.0F, 6.0F)), ModRenderType.CUTOUT),
    ARCANE_GOLDEN_CHAIN(new ChainBlock(addProperties(Material.IRON, 3.0F, 6.0F)), ModRenderType.CUTOUT),
    CANDLE_LAMP(new CandleLampBlock(addProperties(Material.ROCK)), ModRenderType.CUTOUT),
    EDELWOOD_LOG(new EdelwoodLogBlock(MaterialColor.BROWN, from(Blocks.OAK_LOG).tickRandomly()), ModRenderType.CUTOUT),
    EDELWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    ARCANE_EDELWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    EDELWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    EDELWOOD_STAIRS(new StairsBlock(EDELWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    EDELWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    EDELWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    EDELWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    EDELWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    EDELWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    EDELWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR))),
    EDELWOOD_LADDER(new EdelwoodLadderBlock(from(Blocks.LADDER)), ModRenderType.CUTOUT),
    EDELWOOD_WALL_SIGN(Main.EDELWOOD_WALL_SIGN, false),
    EDELWOOD_SIGN(Main.EDELWOOD_SIGN, new SignItem(new Item.Properties().group(Main.FORBIDDEN_ARCANUS), Main.EDELWOOD_SIGN, EDELWOOD_WALL_SIGN.getBlock())),
    CHERRYWOOD_LOG(new LogBlock(MaterialColor.PINK, from(Blocks.OAK_LOG))),
    CHERRYWOOD(new LogBlock(MaterialColor.PINK, from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD_LOG(new LogBlock(MaterialColor.PINK, from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD(new LogBlock(MaterialColor.PINK, from(Blocks.OAK_LOG))),
    CHERRYWOOD_LEAVES(new LeavesBlock(from(Blocks.OAK_LEAVES))),
    CHERRYWOOD_SAPLING(new ModSaplingBlock(new CherrywoodTree(), from(Blocks.OAK_SAPLING)), ModRenderType.CUTOUT),
    CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CARVED_CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CHERRYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    CHERRYWOOD_STAIRS(new StairsBlock(CHERRYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    CHERRYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    CHERRYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    CHERRYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    CHERRYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    CHERRYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    CHERRYWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR)), ModRenderType.CUTOUT),
    CHERRYWOOD_WALL_SIGN(Main.CHERRYWOOD_WALL_SIGN, false),
    CHERRYWOOD_SIGN(Main.CHERRYWOOD_SIGN, new SignItem(new Item.Properties().group(Main.FORBIDDEN_ARCANUS), Main.CHERRYWOOD_SIGN, CHERRYWOOD_WALL_SIGN.getBlock())),
    MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
    MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
    STRIPPED_MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
    STRIPPED_MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG).lightValue(11))),
    MYSTERYWOOD_LEAVES(new LeavesBlock(from(Blocks.OAK_LEAVES))),
    MYSTERYWOOD_SAPLING(new ModSaplingBlock(new MysterywoodTree(), from(Blocks.OAK_SAPLING)), ModRenderType.CUTOUT),
    MYSTERYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    MYSTERYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    MYSTERYWOOD_STAIRS(new StairsBlock(MYSTERYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    MYSTERYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    MYSTERYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    MYSTERYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    MYSTERYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    MYSTERYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    MYSTERYWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR)), ModRenderType.CUTOUT),
    MYSTERYWOOD_WALL_SIGN(Main.MYSTERYWOOD_WALL_SIGN, false),
    MYSTERYWOOD_SIGN(Main.MYSTERYWOOD_SIGN, new SignItem(new Item.Properties().group(Main.FORBIDDEN_ARCANUS), Main.MYSTERYWOOD_SIGN, MYSTERYWOOD_WALL_SIGN.getBlock())),
    YELLOW_ORCHID(new FlowerBlock(Effects.GLOWING, 25, from(Blocks.BLUE_ORCHID)), ModRenderType.CUTOUT),
    GOLDEN_ORCHID(new GoldenOrchidBlock(from(Blocks.BLUE_ORCHID).tickRandomly()), false, ModRenderType.CUTOUT),
    MAGICAL_FARMLAND(new ModFarmlandBlock(from(Blocks.FARMLAND))),
    SOULLESS_SAND(new SoullessSandBlock(from(Blocks.SOUL_SAND))),
    SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    CUT_SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    SMOOTH_SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    SOULLESS_SANDSTONE_SLAB(new SlabBlock(from(Blocks.SANDSTONE_SLAB))),
    CUT_SOULLESS_SANDSTONE_SLAB(new SlabBlock(from(Blocks.SANDSTONE_SLAB))),
    SOULLESS_SANDSTONE_STAIRS(new StairsBlock(SOULLESS_SANDSTONE.getState(), from(Blocks.SANDSTONE_STAIRS))),
    SOULLESS_SANDSTONE_WALL(new WallBlock(from(Blocks.SANDSTONE_WALL))),
    POTTED_CHERRYWOOD_SAPLING(new FlowerPotBlock(CHERRYWOOD_SAPLING.getBlock(), from(Blocks.POTTED_OAK_SAPLING)), false, ModRenderType.CUTOUT),
    POTTED_MYSTERYWOOD_SAPLING(new FlowerPotBlock(MYSTERYWOOD_SAPLING.getBlock(), from(Blocks.POTTED_OAK_SAPLING)), false, ModRenderType.CUTOUT),
    POTTED_YELLOW_ORCHID(new FlowerPotBlock(YELLOW_ORCHID.getBlock(), from(Blocks.POTTED_OAK_SAPLING)), false, ModRenderType.CUTOUT);

    private final Block block;
    private final BlockItem item;
    private final boolean hasItem;
    private final ModRenderType renderType;

    ModBlocks(Block block) {
        this.block = block;
        this.item = null;
        this.hasItem = true;
        this.renderType = ModRenderType.SOLID;
    }

    ModBlocks(Block block, boolean hasItem, ModRenderType renderType) {
        this.block = block;
        this.item = null;
        this.hasItem = hasItem;
        this.renderType = renderType;
    }

    ModBlocks(Block block, boolean hasItem) {
        this.block = block;
        this.item = null;
        this.hasItem = hasItem;
        this.renderType = ModRenderType.SOLID;
    }

    ModBlocks(Block block, BlockItem item) {
        this.block = block;
        this.item = item;
        this.hasItem = true;
        this.renderType = ModRenderType.SOLID;
    }

    ModBlocks(Block block, BlockItem item, ModRenderType renderType) {
        this.block = block;
        this.item = item;
        this.hasItem = true;
        this.renderType = renderType;
    }

    ModBlocks(Block block, ModRenderType renderType) {
        this.block = block;
        this.item = null;
        this.hasItem = true;
        this.renderType = renderType;
    }

    public String getName() {
        return String.valueOf(this).toLowerCase();
    }

    public Block getBlock() {
        if (block.getRegistryName() == null) {
            block.setRegistryName(Main.MOD_ID, getName());
        }
        return block;
    }

    public BlockState getState() {
        return getBlock().getDefaultState();
    }

    public Item getItem() {
        if (hasItem) {
            if (hasSpecialItem()) {
                if (item.getRegistryName() == null) {
                    item.setRegistryName(Main.MOD_ID, getName());
                    return item;
                }
            } else {
                return getBlock().asItem();
            }
        }
        return null;
    }

    public boolean hasItem() {
        return hasItem;
    }

    public boolean hasSpecialItem() {
        return item != null;
    }

    public boolean needsSpecialRender() {
        return renderType.getRenderType() != RenderType.solid();
    }

    public RenderType getRenderType() {
        return renderType.getRenderType();
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
