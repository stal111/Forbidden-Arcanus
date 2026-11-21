package com.stal111.forbidden_arcanus.client.renderer.item.properties;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.item.component.ToggleableState;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Conditional item model property that checks whether an item's {@link ToggleableState} is active.
 */
public class ToggleableActive implements ConditionalItemModelProperty {

    public static final MapCodec<ToggleableActive> MAP_CODEC = MapCodec.unit(new ToggleableActive());

    @Override
    public boolean get(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return stack.getOrDefault(ModDataComponents.TOGGLEABLE_STATE, ToggleableState.ACTIVE).active();
    }

    @Override
    public MapCodec<? extends ConditionalItemModelProperty> type() {
        return MAP_CODEC;
    }
}
