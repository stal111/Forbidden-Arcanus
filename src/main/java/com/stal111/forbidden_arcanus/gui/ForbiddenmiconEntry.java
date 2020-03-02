package com.stal111.forbidden_arcanus.gui;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.Collection;

public class ForbiddenmiconEntry {

    private final ItemStack stack;
    private final boolean hasTitle;
    private final String title;
    private final boolean hasDescription;
    private final String description;

    public ForbiddenmiconEntry(ItemStack stack) {
        this(stack, "");
    }

    public ForbiddenmiconEntry(ItemStack stack, String description) {
        this.stack = stack;
        this.hasTitle = true;
        this.title = stack.getDisplayName().getFormattedText();
        this.hasDescription = !description.isEmpty();
        this.description = description;
    }

    public boolean hasTitle() {
        return this.hasTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public Collection<IRecipe<?>> getRecipes() {
        if (!ModUtils.getCraftingRecipesByOutput(stack).isEmpty()) {
            return ModUtils.getCraftingRecipesByOutput(stack);
        }
        return null;
    }

    public Collection<IRecipe<?>> getSmeltingRecipes() {
        if (!ModUtils.getSmeltingRecipesByOutput(stack).isEmpty()) {
            return ModUtils.getSmeltingRecipesByOutput(stack);
        }
        return null;
    }

    public boolean hasRecipe() {
        return getSmeltingRecipes() != null || getRecipes() != null;
    }

    public boolean hasDescription() {
        return hasDescription;
    }

    public String getDescription() {
        return description;
    }
}
