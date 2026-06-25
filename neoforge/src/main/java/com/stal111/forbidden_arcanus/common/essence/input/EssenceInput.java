package com.stal111.forbidden_arcanus.common.essence.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 2023-05-24
 */
public interface EssenceInput {

    static Optional<EssenceInput> findValidInput(ItemStack stack, EssenceType essenceType) {
        return FARegistries.ESSENCE_INPUT_REGISTRY.listElements()
                .map(Holder.Reference::value)
                .filter(forgeInput -> forgeInput.isValidInput(stack, essenceType))
                .findFirst();
    }

    default boolean isValidInput(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type) > 0;
    }

    int getAmount(ItemStack stack, EssenceType type);

    default int getMaxAmount(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type);
    }

    ItemStack finishInput(ItemStack stack, int inputAmount);
}
