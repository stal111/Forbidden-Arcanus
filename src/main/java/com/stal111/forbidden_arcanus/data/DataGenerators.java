package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.client.ModItemModelProvider;
import com.stal111.forbidden_arcanus.data.server.ModBlockTagsProvider;
import com.stal111.forbidden_arcanus.data.server.ModItemTagsProvider;
import com.stal111.forbidden_arcanus.data.server.ModLootTableProvider;
import com.stal111.forbidden_arcanus.data.server.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

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

        generator.addProvider(new ModItemModelProvider(generator, existingFileHelper));

        generator.addProvider(new ModLootTableProvider(generator));
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, existingFileHelper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, existingFileHelper));
        generator.addProvider(new ModRecipeProvider(generator));
    }
}
