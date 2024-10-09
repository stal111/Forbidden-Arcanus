package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 01.06.2024
 */
public record ForgeDataCache(HashMap<BlockPos, ItemStack> cachedIngredients, ItemStack mainIngredient, Int2ObjectArrayMap<Holder<EnhancerDefinition>> enhancers) {

    public static final ForgeDataCache EMPTY = new ForgeDataCache(new HashMap<>(), ItemStack.EMPTY, new Int2ObjectArrayMap<>());

    public ForgeDataCache setMainIngredient(ItemStack mainIngredient) {
        return new ForgeDataCache(this.cachedIngredients, mainIngredient, this.enhancers);
    }

    public HolderSet<EnhancerDefinition> getEnhancers() {
        return HolderSet.direct(UnaryOperator.identity(), this.enhancers.values());
    }
}
