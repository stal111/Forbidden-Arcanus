package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ApplyIndestructibleEnchantmentMaker {

    public static List<SmithingRecipe> getRecipes() {
        List<SmithingRecipe> recipes = new ArrayList<>();
        List<Item> list = new ArrayList<>();

        String group = ForbiddenArcanus.MOD_ID + ".apply_indestructible_enchantment";

        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item.isDamageable()) {
                list.add(item);
            }
        });

        Ingredient eternalStella = Ingredient.fromItems(ModItems.ETERNAL_STELLA.get());

        list.forEach(item -> {
            if (!ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(item)) {
                ResourceLocation id = new ResourceLocation(ForbiddenArcanus.MOD_ID, "jei.apply_indestructible_enchantment." + item.getTranslationKey());

                ItemStack output = new ItemStack(item);
                output.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

                SmithingRecipe recipe = new SmithingRecipe(id, Ingredient.fromItems(item), eternalStella, output);

                recipes.add(recipe);
            }
        });

        return recipes;
    }
}
