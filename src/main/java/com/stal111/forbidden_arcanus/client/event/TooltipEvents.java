package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
    public static void onRenderTooltipPostText(ItemTooltipEvent event) {
        if (ModifierHelper.getModifier(event.getItemStack()) != null) {
            //TODO
        }
    }
}
