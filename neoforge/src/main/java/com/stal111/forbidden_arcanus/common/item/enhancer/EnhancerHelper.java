package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Optional;

/**
 * @author stal111
 * @since 14.05.2024
 */
public class EnhancerHelper {

    public static Optional<EnhancerDefinition> getEnhancer(ItemStack stack) {
        return getEnhancerHolder(stack).map(Holder::value);
    }

    public static Optional<Holder<EnhancerDefinition>> getEnhancerHolder(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ENHANCER));
    }

    public static ItemStack createEnhancerItem(HolderLookup.RegistryLookup<EnhancerDefinition> registryLookup, ItemLike item, ResourceKey<EnhancerDefinition> enhancer) {
        ItemStack stack = item.asItem().getDefaultInstance();

        registryLookup.get(enhancer).ifPresent(enhancerHolder -> {
            stack.set(ModDataComponents.ENHANCER.get(), enhancerHolder);
        });

        return stack;
    }
}
