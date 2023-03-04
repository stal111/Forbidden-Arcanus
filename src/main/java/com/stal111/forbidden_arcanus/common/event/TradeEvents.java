package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.villager.FAVillagerTrades;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author stal111
 * @since 2023-03-04
 */
public class TradeEvents {

    @SubscribeEvent
    public void addWanderingTraderTrades(WandererTradesEvent event) {
        FAVillagerTrades.INSTANCE.getWanderingTraderTrades().forEach(itemListing -> {
            event.getGenericTrades().add(itemListing);
        });
    }
}
