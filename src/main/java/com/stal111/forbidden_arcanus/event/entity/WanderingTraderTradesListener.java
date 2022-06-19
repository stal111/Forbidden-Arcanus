package com.stal111.forbidden_arcanus.event.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.helper.TradeHelper;

/**
 * Wandering Trader Trades Listener <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.entity.WanderingTraderTradesListener
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-05-27
 */
@Mod.EventBusSubscriber
public class WanderingTraderTradesListener {

    @SubscribeEvent
    public static void onWanderingTraderTradesEvent(WandererTradesEvent event) {
        TradeHelper.addWanderingTraderTrades(event,
                new BasicItemListing(3, new ItemStack(ModBlocks.FUNGYSS.get()), 5, 1),
                new BasicItemListing(5, new ItemStack(ModBlocks.CHERRY_SAPLING.get()), 8, 1),
                new BasicItemListing(5, new ItemStack(ModBlocks.AURUM_SAPLING.get()), 8, 1),
                new BasicItemListing(2, new ItemStack(ModBlocks.YELLOW_ORCHID.get()), 6, 1)
        );
    }
}
