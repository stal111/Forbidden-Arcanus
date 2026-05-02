package com.stal111.forbidden_arcanus.datagen

import com.stal111.forbidden_arcanus.ForbiddenArcanus
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.datagen.atlas.ModAtlasProvider
import com.stal111.forbidden_arcanus.datagen.block.clibano.ModMoltenMaterialTypes
import com.stal111.forbidden_arcanus.datagen.block.clibano.ModResidueTypes
import com.stal111.forbidden_arcanus.datagen.block.forge.ModMagicCircles
import com.stal111.forbidden_arcanus.datagen.block.forge.ModRituals
import com.stal111.forbidden_arcanus.datagen.item.ModEnchantments
import com.stal111.forbidden_arcanus.datagen.item.ModEnhancerDefinitions
import com.stal111.forbidden_arcanus.datagen.item.ModItemModifiers
import com.stal111.forbidden_arcanus.datagen.item.WandMaterialProvider
import com.stal111.forbidden_arcanus.datagen.lang.LangProvider
import com.stal111.forbidden_arcanus.datagen.loot.*
import com.stal111.forbidden_arcanus.datagen.model.ModBlockModels
import com.stal111.forbidden_arcanus.datagen.model.ModEquipmentAssetProvider
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
import net.valhelsia.dataforge.model.DataForgeModelProvider
import net.valhelsia.dataforge.recipe.DataForgeRecipeRunner

class ProviderCollector : DataCollector() {

    override fun collectClientProviders(context: DataProviderContext.Client) {
        addClientProvider(LangProvider(context.packOutput))
        addClientProvider(ModSoundsProvider(context))
        addClientProvider(ModEquipmentAssetProvider(context))
        addClientProvider(DataForgeModelProvider(context, { ModBlockModels(it) }, { ModItemModels(it) }))
        addClientProvider(ParticleDataProvider(context))
        addClientProvider(ModAtlasProvider(context))
    }

    override fun collectServerProviders(context: DataProviderContext.Server) {
        val blocks = ForbiddenArcanus.REGISTRY_MANAGER.blockHelper.registryEntries.map { { it.value() } }

        addServerProvider(ModBlockTagsProvider(context))
        addServerProvider(ModItemTagsProvider(context))
        addServerProvider(ModEnchantmentTagsProvider(context))
        addServerProvider(ModEntityTypeTagsProvider(context))
        addServerProvider(
            DataForgeRecipeRunner(
                context,
                ::CraftingRecipeProvider,
                ::StonecutterRecipeProvider,
                ::ApplyModifierRecipeProvider,
                ::SpecialRecipesProvider,
                ::ClibanoRecipeProvider
            )
        )
        addServerProvider(
            LootTableProvider(
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
        addServerProvider(ModLootModifierProvider(context))
    }

    override fun collectRegistryProviders() {
        addRegistryProvider(FARegistries.ENHANCER_DEFINITION, ModEnhancerDefinitions)
        addRegistryProvider(FARegistries.MAGIC_CIRCLE, ModMagicCircles)
        addRegistryProvider(FARegistries.RITUAL, ModRituals)
        addRegistryProvider(FARegistries.RESIDUE_TYPE, ModResidueTypes)
        addRegistryProvider(FARegistries.MOLTEN_MATERIAL_TYPE, ModMoltenMaterialTypes)
        addRegistryProvider(FARegistries.ITEM_MODIFIER, ModItemModifiers)
        addRegistryProvider(FARegistries.WAND_MATERIAL, WandMaterialProvider)
        addRegistryProvider(Registries.ENCHANTMENT, ModEnchantments)
        addRegistryProvider(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers)
        addRegistryProvider(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures)
        addRegistryProvider(Registries.PLACED_FEATURE, ModOrePlacements, ModTreePlacements, ModVegetationPlacements)
    }
}
