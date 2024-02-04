package com.stal111.forbidden_arcanus.common.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFireType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResidueType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

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

    public ClibanoRecipe(String group, CookingBookCategory category, Ingredient ingredient, ItemStack result, float experience, int cookingTime, ResidueInfo residueInfo, ClibanoFireType requiredFireType) {
        super(ModRecipeTypes.CLIBANO_COMBUSTION.get(), group, category, ingredient, result, experience, cookingTime);
        this.residueInfo = residueInfo;
        this.requiredFireType = requiredFireType;

        for (ClibanoFireType fireType : ClibanoFireType.values()) {
            this.cookingTimes.put(fireType, (int) (cookingTime / fireType.getCookingSpeedMultiplier()));
        }
    }

    @Override
    public boolean matches(@NotNull Container inv, @NotNull Level level) {
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

        private static final Codec<ClibanoRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter(recipe -> {
                    return recipe.group;
                }),
                CookingBookCategory.CODEC.fieldOf("category").orElse(CookingBookCategory.MISC).forGetter(recipe -> {
                    return recipe.category;
                }),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> {
                    return recipe.ingredient;
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
                ClibanoFireType.CODEC.fieldOf("fire_type").orElse(ClibanoFireType.FIRE).forGetter(recipe -> {
                    return recipe.requiredFireType;
                })
        ).apply(instance, (s, cookingBookCategory, ingredient1, stack, aFloat, integer, clibanoFireType) -> new ClibanoRecipe(s, cookingBookCategory, ingredient1, stack, aFloat, integer, ResidueInfo.NONE, clibanoFireType)));

        @Override
        public @NotNull Codec<ClibanoRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull ClibanoRecipe fromNetwork(@NotNull FriendlyByteBuf buffer) {
            String s = buffer.readUtf();
            CookingBookCategory category = buffer.readEnum(CookingBookCategory.class);
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack itemstack = buffer.readItem();
            float f = buffer.readFloat();
            int i = buffer.readVarInt();

            ClibanoFireType fireType = ClibanoFireType.CODEC.byName(buffer.readUtf(), ClibanoFireType.FIRE);

            return new ClibanoRecipe(s, category, ingredient, itemstack, f, i, ResidueInfo.fromNetwork(buffer), fireType);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, @NotNull ClibanoRecipe recipe) {
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
