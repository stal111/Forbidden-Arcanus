package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
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
import java.util.function.Supplier;

public class ApplyModifierRecipe extends SimpleSmithingRecipe {

    private static final MapCodec<ApplyModifierRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Recipe.CommonInfo.MAP_CODEC.forGetter(recipe -> recipe.commonInfo),
            Ingredient.CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
            Ingredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
            ItemModifier.CODEC.fieldOf("modifier").forGetter(recipe -> recipe.modifier)
    ).apply(instance, ApplyModifierRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ApplyModifierRecipe> STREAM_CODEC = StreamCodec.composite(
            Recipe.CommonInfo.STREAM_CODEC,
            o -> o.commonInfo,
            Ingredient.CONTENTS_STREAM_CODEC,
            recipe -> recipe.template,
            Ingredient.CONTENTS_STREAM_CODEC,
            recipe -> recipe.addition,
            ItemModifier.STREAM_CODEC,
            recipe -> recipe.modifier,
            ApplyModifierRecipe::new
    );

    public static final RecipeSerializer<ApplyModifierRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

    private final Ingredient template;
    private final Supplier<Ingredient> base;
    private final Ingredient addition;
    private final Holder<ItemModifier> modifier;

    public ApplyModifierRecipe(Recipe.CommonInfo commonInfo, Ingredient template, Ingredient addition, Holder<ItemModifier> modifier) {
        super(commonInfo);
        this.template = template;
        this.base = Suppliers.memoize(() -> Ingredient.of(BuiltInRegistries.ITEM.stream().filter(item -> modifier.value().isValidItem(item.getDefaultInstance()))));
        this.addition = addition;
        this.modifier = modifier;
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
        return this.base.get();
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
    public RecipeSerializer<? extends SimpleSmithingRecipe> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.create(List.of(this.template, this.baseIngredient(), this.addition));
    }
}
