package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 26.04.2024
 */
public interface EssenceContainer {

    default EssenceData getEssenceData(ItemStack stack) {
        return stack.get(ModDataComponents.ESSENCE_DATA);
    }

    EssenceType getType(ItemStack stack);

    default int getLimit(ItemStack stack) {
        return this.getEssenceData(stack).limit();
    }

    default int getEssence(ItemStack stack) {
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        return data != null ? data.amount() : 0;
    }

    default void setEssence(ItemStack stack, int amount) {
        stack.set(ModDataComponents.ESSENCE_DATA, new EssenceData(this.getType(stack), amount, this.getLimit(stack)));
    }

    default void addEssence(ItemStack stack, int amount) {
        this.setEssence(stack, Math.min(this.getEssence(stack) + amount, this.getLimit(stack)));
    }

    default boolean isEmpty(ItemStack stack) {
        return this.getEssence(stack) <= 0;
    }

    default boolean isFull(ItemStack stack) {
        return this.getEssence(stack) >= this.getLimit(stack);
    }

    default Optional<ItemStack> getEmptyStack() {
        return Optional.empty();
    }
}
