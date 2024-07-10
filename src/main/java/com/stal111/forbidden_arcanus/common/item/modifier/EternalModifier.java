package com.stal111.forbidden_arcanus.common.item.modifier;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * @author stal111
 * @since 2021-11-25
 */
public class EternalModifier extends ItemModifier {

    public EternalModifier(ItemPredicate predicate, TagKey<Item> incompatibleItems, TagKey<Enchantment> incompatibleEnchantments, int startTooltipColor, int endTooltipColor) {
        super(predicate, incompatibleItems, incompatibleEnchantments, startTooltipColor, endTooltipColor);
    }

    @Override
    public void onApplied(ItemStack stack) {
        super.onApplied(stack);

        stack.remove(DataComponents.DAMAGE);
        stack.remove(DataComponents.MAX_DAMAGE);
    }
}
