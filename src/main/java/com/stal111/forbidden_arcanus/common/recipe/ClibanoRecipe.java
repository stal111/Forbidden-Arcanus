package com.stal111.forbidden_arcanus.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResidueType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

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
    private final ResidueInfo residueInfo;

    /**
     * The minimum fire type that needs to be present to start this recipe.
     */
    private final ClibanoFireType requiredFireType;

    public ClibanoRecipe(ResourceLocation id, String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, ResidueInfo residueInfo, ClibanoFireType requiredFireType) {
        super(ModRecipeTypes.CLIBANO_COMBUSTION.get(), id, group, category, ingredient, result, experience, cookingTime);
        this.residueInfo = residueInfo;
        this.requiredFireType = requiredFireType;

        for (ClibanoFireType fireType : ClibanoFireType.values()) {
            this.cookingTimes.put(fireType, (int) (cookingTime / fireType.getCookingSpeedMultiplier()));
        }
    }

    @Override
    public boolean matches(@Nonnull Container inv, @Nonnull Level level) {
        return this.ingredient.test(inv.getItem(0));
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

    public ResidueInfo getResidueInfo() {
        return this.residueInfo;
    }

    public ClibanoFireType getRequiredFireType() {
        return this.requiredFireType;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CLIBANO_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<ClibanoRecipe> {

        @Nonnull
        @Override
        public ClibanoRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject jsonObject) {
            String group = GsonHelper.getAsString(jsonObject, "group", "");
            JsonElement jsonElement = GsonHelper.isArrayNode(jsonObject, "ingredient") ? GsonHelper.getAsJsonArray(jsonObject, "ingredient") : GsonHelper.getAsJsonObject(jsonObject, "ingredient");
            Ingredient ingredient = Ingredient.fromJson(jsonElement);

            CookingBookCategory category = CookingBookCategory.CODEC.byName(GsonHelper.getAsString(jsonObject, "category", null), CookingBookCategory.MISC);

            if (!jsonObject.has("result")) {
                throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
            }

            ItemStack stack;

            if (jsonObject.get("result").isJsonObject()) {
                stack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));
            } else {
                String result = GsonHelper.getAsString(jsonObject, "result");
                stack = new ItemStack(ForgeRegistries.ITEMS.getDelegateOrThrow(new ResourceLocation(result)).get());
            }

            float experience = GsonHelper.getAsFloat(jsonObject, "experience", 0.0F);
            int cookingTime = GsonHelper.getAsInt(jsonObject, "cooking_time", ClibanoRecipe.DEFAULT_COOKING_TIME);

            ResidueInfo residueInfo = ResidueInfo.fromJson(jsonObject);
            ResidueType residueType = new ResidueType(residueInfo.name);

            if (!ResiduesStorage.RESIDUE_TYPES.contains(residueType) && residueInfo != ResidueInfo.NONE) {
                ResiduesStorage.RESIDUE_TYPES.add(residueType);
            }

            Optional<ClibanoFireType> fireType = ClibanoFireType.byName(GsonHelper.getAsString(jsonObject, "fire_type", "fire"));

            return new ClibanoRecipe(recipeId, group, category, ingredient, stack, experience, cookingTime, residueInfo, fireType.orElse(ClibanoFireType.FIRE));
        }

        @Nullable
        @Override
        public ClibanoRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CookingBookCategory category = buffer.readEnum(CookingBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            float f = buffer.readFloat();
            int i = buffer.readVarInt();

            Optional<ClibanoFireType> fireType = ClibanoFireType.byName(buffer.readUtf());

            return new ClibanoRecipe(recipeId, s, category, ingredient, itemstack, f, i, ResidueInfo.fromNetwork(buffer), fireType.orElse(ClibanoFireType.FIRE));
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, @Nonnull ClibanoRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeEnum(recipe.category());
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookingTime);

            buffer.writeUtf(recipe.requiredFireType.getSerializedName());

            recipe.residueInfo.toNetwork(buffer);
        }
    }

    public record ResidueInfo(String name, double chance) {

        public static final ResidueInfo NONE = new ResidueInfo("none", 0.0D);

        public static ResidueInfo fromNetwork(@Nonnull FriendlyByteBuf buffer) {
            return new ResidueInfo(buffer.readUtf(), buffer.readDouble());
        }

        public void toNetwork(@Nonnull FriendlyByteBuf buffer) {
            buffer.writeUtf(this.name);
            buffer.writeDouble(this.chance);
        }

        public ResidueType getType() {
            return ResiduesStorage.RESIDUE_TYPES.stream().filter(residueType -> residueType.name().equals(this.name)).findFirst().orElseThrow(() -> {
                return new IllegalStateException("No ResidueType found");
            });
        }

        public static ResidueInfo fromJson(@Nonnull JsonObject jsonObject) {
            if (jsonObject.has("residue")) {
                JsonObject residue = jsonObject.getAsJsonObject("residue");

                return new ResidueInfo(GsonHelper.getAsString(residue, "name"), GsonHelper.getAsDouble(residue, "chance"));
            }

            return NONE;
        }

        public void toJson(@Nonnull JsonObject jsonObject) {
            JsonObject residue = new JsonObject();

            if (this == ResidueInfo.NONE) {
                return;
            }

            residue.addProperty("name", this.name);
            residue.addProperty("chance", this.chance);

            jsonObject.add("residue", residue);
        }
    }
}
