package com.stal111.forbidden_arcanus.data.server.tags;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

/**
 * Mod Item Tags Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.server.tags.ModItemTagsProvider
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-02-11
 */
public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, ForbiddenArcanus.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        this.copy(ModTags.Blocks.FUNGYSS_STEMS, ModTags.Items.FUNGYSS_STEMS);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(Tags.Blocks.FENCES_WOODEN, Tags.Items.FENCES_WOODEN);
        this.copy(Tags.Blocks.FENCE_GATES_WOODEN, Tags.Items.FENCE_GATES_WOODEN);

        this.getOrCreateBuilder(ModTags.Items.OBSIDIAN_SKULLS).add(NewModItems.OBSIDIAN_SKULL.get(), NewModItems.ETERNAL_OBSIDIAN_SKULL.get());
        this.getOrCreateBuilder(Tags.Items.HEADS).addTag(ModTags.Items.OBSIDIAN_SKULLS);
        this.getOrCreateBuilder(ModTags.Items.INDESTRUCTIBLE_BLACKLISTED);
        this.getOrCreateBuilder(ModTags.Items.BLACKSMITH_GAVEL).add(
                NewModItems.WOODEN_BLACKSMITH_GAVEL.get(),
                NewModItems.STONE_BLACKSMITH_GAVEL.get(),
                NewModItems.GOLDEN_BLACKSMITH_GAVEL.get(),
                NewModItems.IRON_BLACKSMITH_GAVEL.get(),
                NewModItems.DIAMOND_BLACKSMITH_GAVEL.get(),
                NewModItems.NETHERITE_BLACKSMITH_GAVEL.get(),
                NewModItems.ARCANE_GOLDEN_BLACKSMITH_GAVEL.get(),
                NewModItems.REINFORCED_ARCANE_GOLDEN_BLACKSMITH_GAVEL.get(),
                NewModItems.OBSIDIAN_BLACKSMITH_GAVEL.get()
        );
        this.getOrCreateBuilder(ModTags.Items.ARCANE_GOLD_INGOTS).add(ModItems.ARCANE_GOLD_INGOT.get());
        this.getOrCreateBuilder(ModTags.Items.OBSIDIAN_INGOTS).add(ModItems.OBSIDIAN_INGOT.get());
    }
}
