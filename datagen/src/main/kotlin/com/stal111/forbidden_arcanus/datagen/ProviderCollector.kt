package com.stal111.forbidden_arcanus.datagen

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.datagen.model.ModBlockModels
import com.stal111.forbidden_arcanus.datagen.model.ModItemModels
import com.stal111.forbidden_arcanus.datagen.recipes.ApplyModifierRecipeProvider
import com.stal111.forbidden_arcanus.datagen.recipes.ClibanoRecipeProvider
import com.stal111.forbidden_arcanus.datagen.recipes.CraftingRecipeProvider
import com.stal111.forbidden_arcanus.datagen.recipes.SpecialRecipesProvider
import com.stal111.forbidden_arcanus.datagen.recipes.StonecutterRecipeProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModBlockTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModEnchantmentTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModEntityTypeTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModItemTagsProvider
import net.valhelsia.dataforge.DataCollector
import net.valhelsia.dataforge.DataProviderContext
import net.valhelsia.dataforge.DataTarget
import net.valhelsia.dataforge.model.DataForgeModelProvider
import net.valhelsia.dataforge.recipe.DataForgeRecipeRunner

class ProviderCollector : DataCollector() {
    override fun collectProviders(context: DataProviderContext) {
        val blocks = ForbiddenArcanus.REGISTRY_MANAGER.blockHelper.registryEntries.map { { it.value() } }

        with(DataTarget.CLIENT) {
            addProvider(this, ModSoundsProvider(context))
            addProvider(this, DataForgeModelProvider(context, blocks, { ModBlockModels(it) }, { ModItemModels(it) }))
        }
//
        with(DataTarget.SERVER) {
            addProvider(this, ModBlockTagsProvider(context))
            addProvider(this, ModItemTagsProvider(context))
            addProvider(this, ModEnchantmentTagsProvider(context, context.fileHelper))
            addProvider(this, ModEntityTypeTagsProvider(context, context.fileHelper))
            addProvider(
                this,
                DataForgeRecipeRunner(
                    context,
                    ::CraftingRecipeProvider,
                    ::StonecutterRecipeProvider,
                    ::ApplyModifierRecipeProvider,
                    ::SpecialRecipesProvider,
                    ::ClibanoRecipeProvider
                )
            )
//            DataCollector.addProvider(
//                this, LootTableProvider(
//                    context.packOutput, setOf<ResourceKey<LootTable>>(), listOf(
//                        LootTableProvider.SubProviderEntry({ ModBlockLoot(it, blocks) }, LootContextParamSets.BLOCK)
//                    ),
//                    context.lookupProvider
//                )
//            )
        }
    }

    override fun collectRegistryProviders() {
//        DataCollector.addRegistryProvider(Registries.PROCESSOR_LIST, ModProcessorLists)
//        DataCollector.addRegistryProvider(Registries.STRUCTURE, ModStructures)
//        DataCollector.addRegistryProvider(Registries.STRUCTURE_SET, ModStructureSets)
//        DataCollector.addRegistryProvider(
//            Registries.TEMPLATE_POOL,
//            BigTreePools, DesertHousePools, MobPools, PlayerHousePools, SimpleStructurePools, SpawnerDungeonPools
//        )
    }
}
