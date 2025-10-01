package com.stal111.forbidden_arcanus.common.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record ApplyModifierRecipe(Optional<Ingredient> template,
                                  Optional<Ingredient> addition,
                                  Holder<ItemModifier> modifier,
                                  HolderLookup.RegistryLookup<Item> registryLookup,
                                  PlacementInfo placementInfo) implements SmithingRecipe {

    public ApplyModifierRecipe(Optional<Ingredient> template, Optional<Ingredient> addition, Holder<ItemModifier> modifier, HolderLookup.RegistryLookup<Item> registryLookup) {
        this(template, addition, modifier, registryLookup, PlacementInfo.createFromOptionals(List.of(template, addition)));
    }

    //TODO: refactor

    @Override
    public boolean matches(@NotNull SmithingRecipeInput recipeInput, @NotNull Level level) {
        System.out.println(this.modifier.getRegisteredName());
        System.out.println(this.isTemplateIngredient(recipeInput.template()));
        System.out.println(this.isAdditionIngredient(recipeInput.addition()));
        System.out.println(this.isBaseIngredient(recipeInput.base()));
        return this.isTemplateIngredient(recipeInput.template()) && this.isAdditionIngredient(recipeInput.addition()) && this.isBaseIngredient(recipeInput.base());
    }

    @Override
    public @NotNull Optional<Ingredient> templateIngredient() {
        return this.template;
    }

    @Override
    public Ingredient baseIngredient() {
        return Ingredient.of(new NotHolderSet<>(this.registryLookup, HolderSet.direct(Items.AIR.builtInRegistryHolder())));
    }

    @Override
    public @NotNull Optional<Ingredient> additionIngredient() {
        return this.addition;
    }

    @NotNull
    @Override
    public ItemStack assemble(@NotNull SmithingRecipeInput recipeInput, @NotNull HolderLookup.Provider provider) {
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


    public boolean isTemplateIngredient(@NotNull ItemStack stack) {
        return this.template.orElseThrow().test(stack);
    }

    public boolean isBaseIngredient(@NotNull ItemStack stack) {
        return this.modifier.value().isValidItem(stack);
    }

    public boolean isAdditionIngredient(@NotNull ItemStack stack) {
        return this.addition.orElseThrow().test(stack);
    }

    public static class Serializer implements RecipeSerializer<ApplyModifierRecipe> {

        private static final MapCodec<ApplyModifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC.optionalFieldOf("template").forGetter(ApplyModifierRecipe::template),
                Ingredient.CODEC.optionalFieldOf("addition").forGetter(ApplyModifierRecipe::addition),
                ItemModifier.CODEC.fieldOf("modifier").forGetter(ApplyModifierRecipe::modifier),
                RegistryOps.retrieveRegistryLookup(Registries.ITEM).forGetter(ApplyModifierRecipe::registryLookup)
        ).apply(instance, ApplyModifierRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,
                ApplyModifierRecipe::template,
                Ingredient.OPTIONAL_CONTENTS_STREAM_CODEC,
                ApplyModifierRecipe::addition,
                ItemModifier.STREAM_CODEC,
                ApplyModifierRecipe::modifier,
                (ingredient, ingredient2, itemModifierHolder) -> new ApplyModifierRecipe(ingredient, ingredient2, itemModifierHolder, null)
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
