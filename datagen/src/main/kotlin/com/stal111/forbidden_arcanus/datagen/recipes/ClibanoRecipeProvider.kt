package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.BuiltinMoltenMaterialTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.data.recipes.builder.ClibanoRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.tags.ItemTags
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

        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL), 9),
                Ingredient.of(items.getOrThrow(ModTags.Items.ARCANE_CRYSTAL_ORES)),
                100
            ).unlockedBy(ModTags.Items.ARCANE_CRYSTAL_ORES)
        )

        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.RUNE), 9),
                Ingredient.of(items.getOrThrow(ModTags.Items.RUNIC_STONES)),
                100
            ).unlockedBy(ModTags.Items.RUNIC_STONES)
        )

        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COAL), 9),
                Ingredient.of(items.getOrThrow(ItemTags.COAL_ORES)),
                100
            ).unlockedBy(ItemTags.COAL_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.IRON), 9),
                Ingredient.of(items.getOrThrow(ItemTags.IRON_ORES)),
                100
            ).unlockedBy(ItemTags.IRON_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 9),
                Ingredient.of(items.getOrThrow(ItemTags.GOLD_ORES)), 100
            ).unlockedBy(ItemTags.GOLD_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COPPER), 9),
                Ingredient.of(items.getOrThrow(ItemTags.COPPER_ORES)), 100
            ).unlockedBy(ItemTags.COPPER_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.LAPIS_LAZULI), 9),
                Ingredient.of(items.getOrThrow(ItemTags.LAPIS_ORES)), 100
            ).unlockedBy(ItemTags.LAPIS_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.DIAMOND), 9),
                Ingredient.of(items.getOrThrow(ItemTags.DIAMOND_ORES)), 100
            ).unlockedBy(ItemTags.DIAMOND_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.EMERALD), 9),
                Ingredient.of(items.getOrThrow(ItemTags.EMERALD_ORES)), 100
            ).unlockedBy(ItemTags.EMERALD_ORES)
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.NETHERITE), 9),
                Ingredient.of(Blocks.ANCIENT_DEBRIS), 100
            ).unlockedBy(Blocks.ANCIENT_DEBRIS)
        )

        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.IRON), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_IRON)),
                100
            ).unlockedBy(Tags.Items.RAW_MATERIALS_IRON),
            "clibano_combustion/iron_ingot_from_clibano_combusting_raw_iron"
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.GOLD), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_GOLD)),
                100
            ).unlockedBy(Tags.Items.RAW_MATERIALS_GOLD),
            "clibano_combustion/gold_ingot_from_clibano_combusting_raw_gold"
        )
        this.add(
            this.clibanoRecipe(
                MoltenMaterial(registries.getOrThrow(BuiltinMoltenMaterialTypes.COPPER), 9),
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_COPPER)),
                100
            ).unlockedBy(Tags.Items.RAW_MATERIALS_COPPER),
            "clibano_combustion/copper_ingot_from_clibano_combusting_raw_copper"
        )

        val artisanRelic = enhancerLookup.getOrThrow(BuiltInEnhancers.ARTISAN_RELIC)
    }

    fun add(builder: RecipeBuilder) = super.add(
        builder,
        builder.defaultId().identifier().path + "_from_clibano_combustion"
    )

    private fun clibanoRecipe(
        result: MoltenMaterial,
        ingredient: Ingredient,
        cookingTime: Int
    ): ClibanoRecipeBuilder {
        return ClibanoRecipeBuilder(
            result,
            ingredient,
            cookingTime
        )
    }
}
