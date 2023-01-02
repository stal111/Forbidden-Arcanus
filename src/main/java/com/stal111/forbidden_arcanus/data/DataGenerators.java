package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.client.ModBlockStateProvider;
import com.stal111.forbidden_arcanus.data.client.ModItemModelProvider;
import com.stal111.forbidden_arcanus.data.client.ModSoundsProvider;
import com.stal111.forbidden_arcanus.data.recipes.ApplyModifierRecipeProvider;
import com.stal111.forbidden_arcanus.data.recipes.ClibanoRecipeProvider;
import com.stal111.forbidden_arcanus.data.recipes.CraftingRecipeProvider;
import com.stal111.forbidden_arcanus.data.server.loot.ModBlockLootTables;
import com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider;
import com.stal111.forbidden_arcanus.data.server.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.core.data.DataProviderInfo;
import net.valhelsia.valhelsia_core.data.recipes.ValhelsiaRecipeProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 2021-01-26
 */
@Mod.EventBusSubscriber(modid = ForbiddenArcanus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        DataProviderInfo info = DataProviderInfo.of(event, ForbiddenArcanus.REGISTRY_MANAGER);

        // Client Providers
        generator.addProvider(event.includeClient(), new ModBlockStateProvider(info));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(info));

        generator.addProvider(event.includeClient(), new ModSoundsProvider(info));

        // Server Providers
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(info);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(info, blockTagsProvider));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(info));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(info));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(info));

        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK))));

        generator.addProvider(event.includeServer(), new ValhelsiaRecipeProvider(info, CraftingRecipeProvider::new, ClibanoRecipeProvider::new, ApplyModifierRecipeProvider::new));

        generator.addProvider(event.includeServer(), new ModLootModifierProvider(output));

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(output, lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER.buildRegistrySet(info), Set.of(ForbiddenArcanus.MOD_ID)));
    }
}
