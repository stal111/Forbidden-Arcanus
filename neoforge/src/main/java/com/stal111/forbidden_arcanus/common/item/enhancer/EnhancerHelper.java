package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.stal111.forbidden_arcanus.common.essence.EssenceModifier;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;

import java.util.List;
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

    public static List<EssenceModifier> getEssenceModifiers(HolderSet<EnhancerDefinition> enhancers, EnhancerTarget target) {
        return enhancers.stream()
                .flatMap(enhancerDefinition -> enhancerDefinition.value().getEffects(target))
                .filter(effect -> effect instanceof EssenceModifier)
                .map(effect -> (EssenceModifier) effect)
                .toList();
    }
}
