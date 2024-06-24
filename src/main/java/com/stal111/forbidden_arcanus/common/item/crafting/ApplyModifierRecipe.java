package com.stal111.forbidden_arcanus.common.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * Apply Modifier Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.crafting.ApplyModifierRecipe
 *
 * @author stal111
 * @since 2021-11-29
 */
public record ApplyModifierRecipe(Ingredient template,
                                  Ingredient addition,
                                  Holder<ItemModifier> modifier) implements SmithingRecipe {

    @Override
    public boolean matches(@NotNull SmithingRecipeInput recipeInput, @NotNull Level level) {
        ItemStack base = recipeInput.base();

        if (ModifierHelper.hasModifier(base) || !this.isTemplateIngredient(recipeInput.template()) || !this.isBaseIngredient(base)) {
            return false;
        }
        return this.isAdditionIngredient(recipeInput.addition());
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SmithingRecipeInput recipeInput, @NotNull HolderLookup.Provider provider) {
        ItemStack stack = recipeInput.base().copy();

        ModifierHelper.setModifier(stack, this.modifier);

        return stack;
    }

    @NotNull
    @Override
    public ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_MODIFIER.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return this.template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return true;
        //TODO
//        return this.modifier.value().canItemContainModifier(stack);
    }

    @Override
    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return this.addition.test(stack);
    }

    public static class Serializer implements RecipeSerializer<ApplyModifierRecipe> {

        private static final MapCodec<ApplyModifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("template").forGetter(recipe -> {
                    return recipe.template;
                }),
                Ingredient.CODEC_NONEMPTY.fieldOf("addition").forGetter(recipe -> {
                    return recipe.addition;
                }),
                ItemModifier.CODEC.fieldOf("modifier").forGetter(recipe -> {
                    return recipe.modifier;
                })
        ).apply(instance, ApplyModifierRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> STREAM_CODEC = StreamCodec.of(
                ApplyModifierRecipe.Serializer::toNetwork, ApplyModifierRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ApplyModifierRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static ApplyModifierRecipe fromNetwork(@NotNull RegistryFriendlyByteBuf buffer) {
            Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
            Holder<ItemModifier> modifier = ItemModifier.STREAM_CODEC.decode(buffer);

            return new ApplyModifierRecipe(template, addition, modifier);
        }

        public static void toNetwork(@NotNull RegistryFriendlyByteBuf buffer, ApplyModifierRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            ItemModifier.STREAM_CODEC.encode(buffer, recipe.modifier);
        }
    }
}
