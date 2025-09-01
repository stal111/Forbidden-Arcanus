package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.data.particle.ParticleDataProvider;
import com.stal111.forbidden_arcanus.data.server.loot.ModLootModifierProvider;
import com.stal111.forbidden_arcanus.data.server.tags.ModBlockTagsProvider;
import com.stal111.forbidden_arcanus.data.server.tags.ModEnchantmentTagsProvider;
import com.stal111.forbidden_arcanus.data.server.tags.ModEntityTypeTagsProvider;
import com.stal111.forbidden_arcanus.data.server.tags.ModItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.valhelsia.valhelsia_core.datagen.DataProviderContext;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @author stal111
 * @since 2021-01-26
 */
@EventBusSubscriber
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        DataProviderContext context = new DataProviderContext(event.getGenerator().getPackOutput(), event.getLookupProvider(), ForbiddenArcanus.REGISTRY_MANAGER, event.getExistingFileHelper());

        // Client Providers
        //TODO
        //generator.addProvider(event.includeClient(), new ModBlockStateProvider(context));
        //generator.addProvider(event.includeClient(), new ModItemModelProvider(context));

//        generator.addProvider(event.includeClient(), new ValhelsiaModelProvider(context, ModBlockModels::new, ModItemModels::new));
        generator.addProvider(event.includeClient(), new LangProvider(context.output()));
        generator.addProvider(event.includeServer(), new ParticleDataProvider(context));

        // Server Providers
        var datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER.buildRegistrySet(), Set.of(ForbiddenArcanus.MOD_ID));
//        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();
        context = new DataProviderContext(event.getGenerator().getPackOutput(), lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER, event.getExistingFileHelper());

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(context);
        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), new ModItemTagsProvider(context, blockTagsProvider.contentsGetter()));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagsProvider(context, fileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagsProvider(context, fileHelper));

//        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(), List.of(
//                new LootTableProvider.SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
//                new LootTableProvider.SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
//                new LootTableProvider.SubProviderEntry(ModChestLootAdditions::new, LootContextParamSets.CHEST),
//                new LootTableProvider.SubProviderEntry(ModBlockLootAdditions::new, LootContextParamSets.BLOCK),
//                new LootTableProvider.SubProviderEntry(ModEntityLootAdditions::new, LootContextParamSets.ENTITY)
//        ), context.lookupProvider()));

//        generator.addProvider(event.includeServer(), new ValhelsiaRecipeProvider(context, CraftingRecipeProvider::new, ClibanoRecipeProvider::new, ApplyModifierRecipeProvider::new, SpecialRecipesProvider::new, StonecutterRecipeProvider::new));

        generator.addProvider(event.includeServer(), new ModLootModifierProvider(context));

        DataGenerator.PackGenerator featurePack = generator.getBuiltinDatapack(true, ForbiddenArcanus.MOD_ID, "preview");

        featurePack.addProvider(output1 -> PackMetadataGenerator.forFeaturePack(
                output1,
                Component.literal("Enable experimental features for Forbidden Arcanus"),
                FeatureFlagSet.of(ForbiddenArcanus.PREVIEW)
        ));
    }
}
