package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ApplyIndestructibleEnchantmentMaker {

    public static List<ShapelessRecipe> getRecipes() {
        List<ShapelessRecipe> recipes = new ArrayList<>();
        List<Item> list = new ArrayList<>();

        String group = Main.MOD_ID + ".apply_indestructible_enchantment";

        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item.isDamageable()) {
                list.add(item);
            }
        });

        Ingredient eternalStella = Ingredient.fromItems(ModItems.ETERNAL_STELLA.get());

        list.forEach(item -> {
            ResourceLocation id = new ResourceLocation(Main.MOD_ID, "jei.apply_indestructible_enchantment." + item.getTranslationKey());

            ItemStack output = new ItemStack(item);
            output.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

            ShapelessRecipe recipe = new ShapelessRecipe(id, group, output, NonNullList.from(Ingredient.EMPTY, Ingredient.fromItems(item), eternalStella));

            recipes.add(recipe);
        });

        return recipes;
    }
}
