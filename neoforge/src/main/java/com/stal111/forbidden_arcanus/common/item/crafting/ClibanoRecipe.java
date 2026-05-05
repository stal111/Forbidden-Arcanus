package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class ClibanoRecipe implements Recipe<SingleRecipeInput> {

    private static final MapCodec<ClibanoRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
            MoltenMaterial.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(recipe -> recipe.experience),
            ClibanoCookingTimes.CODEC.optionalFieldOf("cooking_time", ClibanoRecipe.DEFAULT_COOKING_TIMES).forGetter(recipe -> recipe.cookingTimes),
            EnhancerDefinition.CODEC.optionalFieldOf("enhancer").forGetter(recipe -> Optional.ofNullable(recipe.requiredEnhancer))
    ).apply(instance, (ingredient, moltenMaterial, experience, cookingTimes, enhancer) -> new ClibanoRecipe(ingredient, moltenMaterial, experience, cookingTimes, enhancer.orElse(null))));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    public static final RecipeSerializer<ClibanoRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);


    private final Ingredient ingredient;
    private final MoltenMaterial result;
    private final float experience;
    private final ClibanoCookingTimes cookingTimes;
    private final @Nullable Holder<EnhancerDefinition> requiredEnhancer;

    private @Nullable PlacementInfo placementInfo;

    public ClibanoRecipe(
            Ingredient ingredient,
            MoltenMaterial result,
            float experience,
            ClibanoCookingTimes cookingTimes,
            @Nullable Holder<EnhancerDefinition> requiredEnhancer) {
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTimes = cookingTimes;
        this.requiredEnhancer = requiredEnhancer;
    }

    public static final ClibanoCookingTimes DEFAULT_COOKING_TIMES = ClibanoCookingTimes.of(100);

    private boolean enhancerMatches(HolderSet<EnhancerDefinition> enhancers) {
        return this.requiredEnhancer == null || enhancers.contains(this.requiredEnhancer);
    }

    @DoNotCall
    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return this.ingredient.test(recipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookingTime(ClibanoFireType fireType) {
        return this.cookingTimes.get(fireType);
    }

    public MoltenMaterial result() {
        return this.result;
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return ModRecipeTypes.CLIBANO_COMBUSTION.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        if (this.placementInfo == null) {
            this.placementInfo = PlacementInfo.create(this.ingredient);
        }

        return this.placementInfo;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }
}
