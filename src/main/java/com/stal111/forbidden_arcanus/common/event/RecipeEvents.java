package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

/**
 * @author stal111
 * @since 17.05.2024
 */
public class RecipeEvents {

    @SubscribeEvent
    public void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        event.getBuilder().addRecipe(Ingredient.of(ModItems.AUREAL_BOTTLE.get()), Ingredient.of(Tags.Items.GUNPOWDERS), new ItemStack(ModItems.SPLASH_AUREAL_BOTTLE.get()));
        event.getBuilder().addMix(Potions.WATER, ModItems.STRANGE_ROOT.get(), Potions.AWKWARD);
    }
}
