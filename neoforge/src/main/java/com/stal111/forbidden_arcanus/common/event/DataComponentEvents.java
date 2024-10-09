package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;

/**
 * @author stal111
 * @since 07.05.2024
 */
public class DataComponentEvents {

    @SubscribeEvent
    public void modifyComponents(ModifyDefaultComponentsEvent event) {
        event.modify(Items.EXPERIENCE_BOTTLE, builder -> builder.set(ModDataComponents.ESSENCE_DATA.get(), EssenceData.of(EssenceType.EXPERIENCE, 15)));
    }
}
