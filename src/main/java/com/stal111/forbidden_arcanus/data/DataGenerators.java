package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.client.ModSoundsProvider;
import com.stal111.forbidden_arcanus.data.model.ModModelProvider;
import com.stal111.forbidden_arcanus.data.particle.ParticleDataProvider;
import com.stal111.forbidden_arcanus.data.recipes.*;
import com.stal111.forbidden_arcanus.data.server.loot.ModBlockLootTables;
import com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider;
import com.stal111.forbidden_arcanus.data.server.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;
import net.valhelsia.valhelsia_core.datagen.recipes.ValhelsiaRecipeProvider;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 2021-01-26
 */
@EventBusSubscriber(modid = ForbiddenArcanus.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        DataProviderContext context = new DataProviderContext(event.getGenerator().getPackOutput(), event.getLookupProvider(), ForbiddenArcanus.REGISTRY_MANAGER);

        // Client Providers
        //TODO
        //generator.addProvider(event.includeClient(), new ModBlockStateProvider(context));
        //generator.addProvider(event.includeClient(), new ModItemModelProvider(context));

        generator.addProvider(event.includeClient(), new ModModelProvider(output));

        generator.addProvider(event.includeClient(), new ModSoundsProvider(context, fileHelper));
        generator.addProvider(event.includeServer(), new ParticleDataProvider(context));

        // Server Providers
        var datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER.buildRegistrySet(), Set.of(ForbiddenArcanus.MOD_ID));
        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();
        context = new DataProviderContext(event.getGenerator().getPackOutput(), lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER);

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(context);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(context, blockTagsProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(context, fileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(context, fileHelper));
        generator.addProvider(event.includeServer(), new ModBiomeTagsProvider(context, fileHelper));
        generator.addProvider(event.includeServer(), new ModDamageTypeTagsProvider(context, fileHelper));

        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK)), context.lookupProvider()));

        generator.addProvider(event.includeServer(), new ValhelsiaRecipeProvider(context, CraftingRecipeProvider::new, ClibanoRecipeProvider::new, ApplyModifierRecipeProvider::new, SpecialRecipesProvider::new, StonecutterRecipeProvider::new));

        generator.addProvider(event.includeServer(), new ModLootModifierProvider(output));
    }
}
