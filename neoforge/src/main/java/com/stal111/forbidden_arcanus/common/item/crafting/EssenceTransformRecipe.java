package com.stal111.forbidden_arcanus.common.item.crafting;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.item.crafting.input.EssenceTransformInput;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class EssenceTransformRecipe implements Recipe<EssenceTransformInput> {

    public static final MapCodec<EssenceTransformRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Ingredient.CODEC.fieldOf("input").forGetter(recipe -> recipe.input),
            EssenceValue.CODEC.fieldOf("essence").forGetter(recipe -> recipe.essence),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
    ).apply(i, EssenceTransformRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EssenceTransformRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC,
            recipe -> recipe.input,
            EssenceValue.STREAM_CODEC,
            recipe -> recipe.essence,
            ItemStackTemplate.STREAM_CODEC,
            recipe -> recipe.result,
            EssenceTransformRecipe::new
    );

    public static final RecipeSerializer<EssenceTransformRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

    private final Ingredient input;
    private final EssenceValue essence;
    private final ItemStackTemplate result;

    public EssenceTransformRecipe(Ingredient input, EssenceValue essence, ItemStackTemplate result) {
        this.input = input;
        this.essence = essence;
        this.result = result;
    }

    @Override
    public boolean matches(EssenceTransformInput input, Level level) {
        return this.input.test(input.item())
                && input.essenceValue().type() == this.essence.type()
                && input.essenceValue().amount() >= this.essence.amount();
    }

    @Override
    public ItemStack assemble(EssenceTransformInput input) {
        input.consumeEssence().accept(this.essence.amount());

        input.item().shrink(1);

        return this.result.create();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public RecipeSerializer<? extends Recipe<EssenceTransformInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<EssenceTransformInput>> getType() {
        return ModRecipeTypes.ESSENCE_TRANSFORM.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
