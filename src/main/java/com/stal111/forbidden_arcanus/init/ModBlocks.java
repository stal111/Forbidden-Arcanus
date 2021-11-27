package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.*;
import com.stal111.forbidden_arcanus.util.ModRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.Locale;

public enum ModBlocks {
    RUNIC_TENEBRIS_FRAME(new RunicTenebrisFrameBlock(addProperties(Material.STONE, 2.0F, 15.0F).noOcclusion())),
    RUNIC_TENEBRIS_CORE(new RunicTenebrisCoreBlock(addProperties(Material.STONE, 2.0F, 15.0F).noOcclusion().lightLevel(state -> 12)), ModRenderType.CUTOUT),
    ARCANE_DARK_STONE(new Block(addProperties(Material.STONE, 1.5F, 6.0F))),
    ARCANE_GOLD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.GOLD_BLOCK))),
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
    EDELWOOD_LADDER(new EdelwoodLadderBlock(from(Blocks.LADDER)), ModRenderType.CUTOUT),
    CHERRYWOOD_LOG(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    CHERRYWOOD(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD_LOG(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    STRIPPED_CHERRYWOOD(new RotatedPillarBlock(from(Blocks.OAK_LOG))),
    CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CARVED_CHERRYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    CHERRYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    CHERRYWOOD_STAIRS(new StairBlock(() -> CHERRYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    CHERRYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    CHERRYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    CHERRYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    CHERRYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    CHERRYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    STRIPPED_MYSTERYWOOD_LOG(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    STRIPPED_MYSTERYWOOD(new MysterywoodLogBlock(from(Blocks.OAK_LOG))),
    MYSTERYWOOD_PLANKS(new Block(from(Blocks.OAK_PLANKS))),
    MYSTERYWOOD_SLAB(new SlabBlock(from(Blocks.OAK_SLAB))),
    MYSTERYWOOD_STAIRS(new StairBlock(() -> MYSTERYWOOD_PLANKS.getState(), from(Blocks.OAK_STAIRS))),
    MYSTERYWOOD_FENCE(new FenceBlock(from(Blocks.OAK_FENCE))),
    MYSTERYWOOD_FENCE_GATE(new FenceGateBlock(from(Blocks.OAK_FENCE_GATE))),
    MYSTERYWOOD_BUTTON(new WoodButtonBlock(from(Blocks.OAK_BUTTON))),
    MYSTERYWOOD_PRESSURE_PLATE(new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, from(Blocks.OAK_PRESSURE_PLATE))),
    MYSTERYWOOD_TRAPDOOR(new TrapDoorBlock(from(Blocks.OAK_TRAPDOOR)), ModRenderType.CUTOUT),
    YELLOW_ORCHID(new FlowerBlock(MobEffects.GLOWING, 25, from(Blocks.BLUE_ORCHID)), ModRenderType.CUTOUT),
    GOLDEN_ORCHID(new GoldenOrchidBlock(from(Blocks.BLUE_ORCHID).randomTicks()), false, ModRenderType.CUTOUT),
    STRANGE_ROOT(new StrangeRootBlock(from(Blocks.WHEAT)), false, ModRenderType.CUTOUT),
    MAGICAL_FARMLAND(new ModFarmlandBlock(from(Blocks.FARMLAND)));

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
