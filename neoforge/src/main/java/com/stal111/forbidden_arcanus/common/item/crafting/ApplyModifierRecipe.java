package com.stal111.forbidden_arcanus.common.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record ApplyModifierRecipe(Ingredient template,
                                  Ingredient addition,
                                  Holder<ItemModifier> modifier,
                                  PlacementInfo placementInfo) implements SmithingRecipe {

    public ApplyModifierRecipe(Ingredient template, Ingredient addition, Holder<ItemModifier> modifier) {
        this(template, addition, modifier, PlacementInfo.create(List.of(template, addition)));
    }

    @Override
    public boolean matches(@NotNull SmithingRecipeInput recipeInput, @NotNull Level level) {
        return Ingredient.testOptionalIngredient(this.templateIngredient(), recipeInput.template())
                && Ingredient.testOptionalIngredient(this.additionIngredient(), recipeInput.addition())
                && this.modifier.value().isValidItem(recipeInput.base());
    }

    @Override
    public @NotNull Optional<Ingredient> templateIngredient() {
        return Optional.of(this.template);
    }

    @Override
    public Ingredient baseIngredient() {
        return Ingredient.of(BuiltInRegistries.ITEM.stream().filter(item -> this.modifier.value().isValidItem(item.getDefaultInstance())));
    }

    @Override
    public @NotNull Optional<Ingredient> additionIngredient() {
        return Optional.of(this.addition);
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput recipeInput) {
        ItemStack stack = recipeInput.base().copyWithCount(1);

        ModifierHelper.setModifier(stack, this.modifier);

        return stack;
    }

    @Override
    public RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return ModRecipeSerializers.APPLY_MODIFIER.get();
    }

    @Override
    public @NotNull PlacementInfo placementInfo() {
        return this.placementInfo;
    }

    public static class Serializer implements RecipeSerializer<ApplyModifierRecipe> {

        private static final MapCodec<ApplyModifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.fieldOf("template").forGetter(ApplyModifierRecipe::template),
                Ingredient.CODEC.fieldOf("addition").forGetter(ApplyModifierRecipe::addition),
                ItemModifier.CODEC.fieldOf("modifier").forGetter(ApplyModifierRecipe::modifier)
        ).apply(instance, ApplyModifierRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC,
                ApplyModifierRecipe::template,
                Ingredient.CONTENTS_STREAM_CODEC,
                ApplyModifierRecipe::addition,
                ItemModifier.STREAM_CODEC,
                ApplyModifierRecipe::modifier,
                ApplyModifierRecipe::new
        );

        @Override
        public @NotNull MapCodec<ApplyModifierRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
