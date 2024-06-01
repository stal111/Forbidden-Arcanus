package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stal111
 * @since 01.06.2024
 */
public class ForgeDataCache {

    private final HashMap<BlockPos, ItemStack> cachedIngredients = new HashMap<>();
    private final List<Holder<EnhancerDefinition>> enhancers = new ArrayList<>();
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

    public void addEnhancer(Holder<EnhancerDefinition> enhancer) {
        this.enhancers.add(enhancer);
    }

    public HolderSet<EnhancerDefinition> getEnhancers() {
        return HolderSet.direct(this.enhancers);
    }
}
