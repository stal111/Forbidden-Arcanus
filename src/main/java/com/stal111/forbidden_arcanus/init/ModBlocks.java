package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.*;
import com.stal111.forbidden_arcanus.block.trees.CherrywoodTree;
import com.stal111.forbidden_arcanus.block.trees.MysterywoodTree;
import com.stal111.forbidden_arcanus.block.NoFluidOverlayBlock;
import com.stal111.forbidden_arcanus.util.ModRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Locale;

public enum ModBlocks {
    RUNIC_TENEBRIS_FRAME(new RunicTenebrisFrameBlock(addProperties(Material.STONE, 2.0F, 15.0F).noOcclusion())),
    RUNIC_TENEBRIS_CORE(new RunicTenebrisCoreBlock(addProperties(Material.STONE, 2.0F, 15.0F).noOcclusion().lightLevel(state -> 12)), ModRenderType.CUTOUT),
    ARCANE_DARK_STONE(new Block(addProperties(Material.STONE, 1.5F, 6.0F))),
    RUNIC_GLASS(new GlassBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    RUNIC_GLASS_PANE(new IronBarsBlock(from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT),
    DARK_RUNIC_GLASS(new GlassBlock(from(Blocks.GLASS)), ModRenderType.CUTOUT),
    DARK_RUNIC_GLASS_PANE(new IronBarsBlock(from(Blocks.GLASS_PANE)), ModRenderType.CUTOUT),
    DARK_NETHER_STAR_BLOCk(new Block(from(Blocks.DIAMOND_BLOCK))),
    END_CRYSTAL_GEM(new NoFluidOverlayBlock(addProperties(Material.GLASS, 1.0F, 5.0F).noOcclusion()), ModRenderType.TRANSLUCENT),
    HEPHAESTUS_FORGE(new HephaestusForgeBlock(from(Blocks.IRON_BLOCK).noOcclusion()), ModRenderType.CUTOUT),
    STELLA_ARCANUM(new StellaArcanumBlock(from(Blocks.OBSIDIAN).strength(38.0F, 1200.0F))),
    STELLARITE_BLOCK(new Block(from(Blocks.OBSIDIAN))),
    XPETRIFIED_ORE(new OreBlock(addProperties(Material.STONE, 3.0F, 3.0F)), ModRenderType.CUTOUT),
    ARCANE_GOLD_BLOCK(new Block(from(Blocks.GOLD_BLOCK))),
    ARCANE_GOLD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.GOLD_BLOCK))),
    ARCANE_GOLD_DOOR(new DoorBlock(from(Blocks.GOLD_BLOCK).noOcclusion()), ModRenderType.CUTOUT),
    ARCANE_DRAGON_EGG(new ArcaneDragonEggBlock(from(Blocks.DRAGON_EGG).lightLevel(state -> 9))),
    EDELWOOD_LOG(new EdelwoodLogBlock(MaterialColor.COLOR_BROWN, from(Blocks.OAK_LOG).randomTicks()), ModRenderType.CUTOUT),
    EDELWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    ARCANE_EDELWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    EDELWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    EDELWOOD_STAIRS(new StairBlock(() -> EDELWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    EDELWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    EDELWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    EDELWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    EDELWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    EDELWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    EDELWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR))),
    EDELWOOD_LADDER(new EdelwoodLadderBlock(from(Blocks.LADDER)), ModRenderType.CUTOUT),
    EDELWOOD_WALL_SIGN(ForbiddenArcanus.EDELWOOD_WALL_SIGN, false),
    EDELWOOD_SIGN(ForbiddenArcanus.EDELWOOD_SIGN, new SignItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS), ForbiddenArcanus.EDELWOOD_SIGN, EDELWOOD_WALL_SIGN.getBlock())),
    CHERRYWOOD_LOG(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    CHERRYWOOD(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD_LOG(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    CHERRYWOOD_LEAVES(new LeavesBlock(from(Blocks.OAK_LEAVES))),
    CHERRYWOOD_SAPLING(new SaplingBlock(new CherrywoodTree(), from(Blocks.OAK_SAPLING)), ModRenderType.CUTOUT),
    CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CARVED_CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CHERRYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    CHERRYWOOD_STAIRS(new StairBlock(() -> CHERRYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    CHERRYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    CHERRYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    CHERRYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    CHERRYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    CHERRYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    CHERRYWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR)), ModRenderType.CUTOUT),
    CHERRYWOOD_WALL_SIGN(ForbiddenArcanus.CHERRYWOOD_WALL_SIGN, false),
    CHERRYWOOD_SIGN(ForbiddenArcanus.CHERRYWOOD_SIGN, new SignItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS), ForbiddenArcanus.CHERRYWOOD_SIGN, CHERRYWOOD_WALL_SIGN.getBlock())),
    MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    STRIPPED_MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    STRIPPED_MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    MYSTERYWOOD_LEAVES(new LeavesBlock(from(Blocks.OAK_LEAVES))),
    MYSTERYWOOD_SAPLING(new SaplingBlock(new MysterywoodTree(), from(Blocks.OAK_SAPLING)), ModRenderType.CUTOUT),
    MYSTERYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    MYSTERYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    MYSTERYWOOD_STAIRS(new StairBlock(() -> MYSTERYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    MYSTERYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    MYSTERYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    MYSTERYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    MYSTERYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    MYSTERYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    MYSTERYWOOD_DOOR(new DoorBlock(from(Blocks.OAK_DOOR)), ModRenderType.CUTOUT),
    MYSTERYWOOD_WALL_SIGN(ForbiddenArcanus.MYSTERYWOOD_WALL_SIGN, false),
    MYSTERYWOOD_SIGN(ForbiddenArcanus.MYSTERYWOOD_SIGN, new SignItem(new Item.Properties().tab(ForbiddenArcanus.FORBIDDEN_ARCANUS), ForbiddenArcanus.MYSTERYWOOD_SIGN, MYSTERYWOOD_WALL_SIGN.getBlock())),
    YELLOW_ORCHID(new FlowerBlock(MobEffects.GLOWING, 25, from(Blocks.BLUE_ORCHID)), ModRenderType.CUTOUT),
    GOLDEN_ORCHID(new GoldenOrchidBlock(from(Blocks.BLUE_ORCHID).randomTicks()), false, ModRenderType.CUTOUT),
    STRANGE_ROOT(new StrangeRootBlock(from(Blocks.WHEAT)), false, ModRenderType.CUTOUT),
    MAGICAL_FARMLAND(new ModFarmlandBlock(from(Blocks.FARMLAND))),
    SOULLESS_SAND(new SoullessSandBlock(from(Blocks.SOUL_SAND))),
    SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    CUT_SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    POLISHED_SOULLESS_SANDSTONE(new Block(from(Blocks.SANDSTONE))),
    SOULLESS_SANDSTONE_SLAB(new SlabBlock(from(Blocks.SANDSTONE_SLAB))),
    CUT_SOULLESS_SANDSTONE_SLAB(new SlabBlock(from(Blocks.SANDSTONE_SLAB))),
    POLISHED_SOULLESS_SANDSTONE_SLAB(new SlabBlock(from(Blocks.SANDSTONE_SLAB))),
    SOULLESS_SANDSTONE_STAIRS(new StairBlock(() -> SOULLESS_SANDSTONE.getState(), from(Blocks.SANDSTONE_STAIRS))),
    POLISHED_SOULLESS_SANDSTONE_STAIRS(new StairBlock(() ->POLISHED_SOULLESS_SANDSTONE.getState(), from(Blocks.SANDSTONE_STAIRS))),
    SOULLESS_SANDSTONE_WALL(new WallBlock(from(Blocks.SANDSTONE_WALL)));

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
        return String.valueOf(this).toLowerCase(Locale.ROOT);
    }

    public Block getBlock() {
        if (block.getRegistryName() == null) {
            block.setRegistryName(ForbiddenArcanus.MOD_ID, getName());
        }
        return block;
    }

    public BlockState getState() {
        return getBlock().defaultBlockState();
    }

    public Item getItem() {
        if (hasItem) {
            if (hasSpecialItem()) {
                if (item.getRegistryName() == null) {
                    item.setRegistryName(ForbiddenArcanus.MOD_ID, getName());
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
        Block.Properties properties = Block.Properties.of(material);
        return properties;

    }

    public static Block.Properties addProperties(Material material, SoundType soundType) {
        Block.Properties properties = Block.Properties.of(material).sound(soundType);
        return properties;

    }

    public static Block.Properties addProperties(Material material, float hardness, float resistance) {
        Block.Properties properties = Block.Properties.of(material).strength(hardness, resistance);
        return properties;

    }

    public static Block.Properties addProperties(Material material, float hardness, float resistance, SoundType soundType) {
        Block.Properties properties = Block.Properties.of(material).strength(hardness, resistance).sound(soundType);
        return properties;

    }

    private static Block.Properties from(Block block) {
        return Block.Properties.copy(block);
    }
}
