package com.stal111.forbidden_arcanus.common.item.modifier;

import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * Helper class for managing item modifiers.
 *
 * @author stal111
 * @since 2021-11-24
 */
public class ModifierHelper {

    /**
     * Retrieves the modifier of a given item stack.
     *
     * @param stack The item stack to retrieve the modifier from.
     * @return An Optional containing the ItemModifier if present, otherwise an empty Optional.
     */
    public static Optional<ItemModifier> getModifier(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ITEM_MODIFIER)).map(Holder::value);
    }

    /**
     * Sets the modifier of a given item stack.
     *
     * @param stack    The item stack to set the modifier for.
     * @param modifier The modifier to set on the item stack.
     */
    public static void setModifier(ItemStack stack, Holder<ItemModifier> modifier) {
        stack.set(ModDataComponents.ITEM_MODIFIER, modifier);

        modifier.value().onApplied(stack);
    }

    /**
     * Checks if a given item stack has a modifier.
     *
     * @param stack The item stack to check.
     * @return True if the item stack has a modifier, otherwise false.
     */
    public static boolean hasModifier(ItemStack stack) {
        return getModifier(stack).isPresent();
    }

    /**
     * Checks if a given item stack has a specific modifier.
     *
     * @param stack    The item stack to check.
     * @param modifier The modifier to check for.
     * @return True if the item stack has the specified modifier, otherwise false.
     */
    public static boolean hasModifier(ItemStack stack, ItemModifier modifier) {
        return getModifier(stack).map(itemModifier -> itemModifier == modifier).orElse(false);
    }
}
