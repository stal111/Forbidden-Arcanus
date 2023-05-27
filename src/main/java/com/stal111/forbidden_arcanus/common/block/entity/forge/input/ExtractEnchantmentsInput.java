package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModForgeInputTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import java.util.Map;

/**
 * @author stal111
 * @since 2021-07-07
 */
public class ExtractEnchantmentsInput extends HephaestusForgeInput {

    public static final Codec<ExtractEnchantmentsInput> CODEC = Codec.unit(ExtractEnchantmentsInput::new);

    public ExtractEnchantmentsInput() {
        super(EssenceType.EXPERIENCE);
    }

    @Override
    public boolean additionalInputChecks(ItemStack stack) {
        return stack.isEnchanted();
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        int xp = this.getEnchantmentXp(stack);

        if (xp <= 0) {
            return 0;
        }

        int i = (int) Math.ceil((double) xp / 2.0D);

        return i + random.nextInt(i);
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            tileEntity.setStack(slot, ItemStackUtils.removeEnchantments(stack));
        }
    }

    @Override
    public HephaestusForgeInputType<?> type() {
        return ModForgeInputTypes.EXTRACT_ENCHANTMENTS.get();
    }

    private int getEnchantmentXp(ItemStack stack) {
        int xp = 0;
        Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack);

        for(Map.Entry<Enchantment, Integer> entry : map.entrySet()) {
            Enchantment enchantment = entry.getKey();
            Integer integer = entry.getValue();
            if (!enchantment.isCurse()) {
                xp += enchantment.getMinCost(integer);
            }
        }

        return xp;
    }
}
