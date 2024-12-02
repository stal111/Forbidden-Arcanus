package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 01.06.2024
 */
public record ForgeDataCache(Map<BlockPos, ItemStack> cachedIngredients, ItemStack mainIngredient, Map<Integer, Holder<EnhancerDefinition>> enhancers) {

    public static final Codec<ForgeDataCache> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.unboundedMap(BlockPos.CODEC, ItemStack.CODEC).fieldOf("ingredients").forGetter(ForgeDataCache::cachedIngredients),
            ItemStack.CODEC.fieldOf("main_ingredient").forGetter(ForgeDataCache::mainIngredient),
            Codec.unboundedMap(Codec.INT, EnhancerDefinition.REFERENCE_CODEC).fieldOf("enhancers").forGetter(ForgeDataCache::enhancers)
    ).apply(instance, ForgeDataCache::new));

    public static final ForgeDataCache EMPTY = new ForgeDataCache(new HashMap<>(), ItemStack.EMPTY, new HashMap<>());

    public ForgeDataCache setMainIngredient(ItemStack mainIngredient) {
        return new ForgeDataCache(this.cachedIngredients, mainIngredient, this.enhancers);
    }

    public HolderSet<EnhancerDefinition> getEnhancers() {
        return HolderSet.direct(UnaryOperator.identity(), this.enhancers.values());
    }
}
