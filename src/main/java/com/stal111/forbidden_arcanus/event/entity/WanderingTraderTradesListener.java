package com.stal111.forbidden_arcanus.event.entity;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.BasicTrade;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.helper.TradeHelper;

/**
 * Wandering Trader Trades Listener
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
                new BasicTrade(3, new ItemStack(NewModBlocks.FUNGYSS.get()), 5, 1),
                new BasicTrade(5, new ItemStack(ModBlocks.CHERRYWOOD_SAPLING.getBlock()), 8, 1),
                new BasicTrade(5, new ItemStack(ModBlocks.MYSTERYWOOD_SAPLING.getBlock()), 8, 1),
                new BasicTrade(2, new ItemStack(ModBlocks.YELLOW_ORCHID.getBlock()), 6, 1)
        );
    }
}
