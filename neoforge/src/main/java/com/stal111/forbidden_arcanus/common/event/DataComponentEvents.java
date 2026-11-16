package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

/**
 * @author stal111
 * @since 07.05.2024
 */
@EventBusSubscriber
public class DataComponentEvents {

    @SubscribeEvent
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.EXPERIENCE_BOTTLE, (builder, _, _) -> builder.set(ModDataComponents.ESSENCE_VALUE.get(), EssenceValue.of(EssenceType.EXPERIENCE, 15)));
    }
}
