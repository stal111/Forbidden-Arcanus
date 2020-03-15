package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.item.ModItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class AwkwardPotionBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        return PotionUtils.getPotionFromItem(input) == Potions.WATER;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == ModItems.STRANGE_ROOT.get();
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (!input.isEmpty() && !ingredient.isEmpty() && isIngredient(ingredient)) {
            return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.AWKWARD);
        }
        return ItemStack.EMPTY;
    }
}
