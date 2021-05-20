package com.stal111.forbidden_arcanus.integration;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.NBTIngredient;

/**
 * Aureal Bottle Recipe Maker
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.integration.AurealBottleRecipeMaker
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-05-20
 */
public class AurealBottleRecipeMaker {

    public static ShapedRecipe createAurealBottleRecipe() {
        NonNullList<Ingredient> list = NonNullList.withSize(9, Ingredient.fromItems(ModItems.ARCANE_CRYSTAL_DUST.get()));
        list.set(4, NBTIngredient.fromStacks(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION)));
        return new ShapedRecipe(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aureal_bottle"), "", 3, 3, list, new ItemStack(NewModItems.AUREAL_BOTTLE.get()));
    }
}
