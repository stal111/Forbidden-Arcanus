package com.stal111.forbidden_arcanus.common.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
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
public record ApplyModifierRecipe(Ingredient template,
                                  Ingredient addition,
                                  ItemModifier modifier) implements SmithingRecipe {

    @Override
    public boolean matches(@Nonnull Container inv, @Nonnull Level level) {
        if (!this.isTemplateIngredient(inv.getItem(0)) || !this.isBaseIngredient(inv.getItem(1))) {
            return false;
        }
        return this.isAdditionIngredient(inv.getItem(2));
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull Container inv, @NotNull HolderLookup.Provider provider) {
        ItemStack stack = inv.getItem(1).copy();

        ModifierHelper.setModifier(stack, this.modifier);

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Nonnull
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
        return this.modifier.canItemContainModifier(stack);
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
                ItemModifier.NAME_CODEC.fieldOf("modifier").forGetter(recipe -> {
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
            ItemModifier modifier = FARegistries.ITEM_MODIFIER_REGISTRY.get(buffer.readResourceLocation());

            return new ApplyModifierRecipe(template, addition, modifier);
        }

        public static void toNetwork(@Nonnull RegistryFriendlyByteBuf buffer, ApplyModifierRecipe recipe) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.template);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.addition);
            buffer.writeResourceLocation(Objects.requireNonNull(FARegistries.ITEM_MODIFIER_REGISTRY.getKey(recipe.modifier)));
        }
    }
}
