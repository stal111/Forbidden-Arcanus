package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerCache;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

/**
 * Datapack Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.DatapackEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-21
 */
@Mod.EventBusSubscriber
public class DatapackEvents {

    @SubscribeEvent
    public static void onTagsUpdate(TagsUpdatedEvent event) {
        FARegistries.ITEM_MODIFIER_REGISTRY.forEach(ItemModifier::clearCachedValidItems);

        EnhancerCache.cacheEnhancers(event.getRegistryAccess());
    }
}
