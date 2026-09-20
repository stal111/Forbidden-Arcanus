package com.stal111.forbidden_arcanus.common.item.crafting.input;

import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.function.IntConsumer;

public record EssenceTransformInput(ItemStack item, EssenceValue essenceValue, IntConsumer consumeEssence) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No item for index " + index);
        } else {
            return this.item;
        }
    }

    @Override
    public int size() {
        return 1;
    }
}
