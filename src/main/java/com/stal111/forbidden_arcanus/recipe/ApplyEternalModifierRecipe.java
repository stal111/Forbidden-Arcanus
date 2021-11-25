package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.init.other.ModItemModifiers;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Apply Indestructible Enchantment Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.recipe.ApplyEternalModifierRecipe
 *
 * @author stal111
 * @version 2.0.0
 */
public class ApplyEternalModifierRecipe extends UpgradeRecipe {

    public ApplyEternalModifierRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.of(ModItems.ETERNAL_STELLA.get()), ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Container inv, @Nonnull Level world) {
        ItemStack input = inv.getItem(0);

        List<Enchantment> enchantments = new ArrayList<>(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED.getValues());

        if (!input.isDamageableItem() || ItemStackUtils.hasStackEnchantment(input, enchantments) || ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(input.getItem())) {
            return false;
        }
        return this.isAdditionIngredient(inv.getItem(1));
    }

    @Nonnull
    @Override
    public ItemStack assemble(Container inv) {
        ItemStack stack = inv.getItem(0).copy();

        ModifierHelper.setModifier(stack, ModItemModifiers.ETERNAL.get());

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_INDESTRUCTIBLE_ENCHANTMENT.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}