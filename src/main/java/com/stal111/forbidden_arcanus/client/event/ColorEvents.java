package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Color Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.ColorEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-11
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ColorEvents {

    @SubscribeEvent
    public static void onRenderTooltipColor(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        colors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), ModItems.MORTEM_HELMET.get(), ModItems.MORTEM_CHESTPLATE.get(), ModItems.MORTEM_LEGGINGS.get(), ModItems.MORTEM_BOOTS.get());
    }
}
