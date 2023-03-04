package com.stal111.forbidden_arcanus.common.entity.villager;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BasicItemListing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stal111
 * @since 2023-03-04
 */
public final class FAVillagerTrades {

    public static final FAVillagerTrades INSTANCE = new FAVillagerTrades();

    private final List<VillagerTrades.ItemListing> wanderingTraderTrades = new ArrayList<>();

    private FAVillagerTrades() {
        this.buildTrades();
    }

    private void buildTrades() {
        this.addBasicTrade(3, new ItemStack(ModBlocks.FUNGYSS.get()), 5, 1);
        this.addBasicTrade(5, new ItemStack(ModBlocks.CHERRY_SAPLING.get()), 8, 1);
        this.addBasicTrade(5, new ItemStack(ModBlocks.AURUM_SAPLING.get()), 8, 1);
        this.addBasicTrade(6, new ItemStack(ModBlocks.GROWING_EDELWOOD.get()), 8, 1);
        this.addBasicTrade(2, new ItemStack(ModBlocks.YELLOW_ORCHID.get()), 6, 1);
        this.addBasicTrade(8, new ItemStack(ModItems.ARTISAN_RELIC.get()), 2, 1);
    }

    private void addBasicTrade(int emeralds, ItemStack stack, int maxTrades, int experience) {
        this.wanderingTraderTrades.add(new BasicItemListing(emeralds, stack, maxTrades, experience));
    }

    public List<VillagerTrades.ItemListing> getWanderingTraderTrades() {
        return this.wanderingTraderTrades;
    }
}
