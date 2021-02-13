package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Item Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.ModItemTagsProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.Items.OBSIDIAN_SKULLS).add(NewModItems.OBSIDIAN_SKULL.get(), NewModItems.ETERNAL_OBSIDIAN_SKULL.get());
        getOrCreateBuilder(Tags.Items.HEADS).addTag(ModTags.Items.OBSIDIAN_SKULLS);
    }
}
