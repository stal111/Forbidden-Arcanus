package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Block Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBlockTagsProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.Blocks.FUNGYSS_STEMS).add(NewerModBlocks.FUNGYSS_STEM.get(), NewerModBlocks.FUNGYSS_HYPHAE.get());
        getOrCreateBuilder(BlockTags.LOGS).addTag(ModTags.Blocks.FUNGYSS_STEMS);
        getOrCreateBuilder(BlockTags.PLANKS).add(NewerModBlocks.FUNGYSS_PLANKS.get());
        getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(NewerModBlocks.FUNGYSS_SLAB.get());
        getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(NewerModBlocks.FUNGYSS_STAIRS.get());
        getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(NewerModBlocks.FUNGYSS_PRESSURE_PLATE.get());
        getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(NewerModBlocks.FUNGYSS_BUTTON.get());
        getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(NewerModBlocks.FUNGYSS_FENCE.get());
        getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(NewerModBlocks.FUNGYSS_FENCE.get());
        getOrCreateBuilder(BlockTags.FENCE_GATES).add(NewerModBlocks.FUNGYSS_FENCE_GATE.get());
        getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(NewerModBlocks.FUNGYSS_FENCE_GATE.get());
        getOrCreateBuilder(BlockTags.UNSTABLE_BOTTOM_CENTER).add(NewerModBlocks.FUNGYSS_FENCE_GATE.get());

    }
}
