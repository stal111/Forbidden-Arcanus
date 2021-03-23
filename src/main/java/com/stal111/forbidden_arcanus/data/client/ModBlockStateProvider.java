package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.data.ValhelsiaBlockStateProvider;

import java.util.Arrays;
import java.util.Objects;

/**
 * Mod Block State Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-18
 */
public class ModBlockStateProvider extends ValhelsiaBlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, ForbiddenArcanus.REGISTRY_MANAGER, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getRemainingBlocks().removeAll(Arrays.asList(NewerModBlocks.OBSIDIAN_SKULL, NewerModBlocks.OBSIDIAN_WALL_SKULL, NewerModBlocks.ETERNAL_OBSIDIAN_SKULL, NewerModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL));

        take(this::withExistingModel, NewerModBlocks.UTREM_JAR, NewerModBlocks.NIPA);
        take(block -> pixieUtremJarBlock(block, false), NewerModBlocks.PIXIE_UTREM_JAR);
        take(block -> pixieUtremJarBlock(block, true), NewerModBlocks.CORRUPTED_PIXIE_UTREM_JAR);
        take(block -> simpleBlock(block, models().cross(Objects.requireNonNull(block.getRegistryName()).getPath(), modLoc("block/fungyss"))), NewerModBlocks.FUNGYSS);
        take(this::mushroomBlock, NewerModBlocks.FUNGYSS_BLOCK);
        take(block -> logBlock((RotatedPillarBlock) block), NewerModBlocks.FUNGYSS_STEM);
        take(block -> axisBlock((RotatedPillarBlock) block, modLoc("block/fungyss_stem"), modLoc("block/fungyss_stem")), NewerModBlocks.FUNGYSS_HYPHAE);
        ResourceLocation fungyssPlanks = modLoc("block/fungyss_planks");
        take(block -> slabBlock((SlabBlock) block, fungyssPlanks, fungyssPlanks), NewerModBlocks.FUNGYSS_SLAB);
        take(block -> stairsBlock((StairsBlock) block, fungyssPlanks), NewerModBlocks.FUNGYSS_STAIRS);
        take(block -> pressurePlateBlock(block, fungyssPlanks), NewerModBlocks.FUNGYSS_PRESSURE_PLATE);
        take(block -> buttonBlock((AbstractButtonBlock) block, fungyssPlanks), NewerModBlocks.FUNGYSS_BUTTON);
        take(block -> fenceBlock((FenceBlock) block, fungyssPlanks), NewerModBlocks.FUNGYSS_FENCE);
        take(block -> fenceGateBlock((FenceGateBlock) block, fungyssPlanks), NewerModBlocks.FUNGYSS_FENCE_GATE);

        forEach(this::simpleBlock);
    }

    private void pixieUtremJarBlock(Block block, boolean corrupted) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();

        ModelFile model = models().withExistingParent( name, modLoc("block/pixie_utrem_jar_template"))
                .texture("bottle_block_top", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_top"))
                .texture("bottle_block_bottom", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_bottom"))
                .texture("bottle_block_side", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_side"))
                .texture("pixie", modLoc("block/" + (corrupted ? "corrupted_" : "") + "utrem_jar_pixie"));

        simpleBlock(block, model);
    }

    private void mushroomBlock(Block block) {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();

        ModelFile model = models().withExistingParent(name, mcLoc("block/template_single_face")).texture("texture", modLoc("block/" + name));
        ModelFile modelInside = models().withExistingParent(name + "_inside", mcLoc("block/template_single_face")).texture("texture", modLoc("block/" + name + "_inside")).ao(false);

        getMultipartBuilder(block)
                .part().modelFile(model).addModel().condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(model).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(model).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(model).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, true).end()
                .part().modelFile(model).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, true).end()
                .part().modelFile(model).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, true).end()

                .part().modelFile(modelInside).addModel().condition(BlockStateProperties.NORTH, false).end()
                .part().modelFile(modelInside).rotationY(90).uvLock(true).addModel().condition(BlockStateProperties.EAST, false).end()
                .part().modelFile(modelInside).rotationY(180).uvLock(true).addModel().condition(BlockStateProperties.SOUTH, false).end()
                .part().modelFile(modelInside).rotationY(270).uvLock(true).addModel().condition(BlockStateProperties.WEST, false).end()
                .part().modelFile(modelInside).rotationX(270).uvLock(true).addModel().condition(BlockStateProperties.UP, false).end()
                .part().modelFile(modelInside).rotationX(90).uvLock(true).addModel().condition(BlockStateProperties.DOWN, false).end();

        this.models().singleTexture(name + "_inventory", mcLoc("block/cube_all"), "all", modLoc("block/" + name));
    }
}
