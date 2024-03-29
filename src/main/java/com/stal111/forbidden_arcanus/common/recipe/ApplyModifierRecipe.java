package com.stal111.forbidden_arcanus.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Apply Modifier Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.recipe.ApplyModifierRecipe
 *
 * @author stal111
 * @since 2021-11-29
 */
public class ApplyModifierRecipe implements SmithingRecipe {

    private final ResourceLocation id;

    private final Ingredient template;
    private final Ingredient addition;

    private final ItemModifier modifier;

    public ApplyModifierRecipe(ResourceLocation id, Ingredient template, Ingredient addition, ItemModifier modifier) {
        this.id = id;
        this.addition = addition;
        this.template = template;
        this.modifier = modifier;
    }

    @Override
    public boolean matches(@Nonnull Container inv, @Nonnull Level level) {
        if (!this.isTemplateIngredient(inv.getItem(0)) || !this.isBaseIngredient(inv.getItem(1))) {
            return false;
        }
        return this.isAdditionIngredient(inv.getItem(2));
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container inv, @NotNull RegistryAccess registryAccess) {
        ItemStack stack = inv.getItem(1).copy();

        ModifierHelper.setModifier(stack, this.modifier);

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_MODIFIER.get();
    }

    public Ingredient getTemplate() {
        return this.template;
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

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return this.modifier.canItemContainModifier(stack);
    }

    @Override
    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return this.addition.test(stack);
    }

    public static class Serializer implements RecipeSerializer<ApplyModifierRecipe> {
        @Nonnull
        @Override
        public ApplyModifierRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            Ingredient template = Ingredient.fromJson(GsonHelper.getNonNull(json, "template"));
            Ingredient addition = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "addition"));

            ResourceLocation resourceLocation = ResourceLocation.tryParse(GsonHelper.getAsString(json, "modifier"));

            if (!FARegistries.ITEM_MODIFIER_REGISTRY.get().containsKey(resourceLocation)) {
                throw new JsonSyntaxException("Unknown item modifier '" + resourceLocation + "'");
            }
            ItemModifier modifier = FARegistries.ITEM_MODIFIER_REGISTRY.get().getValue(resourceLocation);
            return new ApplyModifierRecipe(recipeId, template, addition, modifier);
        }

        @Override
        public ApplyModifierRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            Ingredient template = Ingredient.fromNetwork(buffer);
            Ingredient addition = Ingredient.fromNetwork(buffer);
            ItemModifier modifier = FARegistries.ITEM_MODIFIER_REGISTRY.get().getValue(buffer.readResourceLocation());
            return new ApplyModifierRecipe(recipeId, template, addition, modifier);
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, ApplyModifierRecipe recipe) {
            recipe.template.toNetwork(buffer);
            recipe.addition.toNetwork(buffer);
            buffer.writeResourceLocation(Objects.requireNonNull(FARegistries.ITEM_MODIFIER_REGISTRY.get().getKey(recipe.modifier)));
        }
    }
}
