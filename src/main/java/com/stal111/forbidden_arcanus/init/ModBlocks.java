package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.ModRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import java.util.Locale;

public enum ModBlocks {
    ;

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
