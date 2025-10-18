package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeLevel;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public record HephaestusForgeState(
        HephaestusForgeLevel tier,
        ItemStack mainItem,
        Collection<ItemStack> pedestalItems,
        HolderSet<EnhancerDefinition> enhancers,
        EssenceSet essenceSet
) {
}
