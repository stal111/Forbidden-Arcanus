package com.stal111.forbidden_arcanus.common.item.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author stal111
 * @since 23.06.2024
 */
public record ClibanoRecipeInput(ItemStack firstInput, ItemStack secondInput) implements RecipeInput {

    public List<ItemStack> getInputs() {
        return Stream.of(this.firstInput, this.secondInput).filter(itemStack -> !itemStack.isEmpty()).toList();
    }

    @Override
    public ItemStack getItem(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
