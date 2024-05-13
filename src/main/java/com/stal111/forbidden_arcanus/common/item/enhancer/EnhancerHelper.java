package com.stal111.forbidden_arcanus.common.item.enhancer;

import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 14.05.2024
 */
public class EnhancerHelper {

    public static Optional<EnhancerDefinition> getEnhancer(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ENHANCER)).map(Holder::value);
    }
}
