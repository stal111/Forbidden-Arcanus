package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.BuiltinMoltenMaterialTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.datagen.recipes.builder.ClibanoAlloyingRecipeBuilder
import com.stal111.forbidden_arcanus.datagen.recipes.builder.ClibanoMeltingRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ItemStackTemplate
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class ClibanoRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput
) : RecipeSubProvider(provider, recipeOutput) {
    override fun buildRecipes() {
        val enhancerLookup = registries.lookupOrThrow(FARegistries.ENHANCER_DEFINITION)

        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL), 9),
                Ingredient.of(items.getOrThrow(ModTags.Items.ARCANE_CRYSTAL_ORES)),
                100
            )
        )

        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.RUNE), 9),
                Ingredient.of(items.getOrThrow(ModTags.Items.RUNIC_STONES)),
                100
            )
        )

        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COAL), 9),
                Ingredient.of(items.getOrThrow(ItemTags.COAL_ORES)),
                100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.IRON), 9),
                Ingredient.of(items.getOrThrow(ItemTags.IRON_ORES)),
                100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 9),
                Ingredient.of(items.getOrThrow(ItemTags.GOLD_ORES)), 100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COPPER), 9),
                Ingredient.of(items.getOrThrow(ItemTags.COPPER_ORES)), 100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.LAPIS_LAZULI), 9),
                Ingredient.of(items.getOrThrow(ItemTags.LAPIS_ORES)), 100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.DIAMOND), 9),
                Ingredient.of(items.getOrThrow(ItemTags.DIAMOND_ORES)), 100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.EMERALD), 9),
                Ingredient.of(items.getOrThrow(ItemTags.EMERALD_ORES)), 100
            )
        )
        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.NETHERITE), 9),
                Ingredient.of(Blocks.ANCIENT_DEBRIS), 100
            )
        )

        addSuffixed(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.IRON), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_IRON)),
                100
            ),
            "from_melting_raw_iron"
        )
        addSuffixed(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_GOLD)),
                100
            ),
            "from_melting_raw_gold"
        )
        addSuffixed(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COPPER), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_COPPER)),
                100
            ),
            "from_melting_raw_copper"
        )

        add(
            melting(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.ASTERITE), 9),
                Ingredient.of(ModItems.ASTERITE_CHUNK), 500
            )
        )

        val artisanRelic = enhancerLookup.getOrThrow(BuiltInEnhancers.ARTISAN_RELIC)

        add(
            alloying(
                listOf(
                    MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.NETHERITE), 3 * 9),
                    MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 3 * 9)
                ),
                ItemStackTemplate(Items.NETHERITE_INGOT)
            )
        )

        add(
            alloying(
                listOf(
                    MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 2 * 9),
                    MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL), 4 * 9)
                ),
                ItemStackTemplate(ModItems.DEORUM_INGOT.get())
            )
        )
    }

    fun addSuffixed(builder: RecipeBuilder, suffix: String) = super.add(
        builder,
        builder.defaultId().identifier().path + "_" + suffix
    )

    private fun melting(
        result: MoltenMaterial,
        ingredient: Ingredient,
        cookingTime: Int
    ) = ClibanoMeltingRecipeBuilder(
        result,
        ingredient,
        cookingTime
    )

    private fun alloying(
        requiredMaterials: List<MoltenMaterial>,
        result: ItemStackTemplate,
    ) = ClibanoAlloyingRecipeBuilder(
        requiredMaterials,
        result
    )
}
