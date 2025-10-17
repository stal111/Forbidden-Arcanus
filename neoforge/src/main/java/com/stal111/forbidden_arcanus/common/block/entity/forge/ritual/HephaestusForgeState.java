package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record HephaestusForgeState(ItemStack mainItem, List<ItemStack> pedestalItems, EssenceSet essenceSet) {
}
