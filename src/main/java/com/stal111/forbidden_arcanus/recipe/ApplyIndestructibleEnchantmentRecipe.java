package com.stal111.forbidden_arcanus.recipe;

import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModRecipeSerializers;
import com.stal111.forbidden_arcanus.item.EternalStellaItem;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApplyIndestructibleEnchantmentRecipe extends SpecialRecipe {

    public ApplyIndestructibleEnchantmentRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        boolean foundDamageableItem = false;
        boolean foundEternalStella = false;

        List<Enchantment> enchantments = getEnchantmentsFromStringList(EnchantmentConfig.INDESTRUCTIBLE_ENCHANTMENT_BLACKLIST.get());
        enchantments.add(ModEnchantments.INDESTRUCTIBLE.get());

        for (int slot = 0; slot < inv.getSizeInventory(); slot++) {
            ItemStack stack = inv.getStackInSlot(slot);

            if (stack.isDamageable() && !foundDamageableItem && !ItemStackUtils.hasStackEnchantment(stack, enchantments)) {
                if (!EnchantmentConfig.INDESTRUCTIBLE_ITEM_BLACKLIST.get().contains(Objects.requireNonNull(stack.getItem().getRegistryName()).toString())) {
                    foundDamageableItem = true;
                }
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

    private List<Enchantment> getEnchantmentsFromStringList(List<? extends String> list) {
        List<Enchantment> enchantments = new ArrayList<>();

        list.forEach(s -> enchantments.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(s))));

        return enchantments;
    }
}
