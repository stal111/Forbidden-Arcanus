package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * @author stal111
 * @since 2021-12-11
 */
@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ColorEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : DyedItemColor.getOrDefault(stack, -6265536), ModItems.MORTEM_HELMET.get(), ModItems.MORTEM_CHESTPLATE.get(), ModItems.MORTEM_LEGGINGS.get(), ModItems.MORTEM_BOOTS.get());
    }
}
