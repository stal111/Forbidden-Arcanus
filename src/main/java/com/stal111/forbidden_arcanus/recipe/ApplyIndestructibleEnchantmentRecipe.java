package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Apply Indestructible Enchantment Recipe <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.recipe.ApplyIndestructibleEnchantmentRecipe
 *
 * @author stal111
 * @version 2.0.0
 */
public class ApplyIndestructibleEnchantmentRecipe extends UpgradeRecipe {

    public ApplyIndestructibleEnchantmentRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.of(ModItems.ETERNAL_STELLA.get()), ItemStack.EMPTY);
    }

    @Override
    public boolean matches(Container inv, @Nonnull Level world) {
        ItemStack input = inv.getItem(0);

        List<Enchantment> enchantments = new ArrayList<>(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED.getValues());
        enchantments.add(ModEnchantments.INDESTRUCTIBLE.get());

        if (!input.isDamageableItem() || !input.isEnchantable() || ItemStackUtils.hasStackEnchantment(input, enchantments) || ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(input.getItem())) {
            return false;
        }
        return this.isAdditionIngredient(inv.getItem(1));
    }

    @Nonnull
    @Override
    public ItemStack assemble(Container inv) {
        ItemStack stack = inv.getItem(0).copy();
        stack.enchant(ModEnchantments.INDESTRUCTIBLE.get(), 1);

        if (EnchantmentConfig.INDESTRUCTIBLE_REPAIR_ITEM.get()) {
            CompoundTag compound = stack.getOrCreateTag();
            compound.putBoolean("Repair", true);
        }

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_INDESTRUCTIBLE_ENCHANTMENT.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }
}