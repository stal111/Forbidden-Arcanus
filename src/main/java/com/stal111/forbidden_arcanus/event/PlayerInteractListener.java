package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerInteractListener {

	@SubscribeEvent
	public static void onBlockActivated(PlayerInteractEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getEntityPlayer();
		if (block == Blocks.FLOWER_POT) {
			if (stack.getItem() == ModBlocks.cherrywood_sapling.asItem()) {
				world.setBlockState(pos, ModBlocks.potted_cherrywood_sapling.getDefaultState(), 3);
				player.addStat(Stats.POT_FLOWER);
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
			} else if (stack.getItem() == ModBlocks.mysterywood_sapling.asItem()) {
				world.setBlockState(pos, ModBlocks.potted_mysterywood_sapling.getDefaultState(), 3);
				player.addStat(Stats.POT_FLOWER);
				if (!player.abilities.isCreativeMode) {
					stack.shrink(1);
				}
			}
		} else if (block == ModBlocks.potted_cherrywood_sapling) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.cherrywood_sapling))) {
				player.dropItem(new ItemStack(ModBlocks.cherrywood_sapling), false);
			}
		} else if (block == ModBlocks.potted_mysterywood_sapling) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.mysterywood_sapling))) {
				player.dropItem(new ItemStack(ModBlocks.mysterywood_sapling), false);
			}
		}
	}

}
