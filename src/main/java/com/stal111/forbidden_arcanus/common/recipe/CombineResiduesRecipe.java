package com.stal111.forbidden_arcanus.common.recipe;

import com.google.gson.JsonObject;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-07-05
 */
public class CombineResiduesRecipe extends CustomRecipe {

    private final String residue;
    private final short residueAmount;
    private final ItemStack result;

    public CombineResiduesRecipe(ResourceLocation id, CraftingBookCategory category, String residue, short residueAmount, ItemStack result) {
        super(id, category);
        this.residue = residue;
        this.residueAmount = residueAmount;
        this.result = result;
    }

    @Override
    public boolean matches(@Nonnull CraftingContainer container, @Nonnull Level level) {
        return false;
    }

    @Nonnull
    @Override
    public ItemStack assemble(@Nonnull CraftingContainer container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    public String getResidue() {
        return this.residue;
    }

    public short getResidueAmount() {
        return this.residueAmount;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Nonnull
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COMBINE_RESIDUES.get();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return this.result;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.COMBINE_RESIDUES_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<CombineResiduesRecipe> {
        @Nonnull
        @Override
        public CombineResiduesRecipe fromJson(@Nonnull ResourceLocation recipeId, @Nonnull JsonObject json) {
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);

            String residueName = GsonHelper.getAsString(json, "residue_name");
            short residueAmount = GsonHelper.getAsShort(json, "residue_amount");
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            return new CombineResiduesRecipe(recipeId, category, residueName, residueAmount, result);
        }

        @Override
        public CombineResiduesRecipe fromNetwork(@Nonnull ResourceLocation recipeId, @Nonnull FriendlyByteBuf buffer) {
            return new CombineResiduesRecipe(recipeId, buffer.readEnum(CraftingBookCategory.class), buffer.readUtf(), buffer.readShort(), buffer.readItem());
        }

        @Override
        public void toNetwork(@Nonnull FriendlyByteBuf buffer, CombineResiduesRecipe recipe) {
            buffer.writeEnum(recipe.category());
            buffer.writeUtf(recipe.residue);
            buffer.writeShort(recipe.residueAmount);
            buffer.writeItem(recipe.result);
        }
    }
}
