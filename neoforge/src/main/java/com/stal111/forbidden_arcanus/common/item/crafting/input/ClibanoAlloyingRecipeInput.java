package com.stal111.forbidden_arcanus.common.item.crafting.input;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.material.MoltenMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record ClibanoAlloyingRecipeInput(List<MoltenMaterial> materials) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 0;
    }
}
