package com.stal111.forbidden_arcanus.common.integration;

import com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe;
import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ApplyModifierCategoryExtension implements ISmithingCategoryExtension<ApplyModifierRecipe> {

    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(ApplyModifierRecipe recipe, T ingredientAcceptor) {
        recipe.templateIngredient().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(ApplyModifierRecipe recipe, T ingredientAcceptor) {
        ingredientAcceptor.add(recipe.baseIngredient());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(ApplyModifierRecipe recipe, T ingredientAcceptor) {
        recipe.additionIngredient().ifPresent(ingredientAcceptor::add);
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(ApplyModifierRecipe recipe, T ingredientAcceptor) {
        Optional<Ingredient> templateIngredient = recipe.templateIngredient();
        Ingredient baseIngredient = recipe.baseIngredient();
        Optional<Ingredient> additionIngredient = recipe.additionIngredient();

        Minecraft minecraft = Minecraft.getInstance();
        ContextMap contextmap = SlotDisplayContext.fromLevel(Objects.requireNonNull(minecraft.level));

        List<ItemStack> templateStacks = templateIngredient.map(i -> i.display().resolveForStacks(contextmap)).orElse(List.of(ItemStack.EMPTY));
        if (templateStacks.isEmpty()) {
            templateStacks = List.of(ItemStack.EMPTY);
        }

        List<ItemStack> baseStacks = baseIngredient.display().resolveForStacks(contextmap);
        if (baseStacks.isEmpty()) {
            baseStacks = List.of(ItemStack.EMPTY);
        }

        ItemStack addition = additionIngredient.map(i -> i.display().resolveForFirstStack(contextmap)).orElse(ItemStack.EMPTY);

        for (ItemStack template : templateStacks) {
            for (ItemStack base : baseStacks) {
                SmithingRecipeInput recipeInput = new SmithingRecipeInput(template, base, addition);
                ItemStack output = recipe.assemble(recipeInput);
                ingredientAcceptor.add(output);
            }
        }
    }

    @Override
    public void onDisplayedIngredientsUpdate(
            ApplyModifierRecipe recipe,
            IRecipeSlotDrawable templateSlot,
            IRecipeSlotDrawable baseSlot,
            IRecipeSlotDrawable additionSlot,
            IRecipeSlotDrawable outputSlot,
            IFocusGroup focuses
    ) {
        List<IFocus<?>> outputFocuses = focuses.getFocuses(RecipeIngredientRole.OUTPUT).toList();
        if (outputFocuses.isEmpty()) {
            ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
            ItemStack base = baseSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
            ItemStack addition = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

            SmithingRecipeInput recipeInput = new SmithingRecipeInput(template, base, addition);
            ItemStack output = recipe.assemble(recipeInput);
            IIngredientAcceptor<?> iIngredientAcceptor = outputSlot.createDisplayOverrides();
            iIngredientAcceptor.add(output);
        } else {
            ItemStack output = outputSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
            ItemStack base = new ItemStack(output.getItem());
            ItemStack template = templateSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);
            ItemStack addition = additionSlot.getDisplayedItemStack().orElse(ItemStack.EMPTY);

            IIngredientAcceptor<?> iIngredientAcceptor1 = baseSlot.createDisplayOverrides();
            iIngredientAcceptor1.add(base);

            SmithingRecipeInput recipeInput = new SmithingRecipeInput(template, base, addition);
            output = recipe.assemble(recipeInput);
            IIngredientAcceptor<?> iIngredientAcceptor = outputSlot.createDisplayOverrides();
            iIngredientAcceptor.add(output);
        }
    }
}
