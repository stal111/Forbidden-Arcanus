package com.stal111.forbidden_arcanus.data.client;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.valhelsia.valhelsia_core.data.ValhelsiaBlockStateProvider;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

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
    protected void register(Set<RegistryObject<Block>> set) {
        set.removeAll(Arrays.asList(NewerModBlocks.OBSIDIAN_SKULL, NewerModBlocks.OBSIDIAN_WALL_SKULL, NewerModBlocks.ETERNAL_OBSIDIAN_SKULL, NewerModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL));

        take(set, this::withExistingModel, NewerModBlocks.UTREM_JAR);
        take(set, block -> pixieUtremJarBlock(block, false), NewerModBlocks.PIXIE_UTREM_JAR);
        take(set, block -> pixieUtremJarBlock(block, true), NewerModBlocks.CORRUPTED_PIXIE_UTREM_JAR);

        forEach(set, this::simpleBlock);
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
}
