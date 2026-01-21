package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.BuiltInResidueTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance
import com.stal111.forbidden_arcanus.common.item.enhancer.BuiltInEnhancers
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.core.registry.FARegistries
import com.stal111.forbidden_arcanus.data.recipes.builder.ClibanoRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeCategory
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.CookingBookCategory
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class ClibanoRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput
) : RecipeSubProvider(provider, recipeOutput) {
    override fun buildRecipes() {
        val residueLookup = registries.lookupOrThrow(FARegistries.RESIDUE_TYPE)
        val enhancerLookup = registries.lookupOrThrow(FARegistries.ENHANCER_DEFINITION)

        this.add(
            this.clibanoRecipe(
                ModItems.ARCANE_CRYSTAL.get(),
                Ingredient.of(items.getOrThrow(ModTags.Items.ARCANE_CRYSTAL_ORES)),
                1.0f,
                100
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.ARCANE_CRYSTAL), CHANCE_33))
                .unlockedBy(ModTags.Items.ARCANE_CRYSTAL_ORES)
        )
        this.add(
            this.clibanoRecipe(
                ModItems.ARCANE_CRYSTAL_DUST.get(),
                Ingredient.of(ModItems.ARCANE_CRYSTAL.get()),
                0.4f,
                80
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.ARCANE_CRYSTAL), CHANCE_10))
                .unlockedBy(ModItems.ARCANE_CRYSTAL.get())
        )
        this.add(
            this.clibanoRecipe(
                ModItems.RUNE.get(),
                Ingredient.of(items.getOrThrow(ModTags.Items.RUNIC_STONES)),
                0.5f,
                100
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.RUNE), CHANCE_10))
                .unlockedBy(ModTags.Items.RUNIC_STONES)
        )

        this.add(
            this.clibanoRecipe(Items.COAL, Ingredient.of(items.getOrThrow(ItemTags.COAL_ORES)), 0.05f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.COAL), CHANCE_33))
                .unlockedBy(ItemTags.COAL_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.IRON_INGOT, Ingredient.of(items.getOrThrow(ItemTags.IRON_ORES)), 0.35f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.IRON), CHANCE_33))
                .unlockedBy(ItemTags.IRON_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.GOLD_INGOT, Ingredient.of(items.getOrThrow(ItemTags.GOLD_ORES)), 0.5f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.GOLD), CHANCE_20))
                .unlockedBy(ItemTags.GOLD_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.COPPER_INGOT, Ingredient.of(items.getOrThrow(ItemTags.COPPER_ORES)), 0.35f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.COPPER), CHANCE_33))
                .unlockedBy(ItemTags.COPPER_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.LAPIS_LAZULI, Ingredient.of(items.getOrThrow(ItemTags.LAPIS_ORES)), 0.1f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.LAPIS_LAZULI), CHANCE_20))
                .unlockedBy(ItemTags.LAPIS_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.DIAMOND, Ingredient.of(items.getOrThrow(ItemTags.DIAMOND_ORES)), 0.5f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.DIAMOND), CHANCE_10))
                .unlockedBy(ItemTags.DIAMOND_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.EMERALD, Ingredient.of(items.getOrThrow(ItemTags.EMERALD_ORES)), 0.5f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.EMERALD), CHANCE_10))
                .unlockedBy(ItemTags.EMERALD_ORES)
        )
        this.add(
            this.clibanoRecipe(Items.NETHERITE_SCRAP, Ingredient.of(Blocks.ANCIENT_DEBRIS), 1.0f, 100)
                .residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.NETHERITE), CHANCE_05))
                .unlockedBy(Blocks.ANCIENT_DEBRIS)
        )

        this.add(
            this.clibanoRecipe(
                Items.IRON_INGOT,
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_IRON)),
                0.35f,
                100
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.IRON), CHANCE_33)).unlockedBy(
                Tags.Items.RAW_MATERIALS_IRON
            ), "clibano_combustion/iron_ingot_from_clibano_combusting_raw_iron"
        )
        this.add(
            this.clibanoRecipe(
                Items.GOLD_INGOT,
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_GOLD)),
                0.5f,
                100
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.GOLD), CHANCE_20)).unlockedBy(
                Tags.Items.RAW_MATERIALS_GOLD
            ), "clibano_combustion/gold_ingot_from_clibano_combusting_raw_gold"
        )
        this.add(
            this.clibanoRecipe(
                Items.COPPER_INGOT,
                Ingredient.of(items.getOrThrow(Tags.Items.RAW_MATERIALS_COPPER)),
                0.35f,
                100
            ).residue(ResidueChance(residueLookup.getOrThrow(BuiltInResidueTypes.COPPER), CHANCE_33)).unlockedBy(
                Tags.Items.RAW_MATERIALS_COPPER
            ), "clibano_combustion/copper_ingot_from_clibano_combusting_raw_copper"
        )

        val artisanRelic = enhancerLookup.getOrThrow(BuiltInEnhancers.ARTISAN_RELIC)
    }

    fun add(builder: RecipeBuilder) = super.add(
        builder,
        "clibano_combustion/" + BuiltInRegistries.ITEM.getKey(builder.result).path + "_from_clibano_combustion"
    )

    private fun clibanoRecipe(
        result: ItemLike,
        ingredient: Ingredient,
        experience: Float,
        cookingTime: Int
    ): ClibanoRecipeBuilder {
        return ClibanoRecipeBuilder(
            RecipeCategory.MISC,
            CookingBookCategory.MISC,
            result.asItem().defaultInstance,
            ingredient,
            experience,
            cookingTime
        )
    }

    companion object {
        const val CHANCE_65: Double = 0.65
        const val CHANCE_33: Double = 0.33
        const val CHANCE_20: Double = 0.2
        const val CHANCE_10: Double = 0.1
        const val CHANCE_05: Double = 0.05
    }
}
