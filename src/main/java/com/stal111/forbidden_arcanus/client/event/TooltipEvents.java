package com.stal111.forbidden_arcanus.client.event;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.client.tooltip.CapacityBucketTooltip;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityBucket;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

/**
 * Tooltip Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.TooltipEvents
 *
 * @author stal111
 * @since 2021-11-25
 */
@EventBusSubscriber(value = Dist.CLIENT)
public class TooltipEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getItemStack();

        ModifierHelper.getModifier(stack).map(modifier -> modifier.displaySettings().tooltipColor()).ifPresent(color -> {
            event.setBorderStart(color.getFirst());
            event.setBorderEnd(color.getSecond());
        });
    }

    @SubscribeEvent
    public static void onGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();

        if (stack.getItem() instanceof CapacityBucket capacityBucket && capacityBucket.getCapacity() != 0) {
            event.getTooltipElements().add(1, Either.right(new CapacityBucketTooltip(stack, capacityBucket.getFullness(stack), capacityBucket.getCapacity())));
        }
    }
}
