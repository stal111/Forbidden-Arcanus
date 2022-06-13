package com.stal111.forbidden_arcanus.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Apply Modifier Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.recipe.ApplyModifierRecipe
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-29
 */
public class ApplyModifierRecipe extends UpgradeRecipe {

    private final ItemModifier modifier;
    private final Ingredient addition;

    public ApplyModifierRecipe(ResourceLocation id, Ingredient addition, ItemModifier modifier) {
        super(id, Ingredient.EMPTY, addition, ItemStack.EMPTY);
        this.addition = addition;
        this.modifier = modifier;
    }

    @Override
    public boolean matches(@Nonnull Container inv, @Nonnull Level level) {
        if (!this.modifier.canItemContainModifier(inv.getItem(0))) {
            return false;
        }
        return this.addition.test(inv.getItem(1));
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container inv) {
        ItemStack stack = inv.getItem(0).copy();

        ModifierHelper.setModifier(stack, this.modifier);

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
        return ModRecipes.APPLY_MODIFIER.get();
    }

    public ItemModifier getModifier() {
        return this.modifier;
    }

    public Ingredient getAddition() {
        return this.addition;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ApplyModifierRecipe> {
        @Nonnull
        @Override
        public ApplyModifierRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));

            ResourceLocation resourceLocation = ResourceLocation.tryParse(GsonHelper.getAsString(json, "modifier"));

            if (!ForbiddenArcanus.ITEM_MODIFIER_REGISTRY.get().containsKey(resourceLocation)) {
                throw new JsonSyntaxException("Unknown item modifier '" + resourceLocation + "'");
            }
            ItemModifier modifier = ForbiddenArcanus.ITEM_MODIFIER_REGISTRY.get().getValue(resourceLocation);
            return new ApplyModifierRecipe(recipeId, addition, modifier);
        }

        @Override
        public ApplyModifierRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            Ingredient addition = Ingredient.fromNetwork(buffer);
            ItemModifier modifier = ForbiddenArcanus.ITEM_MODIFIER_REGISTRY.get().getValue(buffer.readResourceLocation());
            return new ApplyModifierRecipe(recipeId, addition, modifier);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, ApplyModifierRecipe recipe) {
            recipe.addition.toNetwork(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(recipe.modifier.getRegistryName()));
        }
    }
}
