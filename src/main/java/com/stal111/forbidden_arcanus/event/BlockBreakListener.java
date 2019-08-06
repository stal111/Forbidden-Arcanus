package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BlockBreakListener {

	@SubscribeEvent
	public static void onBlockBrocken(BlockEvent.BreakEvent event) {
		World world = event.getWorld().getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();
		if (block == ModBlocks.potted_cherrywood_sapling) {
			world.addEntity(new ItemEntity(world.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.cherrywood_sapling)));
		} else if (block == ModBlocks.potted_mysterywood_sapling) {
			world.addEntity(new ItemEntity(world.getWorld(), pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.mysterywood_sapling)));
		}

	}

}
