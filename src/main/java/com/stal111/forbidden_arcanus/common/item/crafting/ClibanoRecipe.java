package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * Clibano Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.crafting.ClibanoRecipe
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-05-28
 */
public class ClibanoRecipe implements Recipe<ClibanoRecipeInput> {

    public static final int DEFAULT_COOKING_TIME = 100;

    private final Map<ClibanoFireType, Integer> cookingTimes = new EnumMap<>(ClibanoFireType.class);

    private final RecipeType<?> type;
    protected final CookingBookCategory category;
    private final String group;

    private final Either<Ingredient, Pair<Ingredient, Ingredient>> ingredients;
    private final ItemStack result;

    private final float experience;
    private final int cookingTime;

    private final @Nullable ResidueChance residueChance;

    /**
     * The minimum fire type that needs to be present to start this recipe.
     */
    private final ClibanoFireType requiredFireType;

    private final @Nullable Holder<EnhancerDefinition> requiredEnhancer;

    public ClibanoRecipe(String group, CookingBookCategory category, Either<Ingredient, Pair<Ingredient, Ingredient>> ingredients, ItemStack result, float experience, int cookingTime, @Nullable ResidueChance residueChance, ClibanoFireType requiredFireType, @Nullable Holder<EnhancerDefinition> requiredEnhancer) {
        this.type = ModRecipeTypes.CLIBANO_COMBUSTION.get();
        this.group = group;
        this.category = category;
        this.ingredients = ingredients;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
        this.residueChance = residueChance;
        this.requiredFireType = requiredFireType;
        this.requiredEnhancer = requiredEnhancer;

        for (ClibanoFireType fireType : ClibanoFireType.values()) {
            this.cookingTimes.put(fireType, (int) (cookingTime / fireType.getCookingSpeedMultiplier()));
        }
    }

    public boolean matches(@NotNull ClibanoRecipeInput recipeInput, @NotNull Level level, List<EnhancerDefinition> enhancers) {
        if (this.getRequiredEnhancer() != null && !enhancers.contains(this.getRequiredEnhancer().value())) {
            return false;
        }

        List<ItemStack> inputs = new ArrayList<>(recipeInput.getInputs());
        var list = this.ingredients.map(List::of, pair -> List.of(pair.getFirst(), pair.getSecond()));

        return RecipeMatcher.findMatches(inputs, list) != null;
    }

    @DoNotCall
    @Override
    public boolean matches(@NotNull ClibanoRecipeInput recipeInput, @NotNull Level level) {
        return this.matches(recipeInput, level, Collections.emptyList());
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull ClibanoRecipeInput recipeInput, HolderLookup.@NotNull Provider lookupProvider) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.@NotNull Provider lookupProvider) {
        return this.result;
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients.map(NonNullList::of, pair -> NonNullList.of(pair.getFirst(), pair.getSecond()));
    }

    /**
     * Gets the cooking time for the given fire type.
     *
     * @param fireType the ClibanoFireType currently in use
     * @return the correct cookingTime for the given fire type
     */
    public int getCookingTime(ClibanoFireType fireType) {
        return this.cookingTimes.get(fireType);
    }

    public @Nullable ResidueChance getResidueChance() {
        return this.residueChance;
    }

    public ClibanoFireType getRequiredFireType() {
        return this.requiredFireType;
    }

    public @Nullable Holder<EnhancerDefinition> getRequiredEnhancer() {
        return this.requiredEnhancer;
    }

    public float getExperience() {
        return this.experience;
    }

    public boolean isDoubleRecipe() {
        return this.getIngredients().size() == 2;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }

    @Override
    public @NotNull ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CLIBANO_CORE.get());
    }

    public static class Serializer implements RecipeSerializer<ClibanoRecipe> {

        private static final Function<NonNullList<Ingredient>, DataResult<NonNullList<Ingredient>>> INGREDIENT_CHECK = ingredients -> {
            if (ingredients.isEmpty() || ingredients.size() >= 3) {
                return DataResult.error(() -> "Invalid number of ingredients", ingredients);
            }
            return DataResult.success(ingredients);
        };

        private static final MapCodec<ClibanoRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> {
                    return recipe.group;
                }),
                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> {
                    return recipe.category;
                }),
                Codec.either(Ingredient.CODEC_NONEMPTY, Codec.pair(Ingredient.CODEC_NONEMPTY, Ingredient.CODEC_NONEMPTY)).fieldOf("ingredients").forGetter(recipe -> {
                    return recipe.ingredients;
                }),
                BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter(recipe -> {
                    return recipe.result;
                }),
                Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> {
                    return recipe.experience;
                }),
                Codec.INT.fieldOf("cooking_time").orElse(ClibanoRecipe.DEFAULT_COOKING_TIME).forGetter(recipe -> {
                    return recipe.cookingTime;
                }),
                ResidueChance.CODEC.optionalFieldOf("residue").forGetter(recipe -> {
                    return Optional.ofNullable(recipe.residueChance);
                }),
                ClibanoFireType.CODEC.fieldOf("fire_type").orElse(ClibanoFireType.FIRE).forGetter(recipe -> {
                    return recipe.requiredFireType;
                }),
                EnhancerDefinition.REFERENCE_CODEC.optionalFieldOf("enhancer").forGetter(recipe -> {
                    return Optional.ofNullable(recipe.requiredEnhancer);
                })
        ).apply(instance, (s, category, ingredients, stack, experience, cooking_time, residue, fireType, enhancer) -> {
            return new ClibanoRecipe(s, category, ingredients, stack, experience, cooking_time, residue.orElse(null), fireType, enhancer.orElse(null));
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> STREAM_CODEC = StreamCodec.of(
                ClibanoRecipe.Serializer::toNetwork, ClibanoRecipe.Serializer::fromNetwork
        );

        @Override
        public @NotNull MapCodec<ClibanoRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        public static @NotNull ClibanoRecipe fromNetwork(@NotNull FriendlyByteBuf buffer) {
            return buffer.readJsonWithCodec(CODEC.codec());
        }

        public static void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull ClibanoRecipe recipe) {
            buffer.writeJsonWithCodec(CODEC.codec(), recipe);
        }
    }
}
