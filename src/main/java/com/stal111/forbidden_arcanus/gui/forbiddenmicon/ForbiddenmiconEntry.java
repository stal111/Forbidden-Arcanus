package com.stal111.forbidden_arcanus.gui.forbiddenmicon;

import com.stal111.forbidden_arcanus.gui.forbiddenmicon.element.button.ChangeCategoryButton;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

import java.util.Collection;

public class ForbiddenmiconEntry {

    private final ItemStack stack;
    private final boolean hasTitle;
    private final String title;
    private final boolean hasDescription;
    private final String description;
    private final ForbiddenmiconCategory category;

    public ForbiddenmiconEntry(ItemStack stack, ForbiddenmiconCategory category) {
        this(stack, "", category);
    }

    public ForbiddenmiconEntry(ItemStack stack, String description, ForbiddenmiconCategory category) {
        this.stack = stack;
        this.hasTitle = true;
        this.title = stack.getDisplayName().getFormattedText();
        this.hasDescription = !description.isEmpty();
        this.description = description;
        this.category = category;
    }

    public boolean hasTitle() {
        return this.hasTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public Collection<IRecipe<?>> getRecipes(Collection<IRecipeSerializer<?>> recipeSerializer) {
        if (!ModUtils.getRecipesByOutput(stack, recipeSerializer).isEmpty()) {
            return ModUtils.getRecipesByOutput(stack, recipeSerializer);
        }
        return null;
    }

    public boolean hasDescription() {
        return hasDescription;
    }

    public String getDescription() {
        return description;
    }

    public ForbiddenmiconCategory getCategory() {
        return category;
    }
}
