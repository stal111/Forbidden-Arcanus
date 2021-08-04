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

/**
 * Apply Indestructible Enchantment Maker <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.integration.ApplyIndestructibleEnchantmentMaker
 *
 * @author stal111
 * @version 2.0.0
 */
public class ApplyIndestructibleEnchantmentMaker {

    public static List<SmithingRecipe> getRecipes() {
        List<SmithingRecipe> recipes = new ArrayList<>();
        Ingredient eternalStella = Ingredient.fromItems(ModItems.ETERNAL_STELLA.get());

        ForgeRegistries.ITEMS.getValues().stream().filter(Item::isDamageable).forEach(item -> {
            if (ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(item)) {
                return;
            }

            ResourceLocation id = new ResourceLocation(ForbiddenArcanus.MOD_ID, "jei.apply_indestructible_enchantment." + item.getTranslationKey());
            ItemStack output = new ItemStack(item);

            output.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

            recipes.add(new SmithingRecipe(id, Ingredient.fromItems(item), eternalStella, output));
        });

        return recipes;
    }
}
