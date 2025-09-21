package com.stal111.forbidden_arcanus.data;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
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

        DataProviderContext context = new DataProviderContext(event.getGenerator().getPackOutput(), event.getLookupProvider(), ForbiddenArcanus.REGISTRY_MANAGER, event.getExistingFileHelper());

        // Server Providers
        var datapackBuiltinEntriesProvider = new DatapackBuiltinEntriesProvider(output, lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER.buildRegistrySet(), Set.of(ForbiddenArcanus.MOD_ID));
//        generator.addProvider(event.includeServer(), datapackBuiltinEntriesProvider);

        lookupProvider = datapackBuiltinEntriesProvider.getRegistryProvider();
        context = new DataProviderContext(event.getGenerator().getPackOutput(), lookupProvider, ForbiddenArcanus.REGISTRY_MANAGER, event.getExistingFileHelper());

//        generator.addProvider(event.includeServer(), new ValhelsiaRecipeProvider(context, CraftingRecipeProvider::new, ClibanoRecipeProvider::new, ApplyModifierRecipeProvider::new, SpecialRecipesProvider::new, StonecutterRecipeProvider::new));

        DataGenerator.PackGenerator featurePack = generator.getBuiltinDatapack(true, ForbiddenArcanus.MOD_ID, "preview");

        featurePack.addProvider(output1 -> PackMetadataGenerator.forFeaturePack(
                output1,
                Component.literal("Enable experimental features for Forbidden Arcanus"),
                FeatureFlagSet.of(ForbiddenArcanus.PREVIEW)
        ));
    }
}
