package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Biome Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBiomeTagsProvider
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-02-27
 */
public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.Biomes.IS_PLAINS).add(Biomes.PLAINS, Biomes.SNOWY_PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW);
        this.tag(ModTags.Biomes.IS_DESERT).add(Biomes.DESERT);

        this.tag(ModTags.Biomes.HAS_NIPA).addTag(ModTags.Biomes.IS_PLAINS).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
        this.tag(ModTags.Biomes.HAS_NIPA_ALWAYS_FLOATING).addTag(ModTags.Biomes.HAS_NIPA).addTag(BiomeTags.IS_OCEAN).addTag(ModTags.Biomes.IS_DESERT);

        this.tag(ModTags.Biomes.SPAWNS_CORRUPT_LOST_SOUL).add(Biomes.SOUL_SAND_VALLEY);
    }
}
