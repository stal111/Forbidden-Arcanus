package com.stal111.forbidden_arcanus.recipe;

import com.google.common.collect.ImmutableList;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.item.EternalStellaItem;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ApplyIndestructibleEnchantmentRecipe extends SmithingRecipe {

    public ApplyIndestructibleEnchantmentRecipe(ResourceLocation recipeId) {
        super(recipeId, Ingredient.EMPTY, Ingredient.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public boolean matches(IInventory inv, World world) {
        ItemStack input = inv.getStackInSlot(0);
        ItemStack addition = inv.getStackInSlot(1);

        List<Enchantment> enchantments = new ArrayList<>(ModTags.Enchantments.INDESTRUCTIBLE_BLACKLISTED.getAllElements());
        enchantments.add(ModEnchantments.INDESTRUCTIBLE.get());

        if (input.isDamageable() && !ItemStackUtils.hasStackEnchantment(input, enchantments) && !ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(input.getItem())) {
            return addition.getItem() instanceof EternalStellaItem;
        }
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        ItemStack stack = inv.getStackInSlot(0).copy();
        stack.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

        return stack;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isValidAdditionItem(ItemStack addition) {
        return addition.getItem() instanceof EternalStellaItem;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_INDESTRUCTIBLE_ENCHANTMENT.get();
    }
}