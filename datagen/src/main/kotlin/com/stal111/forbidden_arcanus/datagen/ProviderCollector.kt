package com.stal111.forbidden_arcanus.datagen

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import net.valhelsia.dataforge.DataCollector
import net.valhelsia.dataforge.DataProviderContext
import net.valhelsia.dataforge.DataTarget

class ProviderCollector : DataCollector() {
    override fun collectProviders(context: DataProviderContext) {
        val blocks = ForbiddenArcanus.REGISTRY_MANAGER.blockHelper.registryEntries.map { { it.value() } }

        with(DataTarget.CLIENT) {
            addProvider(this, ModSoundsProvider(context))
//            DataCollector.addProvider(this, DataForgeModelProvider(context, blocks, { ModBlockModels(it) }, null))
        }
//
//        with(DataTarget.SERVER) {
//            DataCollector.addProvider(this, ModBlockTagsProvider(context))
//            DataCollector.addProvider(this, ModItemTagsProvider(context))
//            DataCollector.addProvider(this, ModBiomeTagsProvider(context))
//            DataCollector.addProvider(this, ModStructureTagsProvider(context))
//            DataCollector.addProvider(this, DataForgeRecipeRunner(context, ::ModRecipeProvider))
//            DataCollector.addProvider(
//                this, LootTableProvider(
//                    context.packOutput, setOf<ResourceKey<LootTable>>(), listOf(
//                        LootTableProvider.SubProviderEntry({ ModBlockLoot(it, blocks) }, LootContextParamSets.BLOCK)
//                    ),
//                    context.lookupProvider
//                )
//            )
//        }
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
