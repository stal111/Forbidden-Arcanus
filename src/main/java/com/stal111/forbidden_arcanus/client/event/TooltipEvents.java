package com.stal111.forbidden_arcanus.client.event;

import com.mojang.datafixers.util.Either;
import com.stal111.forbidden_arcanus.client.tooltip.EdelwoodBucketTooltip;
import com.stal111.forbidden_arcanus.common.item.CapacityBucket;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Tooltip Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.TooltipEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-25
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TooltipEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(RenderTooltipEvent.Color event) {
        ItemStack stack = event.getStack();
        ItemModifier modifier = ModifierHelper.getModifier(stack);

        if (modifier != null) {
            event.setBorderStart(modifier.getTooltipColors().getFirst());
            event.setBorderEnd(modifier.getTooltipColors().getSecond());
        }
    }

    @SubscribeEvent
    public static void onGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getStack();

        if (stack.getItem() instanceof CapacityBucket capacityBucket && !capacityBucket.getEmptyBucket().is(stack.getItem())) {
            event.getTooltipElements().add(1, Either.right(new EdelwoodBucketTooltip(stack, capacityBucket.getFullness(stack), capacityBucket.getCapacity())));
        }
    }
}
