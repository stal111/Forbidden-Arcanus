package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.DyeableLeatherItem;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * @author stal111
 * @since 2021-12-11
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(RegisterColorHandlersEvent.Item event) {
        ItemColors colors = event.getItemColors();

        colors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), ModItems.MORTEM_HELMET.get(), ModItems.MORTEM_CHESTPLATE.get(), ModItems.MORTEM_LEGGINGS.get(), ModItems.MORTEM_BOOTS.get());
    }
}
