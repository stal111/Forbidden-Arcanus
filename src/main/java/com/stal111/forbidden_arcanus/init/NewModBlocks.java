package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ArcanePolishedDarkstoneRod;
import com.stal111.forbidden_arcanus.block.BlackHoleBlock;
import com.stal111.forbidden_arcanus.block.PillarBlock;
import com.stal111.forbidden_arcanus.block.RunicChiseledPolishedDarkstone;
import com.stal111.forbidden_arcanus.util.ModRenderType;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class NewModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);

    public static final Map<Block, ModRenderType> BLOCK_RENDER_TYPE_MAP = new HashMap<>();

    public static final RegistryObject<Block> DARKSTONE = register("darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_SLAB = register("darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_STAIRS = register("darkstone_stairs", new StairsBlock(() -> DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> DARKSTONE_WALL = register("darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_GILDED_DARKSTONE = register("arcane_gilded_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F).sound(SoundType.field_235599_U_)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE = register("polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_SLAB = register("polished_darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_STAIRS = register("polished_darkstone_stairs", new StairsBlock(() -> POLISHED_DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_WALL = register("polished_darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_PRESSURE_PLATE = register("polished_darkstone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, Block.Properties.from(Blocks.STONE_PRESSURE_PLATE).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BUTTON = register("polished_darkstone_button", new StoneButtonBlock(Block.Properties.from(Blocks.STONE_BUTTON).hardnessAndResistance(0.5F)));
    public static final RegistryObject<Block> CHISELED_POLISHED_DARKSTONE = register("chiseled_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> RUNIC_CHISELED_POLISHED_DARKSTONE = register("runic_chiseled_polished_darkstone", new RunicChiseledPolishedDarkstone(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)), ModRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_CHISELED_POLISHED_DARKSTONE = register("arcane_chiseled_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICKS = register("polished_darkstone_bricks", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_SLAB = register("polished_darkstone_brick_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_STAIRS = register("polished_darkstone_brick_stairs", new StairsBlock(() -> POLISHED_DARKSTONE_BRICKS.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> POLISHED_DARKSTONE_BRICK_WALL = register("polished_darkstone_brick_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> CRACKED_POLISHED_DARKSTONE_BRICKS = register("cracked_polished_darkstone_bricks", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE = register("arcane_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_SLAB = register("arcane_polished_darkstone_slab", new SlabBlock(Block.Properties.from(Blocks.STONE_SLAB).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_STAIRS = register("arcane_polished_darkstone_stairs", new StairsBlock(() -> ARCANE_POLISHED_DARKSTONE.get().getDefaultState(), Block.Properties.from(Blocks.STONE_STAIRS).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_WALL = register("arcane_polished_darkstone_wall", new WallBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> CHISELED_ARCANE_POLISHED_DARKSTONE = register("chiseled_arcane_polished_darkstone", new Block(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_PILLAR = register("arcane_polished_darkstone_pillar", new PillarBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_POLISHED_DARKSTONE_ROD = register("arcane_polished_darkstone_rod", new ArcanePolishedDarkstoneRod(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F)));
    public static final RegistryObject<Block> ARCANE_GLASS = register("arcane_glass", new GlassBlock(Block.Properties.from(Blocks.GLASS)), ModRenderType.CUTOUT);
    public static final RegistryObject<Block> ARCANE_GLASS_PANE = register("arcane_glass_pane", new PaneBlock(Block.Properties.from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT);

    public static final RegistryObject<Block> BLACK_HOLE = register("black_hole", new BlackHoleBlock(Block.Properties.from(Blocks.STONE).hardnessAndResistance(2.0F, 8.0F).notSolid()), ModRenderType.CUTOUT, false);

    private static <T extends Block> RegistryObject<T> register(String name, T block) {
        return register(name, block, ModRenderType.SOLID);
    }

    private static <T extends Block> RegistryObject<T> registerBlockNoItem(String name, T block) {
        return register(name, block, ModRenderType.SOLID, false);
    }

    private static <T extends Block> RegistryObject<T> register(String name, T block, ModRenderType renderType) {
        return register(name, block, renderType, true);
    }

    private static <T extends Block> RegistryObject<T> register(String name, T block, ModRenderType renderType, boolean hasItem) {
        if (hasItem) {
            ModItems.ITEMS.register(name, () -> new BlockItem(block, new Item.Properties().group(Main.FORBIDDEN_ARCANUS)));
        }

        if (renderType != ModRenderType.SOLID) {
            BLOCK_RENDER_TYPE_MAP.put(block, renderType);
        }

        return BLOCKS.register(name, () -> block);
    }
}
