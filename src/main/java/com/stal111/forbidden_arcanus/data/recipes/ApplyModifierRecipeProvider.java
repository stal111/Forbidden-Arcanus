package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModItemModifiers;
import com.stal111.forbidden_arcanus.data.recipes.builder.ApplyModifierRecipeBuilder;
import net.minecraft.world.level.ItemLike;
import net.valhelsia.valhelsia_core.data.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.data.recipes.ValhelsiaRecipeProvider;

/**
 * @author stal111
 * @since 2022-10-21
 */
public class ApplyModifierRecipeProvider extends RecipeSubProvider {


    public ApplyModifierRecipeProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
    }

    @Override
    protected void registerRecipes() {
        this.modifier(ModItems.ETERNAL_STELLA.get(), ModItemModifiers.ETERNAL.get());
        this.modifier(ModItems.SMELTER_PRISM.get(), ModItemModifiers.FIERY.get());
    }

    private void modifier(ItemLike addition, ItemModifier modifier) {
        this.add(ApplyModifierRecipeBuilder.of(addition, modifier));
    }
}
