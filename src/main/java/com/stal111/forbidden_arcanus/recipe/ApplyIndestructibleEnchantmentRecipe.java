package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

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
public class ApplyIndestructibleEnchantmentRecipe extends SmithingRecipe {

    public ApplyIndestructibleEnchantmentRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.fromItems(ModItems.ETERNAL_STELLA.get()), ItemStack.EMPTY);
    }

    @Override
    public boolean matches(IInventory inv, @Nonnull World world) {
        ItemStack input = inv.getStackInSlot(0);

        List<Enchantment> enchantments = new ArrayList<>(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED.getAllElements());
        enchantments.add(ModEnchantments.INDESTRUCTIBLE.get());

        if (!input.isDamageable() || ItemStackUtils.hasStackEnchantment(input, enchantments) || ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(input.getItem())) {
            return false;
        }
        return this.isValidAdditionItem(inv.getStackInSlot(1));
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack stack = inv.getStackInSlot(0).copy();
        stack.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

        if (EnchantmentConfig.INDESTRUCTIBLE_REPAIR_ITEM.get()) {
            CompoundNBT compound = stack.getOrCreateTag();
            compound.putBoolean("Repair", true);
        }

        return stack;
    }

    @Nonnull
    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Nonnull
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_INDESTRUCTIBLE_ENCHANTMENT.get();
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}