package com.stal111.forbidden_arcanus.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2021-12-06
 */
public record CapacityBucketTooltip(ItemStack filledBucket, int fullness, int capacity) implements TooltipComponent {
}
