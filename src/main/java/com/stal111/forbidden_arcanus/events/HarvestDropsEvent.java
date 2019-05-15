package com.stal111.forbidden_arcanus.events;

import com.stal111.forbidden_arcanus.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HarvestDropsEvent {
	
	@SubscribeEvent
	public static void onBlockDestroyed(BlockEvent.HarvestDropsEvent e) {
		Block block = e.getState().getBlock();
		if (e.getHarvester() != null && e.getHarvester().getHeldItemMainhand().getItem() == ModItems.infernum_pickaxe) {
			if (block == Blocks.IRON_ORE) {
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(Items.IRON_INGOT));
			} else if (block == Blocks.GOLD_ORE) {
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(Items.GOLD_INGOT));
			} else if (block == Blocks.WET_SPONGE) {
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(Blocks.SPONGE));
			}
		}
	}

}
