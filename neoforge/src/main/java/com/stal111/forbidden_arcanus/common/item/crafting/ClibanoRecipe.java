package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public record ClibanoRecipe(String group,
                            CookingBookCategory category,
                            Either<Ingredient, Pair<Ingredient, Ingredient>> ingredients,
                            ItemStack result,
                            float experience,
                            ClibanoCookingTimes cookingTimes,
                            Optional<ResidueChance> residueChance,
                            ClibanoFireType requiredFireType,
                            Optional<Holder<EnhancerDefinition>> requiredEnhancer) implements Recipe<ClibanoRecipeInput> {

    public static final ClibanoCookingTimes DEFAULT_COOKING_TIMES = ClibanoCookingTimes.of(100);

    public boolean matches(@NotNull ClibanoRecipeInput recipeInput, @NotNull Level level, HolderSet<EnhancerDefinition> enhancers) {
        if (!enhancerMatches(enhancers)) {
            return false;
        }

        return this.matches(recipeInput, level);
    }

    private boolean enhancerMatches(HolderSet<EnhancerDefinition> enhancers) {
        return this.requiredEnhancer.isEmpty() || enhancers.contains(this.requiredEnhancer.get());
    }

    @DoNotCall
    @Override
    public boolean matches(@NotNull ClibanoRecipeInput recipeInput, @NotNull Level level) {
        List<ItemStack> inputs = new ArrayList<>(recipeInput.getInputs());
        var list = this.ingredients.map(List::of, pair -> List.of(pair.getFirst(), pair.getSecond()));

        return RecipeMatcher.findMatches(inputs, list) != null;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull ClibanoRecipeInput recipeInput, HolderLookup.@NotNull Provider lookupProvider) {
        return this.result.copy();
    }

    //TODO
//    @Override
//    public @NotNull NonNullList<Ingredient> getIngredients() {
//        return this.ingredients.map(ingredient -> NonNullList.of(Ingredient.EMPTY, ingredient), pair -> NonNullList.of(Ingredient.EMPTY, pair.getFirst(), pair.getSecond()));
//    }

    public int getDefaultCookingTime() {
        return this.cookingTimes.get(this.requiredFireType);
    }

    public float getExperience() {
        return this.experience;
    }

    public boolean isDoubleRecipe() {
        return this.ingredients.right().isPresent();
    }

    @Override
    public @NotNull RecipeSerializer<? extends Recipe<ClibanoRecipeInput>> getSerializer() {
        return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<? extends Recipe<ClibanoRecipeInput>> getType() {
        return ModRecipeTypes.CLIBANO_COMBUSTION.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return null;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    public static class Serializer implements RecipeSerializer<ClibanoRecipe> {

        private static final MapCodec<ClibanoRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(ClibanoRecipe::group),
                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(ClibanoRecipe::category),
                Codec.either(Ingredient.CODEC, Codec.mapPair(Ingredient.CODEC.fieldOf("first"), Ingredient.CODEC.fieldOf("second")).codec()).fieldOf("ingredients").forGetter(ClibanoRecipe::ingredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(ClibanoRecipe::result),
                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(ClibanoRecipe::experience),
                ClibanoCookingTimes .CODEC.fieldOf("cooking_time").orElse(ClibanoRecipe.DEFAULT_COOKING_TIMES).forGetter(ClibanoRecipe::cookingTimes),
                ResidueChance.CODEC.optionalFieldOf("residue").forGetter(ClibanoRecipe::residueChance),
                ClibanoFireType.CODEC.fieldOf("fire_type").orElse(ClibanoFireType.FIRE).forGetter(ClibanoRecipe::requiredFireType),
                EnhancerDefinition.REFERENCE_CODEC.optionalFieldOf("enhancer").forGetter(ClibanoRecipe::requiredEnhancer)
        ).apply(instance, ClibanoRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

        @Override
        public @NotNull MapCodec<ClibanoRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
