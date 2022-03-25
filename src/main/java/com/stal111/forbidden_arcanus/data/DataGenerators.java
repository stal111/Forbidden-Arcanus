package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider;
import com.stal111.forbidden_arcanus.data.client.ModItemModelProvider;
import com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider;
import com.stal111.forbidden_arcanus.data.server.tags.*;
import com.stal111.forbidden_arcanus.data.server.loot.ModLootTableProvider;
import com.stal111.forbidden_arcanus.data.server.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * Data Generators
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.data.DataGenerators
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(modid = ForbiddenArcanus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new ModBlockStateProvider(generator, existingFileHelper));
            generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));
        }

        if (event.includeServer()) {
            ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
            generator.addProvider(blockTagsProvider);
            generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper));
            generator.addProvider(new ModEnchantmentTagsProvider(generator, existingFileHelper));
            generator.addProvider(new ModEntityTypeTagsProvider(generator, existingFileHelper));
            generator.addProvider(new ModBiomeTagsProvider(generator, existingFileHelper));

            generator.addProvider(new ModLootTableProvider(generator));
            generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new ModLootModifierProvider(generator));
        }
    }
}
