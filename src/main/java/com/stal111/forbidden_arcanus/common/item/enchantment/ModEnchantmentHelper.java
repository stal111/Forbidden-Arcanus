package com.stal111.forbidden_arcanus.common.item.enchantment;

import com.stal111.forbidden_arcanus.core.init.ModEnchantmentDataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ModEnchantmentHelper {

    public static float getLostSoulSpawnChance(ServerLevel level, ItemStack stack) {
        MutableFloat mutableValue = new MutableFloat(0.0F);
        EnchantmentHelper.runIterationOnItem(
                stack, (enchantment, enchantmentLevel) -> enchantment.value().modifyItemFilteredCount(ModEnchantmentDataComponents.LOST_SOUL_SPAWN_CHANCE.get(), level, enchantmentLevel, stack, mutableValue)
        );
        return mutableValue.floatValue();
    }
}
