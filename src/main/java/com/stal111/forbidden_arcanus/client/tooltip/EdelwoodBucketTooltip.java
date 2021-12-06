package com.stal111.forbidden_arcanus.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

/**
 * Edelwood Bucket Tooltip <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.tooltip.EdelwoodBucketTooltip
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-06
 */
public record EdelwoodBucketTooltip(ItemStack filledBucket, int fullness, int capacity) implements TooltipComponent {
}
