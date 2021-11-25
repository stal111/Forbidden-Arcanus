package com.stal111.forbidden_arcanus.common.item.modifier;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Modifier Helper <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-24
 */
public class ModifierHelper {

    @Nullable
    public static ItemModifier getModifier(ItemStack stack) {
        if (stack.isEmpty() || !stack.hasTag()) {
            return null;
        }

        return ForbiddenArcanus.ITEM_MODIFIER_REGISTRY.get().getValue(new ResourceLocation(Objects.requireNonNull(stack.getTag()).getString("Modifier")));
    }

    public static void setModifier(ItemStack stack, ItemModifier modifier) {
        stack.getOrCreateTag().putString("Modifier", Objects.requireNonNull(modifier.getRegistryName()).toString());

        modifier.onApplied(stack);
    }
}
