package com.stal111.forbidden_arcanus.common.recipe;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * Clibano Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.recipe.ClibanoRecipe
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-05-28
 */
public class ClibanoRecipe extends AbstractCookingRecipe {

    public static final int DEFAULT_COOKING_TIME = 100;

    private final Map<ClibanoFireType, Integer> cookingTimes = new EnumMap<>(ClibanoFireType.class);

    private final NonNullList<Ingredient> ingredients;

    private final @Nullable ResidueChance residueChance;

    /**
     * The minimum fire type that needs to be present to start this recipe.
     */
    private final ClibanoFireType requiredFireType;

    private final @Nullable Holder<EnhancerDefinition> requiredEnhancer;

    public ClibanoRecipe(String group, CookingBookCategory category, NonNullList<Ingredient> ingredients, ItemStack result, float experience, int cookingTime, @Nullable ResidueChance residueChance, ClibanoFireType requiredFireType, @Nullable Holder<EnhancerDefinition> requiredEnhancer) {
        super(ModRecipeTypes.CLIBANO_COMBUSTION.get(), group, category, null, result, experience, cookingTime);
        this.ingredients = ingredients;
        this.residueChance = residueChance;
        this.requiredFireType = requiredFireType;
        this.requiredEnhancer = requiredEnhancer;

        for (ClibanoFireType fireType : ClibanoFireType.values()) {
            this.cookingTimes.put(fireType, (int) (cookingTime / fireType.getCookingSpeedMultiplier()));
        }
    }

    public boolean matches(@NotNull Container inv, @NotNull Level level, List<EnhancerDefinition> enhancers) {
        if (this.getRequiredEnhancer() != null && !enhancers.contains(this.getRequiredEnhancer().value())) {
            return false;
        }

        List<ItemStack> inputs = new ArrayList<>();

        for (int i = 0; i < this.ingredients.size(); i++) {
            inputs.add(inv.getItem(i));
        }

        return RecipeMatcher.findMatches(inputs, this.ingredients) != null;
    }

    @DoNotCall
    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
        return this.matches(inv, level, Collections.emptyList());
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
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

    public boolean isDoubleRecipe() {
        return this.getIngredients().size() == 2;
    }

    @NotNull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
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

        private static final Codec<ClibanoRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> {
                    return recipe.group;
                }),
                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> {
                    return recipe.category;
                }),
                NonNullList.codecOf(Ingredient.CODEC_NONEMPTY).flatXmap(INGREDIENT_CHECK, INGREDIENT_CHECK).fieldOf("ingredients").forGetter(recipe -> {
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
                ExtraCodecs.strictOptionalField(ResidueChance.CODEC, "residue").forGetter(recipe -> {
                    return Optional.ofNullable(recipe.residueChance);
                }),
                ClibanoFireType.CODEC.fieldOf("fire_type").orElse(ClibanoFireType.FIRE).forGetter(recipe -> {
                    return recipe.requiredFireType;
                }),
                ExtraCodecs.strictOptionalField(EnhancerDefinition.REFERENCE_CODEC, "enhancer").forGetter(recipe -> {
                    return Optional.ofNullable(recipe.requiredEnhancer);
                })
        ).apply(instance, (s, category, ingredients, stack, experience, cooking_time, residue, fireType, enhancer) -> {
            return new ClibanoRecipe(s, category, ingredients, stack, experience, cooking_time, residue.orElse(null), fireType, enhancer.orElse(null));
        }));

        @Override
        public @NotNull Codec<ClibanoRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull ClibanoRecipe fromNetwork(@NotNull FriendlyByteBuf buffer) {
            return buffer.readJsonWithCodec(CODEC);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull ClibanoRecipe recipe) {
            buffer.writeJsonWithCodec(CODEC, recipe);
        }
    }
}
