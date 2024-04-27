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

    EssenceType getType();
    int getLimit();

    default int getEssence(ItemStack stack) {
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        return data != null && data.type() == this.getType() ? data.amount() : 0;
    }

    default void setEssence(ItemStack stack, int amount) {
        stack.set(ModDataComponents.ESSENCE_DATA, new EssenceData(this.getType(), amount, this.getLimit()));
    }

    default void addEssence(ItemStack stack, int amount) {
        this.setEssence(stack, Math.min(this.getEssence(stack) + amount, this.getLimit()));
    }

    default boolean isEmpty(ItemStack stack) {
        return this.getEssence(stack) <= 0;
    }

    default boolean isFull(ItemStack stack) {
        return this.getEssence(stack) >= this.getLimit();
    }

    default Optional<ItemStack> getEmptyStack() {
        return Optional.empty();
    }
}
