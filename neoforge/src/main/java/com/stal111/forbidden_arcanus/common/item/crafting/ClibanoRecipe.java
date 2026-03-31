package com.stal111.forbidden_arcanus.common.item.crafting;

import com.google.errorprone.annotations.DoNotCall;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoCookingTimes;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueChance;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public class ClibanoRecipe implements Recipe<SingleRecipeInput> {

    private static final MapCodec<ClibanoRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(ClibanoRecipe::group),
            CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> recipe.category),
            Ingredient.CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
            Codec.FLOAT.fieldOf("experience").orElse(0.0F).forGetter(recipe -> recipe.experience),
            ClibanoCookingTimes.CODEC.fieldOf("cooking_time").orElse(ClibanoRecipe.DEFAULT_COOKING_TIMES).forGetter(recipe -> recipe.cookingTimes),
            ResidueChance.CODEC.optionalFieldOf("residue").forGetter(recipe -> recipe.residueChance),
            ClibanoFireType.CODEC.fieldOf("fire_type").orElse(ClibanoFireType.FIRE).forGetter(recipe -> recipe.requiredFireType),
            EnhancerDefinition.CODEC.optionalFieldOf("enhancer").forGetter(recipe -> recipe.requiredEnhancer)
    ).apply(instance, ClibanoRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ClibanoRecipe> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    public static final RecipeSerializer<ClibanoRecipe> SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);


    private final String group;
    private final CookingBookCategory category;
    private final Ingredient ingredient;
    private final ItemStackTemplate result;
    private final float experience;
    private final ClibanoCookingTimes cookingTimes;
    private final Optional<ResidueChance> residueChance;
    private final ClibanoFireType requiredFireType;
    private final Optional<Holder<EnhancerDefinition>> requiredEnhancer;

    private @Nullable PlacementInfo placementInfo;

    public ClibanoRecipe(String group,
                         CookingBookCategory category,
                         Ingredient ingredient,
                         ItemStackTemplate result,
                         float experience,
                         ClibanoCookingTimes cookingTimes,
                         Optional<ResidueChance> residueChance,
                         ClibanoFireType requiredFireType,
                         Optional<Holder<EnhancerDefinition>> requiredEnhancer) {
        this.group = group;
        this.category = category;
        this.ingredient = ingredient;
        this.result = result;
        this.experience = experience;
        this.cookingTimes = cookingTimes;
        this.residueChance = residueChance;
        this.requiredFireType = requiredFireType;
        this.requiredEnhancer = requiredEnhancer;
    }

    public static final ClibanoCookingTimes DEFAULT_COOKING_TIMES = ClibanoCookingTimes.of(100);

    private boolean enhancerMatches(HolderSet<EnhancerDefinition> enhancers) {
        return this.requiredEnhancer.isEmpty() || enhancers.contains(this.requiredEnhancer.get());
    }

    @DoNotCall
    @Override
    public boolean matches(@NotNull SingleRecipeInput recipeInput, @NotNull Level level) {
        return this.ingredient.test(recipeInput.item());
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input) {
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

    public ClibanoFireType requiredFireType() {
        return this.requiredFireType;
    }

    public ClibanoCookingTimes cookingTimes() {
        return this.cookingTimes;
    }

    public Optional<ResidueChance> residueChance() {
        return this.residueChance;
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
