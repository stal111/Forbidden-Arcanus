package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.item.EternalStellaItem;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ApplyIndestructibleEnchantmentRecipe extends SpecialRecipe {

    public ApplyIndestructibleEnchantmentRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean foundDamageableItem = false;
        boolean foundEternalStella = false;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);

            if (stack.isDamageable() && !foundDamageableItem && !ItemStackUtils.hasStackEnchantment(stack, ModEnchantments.INDESTRUCTIBLE.get(), Enchantments.UNBREAKING, Enchantments.MENDING)) {
                foundDamageableItem = true;
            } else if (stack.getItem() instanceof EternalStellaItem && !foundEternalStella) {
                foundEternalStella = true;
            } else if (!stack.isEmpty()) {
                return false;
            }

        }
        return foundDamageableItem && foundEternalStella;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        ItemStack stack = ItemStack.EMPTY;

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stackInSlot = inv.getStackInSlot(slot);

            if (stackInSlot.isDamageable()) {
                stack = stackInSlot.copy();
                break;
            }

        }

        stack.addEnchantment(ModEnchantments.INDESTRUCTIBLE.get(), 1);

        return stack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.APPLY_INDESTRUCTIBLE_ENCHANTMENT.get();
    }
}
