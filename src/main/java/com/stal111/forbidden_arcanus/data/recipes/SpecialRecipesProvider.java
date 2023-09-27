package com.stal111.forbidden_arcanus.data.recipes;

import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.valhelsia.valhelsia_core.api.datagen.recipes.RecipeSubProvider;
import net.valhelsia.valhelsia_core.api.datagen.recipes.ValhelsiaRecipeProvider;

/**
 * @author stal111
 * @since 24.09.2023
 */
public class SpecialRecipesProvider extends RecipeSubProvider {

    public SpecialRecipesProvider(ValhelsiaRecipeProvider provider) {
        super(provider);
    }

    @Override
    protected void registerRecipes() {
        SpecialRecipeBuilder.special(ModRecipeSerializers.COMBINE_AUREAL_TANK.get()).save(this.getFinishedRecipeConsumer(), "combine_aureal_tank");
    }
}
