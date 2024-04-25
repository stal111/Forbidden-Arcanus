package com.stal111.forbidden_arcanus.common.enchantment;

import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 06.10.2023
 */
public class AurealReservoirEnchantment extends Enchantment {

    private static final EnchantmentDefinition DEFINITION = Enchantment.definition(
            ModTags.Items.AUREAL_STORAGE_ENCHANTABLE,
            2,
            1,
            Enchantment.dynamicCost(25, 25),
            Enchantment.dynamicCost(75, 25),
            4,
            EquipmentSlot.values()
    );

    public AurealReservoirEnchantment() {
        super(DEFINITION);
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(@NotNull ItemStack stack) {
        return false;
    }
}
