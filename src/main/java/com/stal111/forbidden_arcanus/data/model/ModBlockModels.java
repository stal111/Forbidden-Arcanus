package com.stal111.forbidden_arcanus.data.model;

import com.google.gson.JsonElement;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 09.09.2023
 */
public class ModBlockModels {

    private final BlockModelGenerators generators;
    private final Consumer<BlockStateGenerator> blockStateOutput;
    private final BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput;

    private final Consumer<Item> skippedAutoItemModels;

    private ModBlockModels(BlockModelGenerators generators, Consumer<Item> skippedAutoItemModels) {
        this.generators = generators;
        this.blockStateOutput = generators.blockStateOutput;
        this.modelOutput = generators.modelOutput;
        this.skippedAutoItemModels = skippedAutoItemModels;

    }

    public static void create(BlockModelGenerators generators, Consumer<Item> skippedAutoItemModels) {
        new ModBlockModels(generators, skippedAutoItemModels).createModels();
    }

    public void createModels() {
        this.blockEntityModels(ModLocationUtils.getBlock("obsidian_skull"), Blocks.SOUL_SAND).createWithCustomBlockItemModel(ModelTemplates.SKULL_INVENTORY, ModBlocks.OBSIDIAN_SKULL.getSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull()).createWithoutBlockItem(ModBlocks.OBSIDIAN_SKULL.getWallSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull());
    }

    private BlockModelGenerators.BlockEntityModelGenerator blockEntityModels(ResourceLocation modelLocation, Block block) {
        return this.generators.new BlockEntityModelGenerator(modelLocation, block);
    }
}
