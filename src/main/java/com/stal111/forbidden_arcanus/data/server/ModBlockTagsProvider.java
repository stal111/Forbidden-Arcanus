package com.stal111.forbidden_arcanus.data.server;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Block Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.ModBlockTagsProvider
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
    }
}
