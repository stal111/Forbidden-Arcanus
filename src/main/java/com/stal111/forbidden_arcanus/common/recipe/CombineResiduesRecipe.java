package com.stal111.forbidden_arcanus.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.core.init.ModRecipeTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-07-05
 */
public class CombineResiduesRecipe extends CustomRecipe {

    private final String residue;
    private final short residueAmount;
    private final ItemStack result;

    public CombineResiduesRecipe(CraftingBookCategory category, String residue, short residueAmount, ItemStack result) {
        super(category);
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
    public ItemStack assemble(@Nonnull CraftingContainer container, @NotNull RegistryAccess registryAccess) {
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
        return ModRecipeTypes.COMBINE_RESIDUES.get();
    }

    @Nonnull
    @Override
    public ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return this.result;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.COMBINE_RESIDUES_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<CombineResiduesRecipe> {

        private static final Codec<CombineResiduesRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CustomRecipe::category),
                Codec.STRING.fieldOf("residue_name").forGetter(recipe -> {
                    return recipe.residue;
                }),
                Codec.SHORT.fieldOf("residue_amount").forGetter(recipe -> {
                    return recipe.residueAmount;
                }),
                BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem).fieldOf("result").forGetter(recipe -> {
                    return recipe.result;
                })
        ).apply(instance, CombineResiduesRecipe::new));

        @Override
        public @NotNull Codec<CombineResiduesRecipe> codec() {
            return CODEC;
        }

        @Override
        public @Nullable CombineResiduesRecipe fromNetwork(FriendlyByteBuf buffer) {
            return new CombineResiduesRecipe(buffer.readEnum(CraftingBookCategory.class), buffer.readUtf(), buffer.readShort(), buffer.readItem());
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
