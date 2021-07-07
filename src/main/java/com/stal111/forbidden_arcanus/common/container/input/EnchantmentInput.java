package com.stal111.forbidden_arcanus.common.container.input;

import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.common.tile.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Random;

/**
 * Enchantment Input
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.input.EnchantmentInput
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-07
 */
public class EnchantmentInput implements IHephaestusForgeInput {

    @Override
    public boolean canInput(InputType inputType, ItemStack stack) {
        return inputType == InputType.EXPERIENCE && stack.isEnchanted();
    }

    @Override
    public int getInputValue(InputType inputType, ItemStack stack, Random random) {
        int xp = this.getEnchantmentXp(stack);

        if (xp <= 0) {
            return 0;
        }

        int i = (int) Math.ceil((double) xp / 2.0D);
        return i + random.nextInt(i);
    }

    @Override
    public void finishInput(InputType inputType, ItemStack stack, HephaestusForgeTileEntity tileEntity, int slot) {
        tileEntity.setInventorySlotContents(slot, ItemStackUtils.removeEnchantments(stack));
    }

    private int getEnchantmentXp(ItemStack stack) {
        int xp = 0;
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);

        for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            Integer integer = entry.getValue();
            if (!enchantment.isCurse()) {
                xp += enchantment.getMinEnchantability(integer);
            }
        }

        return xp;
    }
}
