package com.stal111.forbidden_arcanus.common.block.entity.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stal111
 * @since 01.06.2024
 */
public class ForgeDataCache {

    private final HashMap<BlockPos, ItemStack> cachedIngredients = new HashMap<>();
    private ItemStack mainIngredient = ItemStack.EMPTY;

    public Map<BlockPos, ItemStack> getCachedIngredients() {
        return this.cachedIngredients;
    }

    public ItemStack getMainIngredient() {
        return this.mainIngredient;
    }

    public void setMainIngredient(ItemStack mainIngredient) {
        this.mainIngredient = mainIngredient;
    }
}
