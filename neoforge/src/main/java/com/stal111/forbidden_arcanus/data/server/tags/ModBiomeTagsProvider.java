package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import org.jetbrains.annotations.NotNull;

/**
 * Mod Biome Tags Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModBiomeTagsProvider
 *
 * @author stal111
 * @since 2021-02-27
 */
public class ModBiomeTagsProvider extends BiomeTagsProvider {

    public ModBiomeTagsProvider(DataProviderContext context, ExistingFileHelper fileHelper) {
        super(context.output(), context.lookupProvider(), ForbiddenArcanus.MOD_ID, fileHelper);
    }

    @Override
    protected void addTags(@NotNull HolderLookup.Provider provider) {
        this.tag(ModTags.Biomes.IS_PLAINS).add(Biomes.PLAINS, Biomes.SNOWY_PLAINS, Biomes.SUNFLOWER_PLAINS, Biomes.MEADOW);
        this.tag(ModTags.Biomes.IS_DESERT).add(Biomes.DESERT);

        this.tag(ModTags.Biomes.HAS_NIPA).addTag(ModTags.Biomes.IS_PLAINS).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA);
        this.tag(ModTags.Biomes.HAS_NIPA_ALWAYS_FLOATING).addTag(ModTags.Biomes.HAS_NIPA).addTag(BiomeTags.IS_OCEAN).addTag(ModTags.Biomes.IS_DESERT);

        this.tag(ModTags.Biomes.SPAWNS_CORRUPT_LOST_SOUL).add(Biomes.SOUL_SAND_VALLEY);
    }
}
