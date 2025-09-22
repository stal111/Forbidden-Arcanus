package com.stal111.forbidden_arcanus.datagen

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.datagen.block.clibano.ModResidueTypes
import com.stal111.forbidden_arcanus.datagen.block.forge.ModMagicCircles
import com.stal111.forbidden_arcanus.datagen.block.forge.ModRituals
import com.stal111.forbidden_arcanus.datagen.item.ModEnchantments
import com.stal111.forbidden_arcanus.datagen.item.ModEnhancerDefinitions
import com.stal111.forbidden_arcanus.datagen.item.ModItemModifiers
import com.stal111.forbidden_arcanus.datagen.lang.LangProvider
import com.stal111.forbidden_arcanus.datagen.loot.*
import com.stal111.forbidden_arcanus.datagen.model.ModBlockModels
import com.stal111.forbidden_arcanus.datagen.model.ModItemModels
import com.stal111.forbidden_arcanus.datagen.particle.ParticleDataProvider
import com.stal111.forbidden_arcanus.datagen.recipes.*
import com.stal111.forbidden_arcanus.datagen.tags.ModBlockTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModEnchantmentTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModEntityTypeTagsProvider
import com.stal111.forbidden_arcanus.datagen.tags.ModItemTagsProvider
import com.stal111.forbidden_arcanus.datagen.worldgen.feature.ModConfiguredFeatures
import com.stal111.forbidden_arcanus.datagen.worldgen.modifier.ModBiomeModifiers
import com.stal111.forbidden_arcanus.datagen.worldgen.placement.ModOrePlacements
import com.stal111.forbidden_arcanus.datagen.worldgen.placement.ModTreePlacements
import com.stal111.forbidden_arcanus.datagen.worldgen.placement.ModVegetationPlacements
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.neoforge.registries.NeoForgeRegistries
import net.valhelsia.dataforge.DataCollector
import net.valhelsia.dataforge.DataProviderContext
import net.valhelsia.dataforge.DataTarget
import net.valhelsia.dataforge.model.DataForgeModelProvider
import net.valhelsia.dataforge.recipe.DataForgeRecipeRunner

class ProviderCollector : DataCollector() {
    override fun collectProviders(context: DataProviderContext) {
        val blocks = ForbiddenArcanus.REGISTRY_MANAGER.blockHelper.registryEntries.map { { it.value() } }

        with(DataTarget.CLIENT) {
            addProvider(this, LangProvider(context.packOutput))
            addProvider(this, ModSoundsProvider(context))
            addProvider(this, DataForgeModelProvider(context, blocks, { ModBlockModels(it) }, { ModItemModels(it) }))
            addProvider(this, ParticleDataProvider(context))
        }

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
            addProvider(
                this, LootTableProvider(
                    context.packOutput, setOf<ResourceKey<LootTable>>(), listOf(
                        LootTableProvider.SubProviderEntry({ ModBlockLoot(it, blocks) }, LootContextParamSets.BLOCK),
                        LootTableProvider.SubProviderEntry({ ModEntityLoot(it) }, LootContextParamSets.ENTITY),
                        LootTableProvider.SubProviderEntry({ ModBlockLootAdditions() }, LootContextParamSets.BLOCK),
                        LootTableProvider.SubProviderEntry({ ModChestLootAdditions() }, LootContextParamSets.CHEST),
                        LootTableProvider.SubProviderEntry({ ModEntityLootAdditions(it) }, LootContextParamSets.ENTITY),
                    ),
                    context.lookupProvider
                )
            )
            addProvider(this, ModLootModifierProvider(context))
        }
    }

    override fun collectRegistryProviders() {
        addRegistryProvider(FARegistries.ENHANCER_DEFINITION, ModEnhancerDefinitions)
        addRegistryProvider(FARegistries.MAGIC_CIRCLE, ModMagicCircles)
        addRegistryProvider(FARegistries.RITUAL, ModRituals)
        addRegistryProvider(FARegistries.RESIDUE_TYPE, ModResidueTypes)
        addRegistryProvider(FARegistries.ITEM_MODIFIER, ModItemModifiers)
        addRegistryProvider(Registries.ENCHANTMENT, ModEnchantments)
        addRegistryProvider(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers)
        addRegistryProvider(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures)
        addRegistryProvider(Registries.PLACED_FEATURE, ModOrePlacements, ModTreePlacements, ModVegetationPlacements)
    }
}
