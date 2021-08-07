package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Block Tags Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBlockTagsProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-11
 */
public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        this.getOrCreateBuilder(ModTags.Blocks.FUNGYSS_STEMS).add(NewModBlocks.FUNGYSS_STEM.get(), NewModBlocks.FUNGYSS_HYPHAE.get());
        this.getOrCreateBuilder(BlockTags.LOGS).addTags(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Blocks.CHERRYWOOD_LOGS, ModTags.Blocks.MYSTERYWOOD_LOGS).add(ModBlocks.EDELWOOD_LOG.getBlock());
        this.getOrCreateBuilder(BlockTags.PLANKS).add(NewModBlocks.FUNGYSS_PLANKS.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(NewModBlocks.FUNGYSS_SLAB.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(NewModBlocks.FUNGYSS_STAIRS.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(NewModBlocks.FUNGYSS_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(NewModBlocks.FUNGYSS_BUTTON.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(NewModBlocks.FUNGYSS_FENCE.get());
        this.getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE.get());
        this.getOrCreateBuilder(BlockTags.FENCE_GATES).add(NewModBlocks.FUNGYSS_FENCE_GATE.get());
        this.getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(NewModBlocks.FUNGYSS_FENCE_GATE.get());
        this.getOrCreateBuilder(BlockTags.UNSTABLE_BOTTOM_CENTER).add(NewModBlocks.FUNGYSS_FENCE_GATE.get());
        this.getOrCreateBuilder(BlockTags.CLIMBABLE).add(ModBlocks.EDELWOOD_LADDER.getBlock());
        this.getOrCreateBuilder(BlockTags.FLOWER_POTS).add(NewModBlocks.POTTED_CHERRYWOOD_SAPLING.get(), NewModBlocks.POTTED_MYSTERYWOOD_SAPLING.get(), NewModBlocks.POTTED_YELLOW_ORCHID.get());
    }
}