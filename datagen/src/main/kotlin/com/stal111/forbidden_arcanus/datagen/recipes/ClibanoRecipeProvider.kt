package com.stal111.forbidden_arcanus.datagen.recipes

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.BuiltinMoltenMaterialTypes
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterialType
import com.stal111.forbidden_arcanus.core.init.ModItems
import com.stal111.forbidden_arcanus.datagen.recipes.builder.ClibanoAlloyingRecipeBuilder
import com.stal111.forbidden_arcanus.datagen.recipes.builder.ClibanoMeltingRecipeBuilder
import com.stal111.forbidden_arcanus.util.ModTags
import net.minecraft.core.HolderLookup
import net.minecraft.data.recipes.RecipeBuilder
import net.minecraft.data.recipes.RecipeOutput
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.Blocks
import net.neoforged.neoforge.common.Tags
import net.valhelsia.dataforge.recipe.RecipeSubProvider

class ClibanoRecipeProvider(
    provider: HolderLookup.Provider,
    recipeOutput: RecipeOutput,
) : RecipeSubProvider(provider, recipeOutput) {

    override fun buildRecipes() {
        add(melting(BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL, ModItems.ARCANE_CRYSTAL))
        add(melting(BuiltinMoltenMaterialTypes.RUNE, ModTags.Items.RUNIC_STONES))
        add(melting(BuiltinMoltenMaterialTypes.COAL, ItemTags.COAL_ORES))
        add(melting(BuiltinMoltenMaterialTypes.IRON, ItemTags.IRON_ORES))
        add(melting(BuiltinMoltenMaterialTypes.GOLD, ItemTags.GOLD_ORES))
        add(melting(BuiltinMoltenMaterialTypes.COPPER, ItemTags.COPPER_ORES))
        add(melting(BuiltinMoltenMaterialTypes.LAPIS_LAZULI, ItemTags.LAPIS_ORES))
        add(melting(BuiltinMoltenMaterialTypes.DIAMOND, ItemTags.DIAMOND_ORES))
        add(melting(BuiltinMoltenMaterialTypes.EMERALD, ItemTags.EMERALD_ORES))
        add(melting(BuiltinMoltenMaterialTypes.NETHERITE, Blocks.ANCIENT_DEBRIS))

        addSuffixed(
            melting(BuiltinMoltenMaterialTypes.IRON, Tags.Items.RAW_MATERIALS_IRON),
            "from_melting_raw_iron",
        )
        addSuffixed(
            melting(BuiltinMoltenMaterialTypes.GOLD, Tags.Items.RAW_MATERIALS_GOLD),
            "from_melting_raw_gold",
        )
        addSuffixed(
            melting(BuiltinMoltenMaterialTypes.COPPER, Tags.Items.RAW_MATERIALS_COPPER),
            "from_melting_raw_copper",
        )

        add(melting(BuiltinMoltenMaterialTypes.ASTERITE, ModItems.ASTERITE_CHUNK, cookingTime = 500))

        add(
            alloying(
                listOf(
                    material(BuiltinMoltenMaterialTypes.NETHERITE, 3 * 9),
                    material(BuiltinMoltenMaterialTypes.GOLD, 3 * 9),
                ),
                Items.NETHERITE_INGOT,
            )
        )
        add(
            alloying(
                listOf(
                    material(BuiltinMoltenMaterialTypes.GOLD, 2 * 9),
                    material(BuiltinMoltenMaterialTypes.ARCANE_CRYSTAL, 4 * 9),
                ),
                ModItems.DEORUM_INGOT,
            )
        )
        add(
            alloying(
                listOf(
                    material(BuiltinMoltenMaterialTypes.COPPER, 4 * 9),
                    material(BuiltinMoltenMaterialTypes.IRON, 2 * 9),
                    material(BuiltinMoltenMaterialTypes.GOLD, 2 * 9),
                    material(BuiltinMoltenMaterialTypes.ASTERITE, 4 * 9),
                ),
                ModItems.STYGIAN_STEEL_INGOT,
            )
        )
    }

    private fun addSuffixed(builder: RecipeBuilder, suffix: String) = super.add(
        builder,
        builder.defaultId().identifier().path + "_" + suffix,
    )

    private fun material(
        type: ResourceKey<MoltenMaterialType>,
        amount: Int = 9,
    ) = MoltenMaterial(registries.getOrThrow(type), amount)

    private fun melting(
        result: ResourceKey<MoltenMaterialType>,
        ingredient: Ingredient,
        cookingTime: Int = 100,
    ) = ClibanoMeltingRecipeBuilder(material(result), ingredient, cookingTime)

    private fun melting(
        result: ResourceKey<MoltenMaterialType>,
        ingredient: ItemLike,
        cookingTime: Int = 100,
    ) = ClibanoMeltingRecipeBuilder(material(result), ingredient, cookingTime)

    private fun melting(
        result: ResourceKey<MoltenMaterialType>,
        tag: TagKey<Item>,
        cookingTime: Int = 100,
    ) = melting(result, Ingredient.of(items.getOrThrow(tag)), cookingTime)

    private fun alloying(
        requiredMaterials: List<MoltenMaterial>,
        result: ItemLike,
        duration: Int = 100,
    ) = ClibanoAlloyingRecipeBuilder(requiredMaterials, result, duration)
}
