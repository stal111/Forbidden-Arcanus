package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
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
	public static void onInteract(PlayerInteractEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getEntityPlayer();

		if (block == Blocks.FLOWER_POT) {
			if (!world.isRemote) {
				if (stack.getItem() == ModBlocks.CHERRYWOOD_SAPLING.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_CHERRYWOOD_SAPLING.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				} else if (stack.getItem() ==  ModBlocks.MYSTERYWOOD_SAPLING.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_MYSTERYWOOD_SAPLING.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				} else if (stack.getItem() ==  ModBlocks.YELLOW_ORCHID.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_YELLOW_ORCHID.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				}
			}
		} else if (block == ModBlocks.POTTED_CHERRYWOOD_SAPLING.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.CHERRYWOOD_SAPLING.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.CHERRYWOOD_SAPLING.getBlock()), false);
			}
		} else if (block == ModBlocks.POTTED_MYSTERYWOOD_SAPLING.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.MYSTERYWOOD_SAPLING.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.MYSTERYWOOD_SAPLING.getBlock()), false);
			}
		} else if (block == ModBlocks.POTTED_YELLOW_ORCHID.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.YELLOW_ORCHID.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.YELLOW_ORCHID.getBlock()), false);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		Entity entity = event.getTarget();
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty()) {
			if (stack.getItem() == ModItems.MUNDABITUR_DUST.getItem()) {
				if (entity instanceof CreeperEntity) {
					CreeperEntity creeperEntity = (CreeperEntity) entity;
					if (!creeperEntity.getDataManager().get(CreeperEntity.POWERED)) {
						ModUtils.shrinkStack(event.getPlayer(), stack);
						creeperEntity.getDataManager().set(CreeperEntity.POWERED, true);
						event.getPlayer().swingArm(event.getHand());
					}
				}
			}
		}
	}
}
