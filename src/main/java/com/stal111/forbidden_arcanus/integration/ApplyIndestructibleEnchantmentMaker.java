package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.resources.ResourceLocation;
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

    public static List<UpgradeRecipe> getRecipes() {
        List<UpgradeRecipe> recipes = new ArrayList<>();
        Ingredient eternalStella = Ingredient.of(ModItems.ETERNAL_STELLA.get());

        ForgeRegistries.ITEMS.getValues().stream().filter(Item::canBeDepleted).forEach(item -> {
            ItemStack stack = new ItemStack(item);
            if (ModTags.Items.INDESTRUCTIBLE_BLACKLISTED.contains(item) || !stack.isEnchantable()) {
                return;
            }

            ResourceLocation id = new ResourceLocation(ForbiddenArcanus.MOD_ID, "jei.apply_indestructible_enchantment." + item.getDescriptionId());

            stack.enchant(ModEnchantments.INDESTRUCTIBLE.get(), 1);

            recipes.add(new UpgradeRecipe(id, Ingredient.of(item), eternalStella, stack));
        });

        return recipes;
    }
}
