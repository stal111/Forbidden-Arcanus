package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SmithingRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplyIndestructibleEnchantmentMaker {

    public static List<SmithingRecipe> getRecipes() {
        List<SmithingRecipe> recipes = new ArrayList<>();
        List<Item> list = new ArrayList<>();

        String group = Main.MOD_ID + ".apply_indestructible_enchantment";

        ForgeRegistries.ITEMS.getValues().forEach(item -> {
            if (item.isDamageable()) {
                list.add(item);
            }
        });

        Ingredient eternalStella = Ingredient.fromItems(ModItems.ETERNAL_STELLA.get());

        list.forEach(item -> {
            if (!(EnchantmentConfig.INDESTRUCTIBLE_ITEM_BLACKLIST.get().contains(Objects.requireNonNull(item.getRegistryName()).toString()))) {
                ResourceLocation id = new ResourceLocation(Main.MOD_ID, "jei.apply_indestructible_enchantment." + item.getTranslationKey());

                ItemStack output = new ItemStack(item);
                output.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

                SmithingRecipe recipe = new SmithingRecipe(id, Ingredient.fromItems(item), eternalStella, output);

                recipes.add(recipe);
            }
        });

        return recipes;
    }
}
