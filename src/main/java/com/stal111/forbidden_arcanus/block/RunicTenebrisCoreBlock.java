package com.stal111.forbidden_arcanus.block;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.sound.ModSounds;

import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RunicTenebrisCoreBlock extends WaterloggedBlock {

	public RunicTenebrisCoreBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItemMainhand();
		if (!stack.isEmpty()) {
			Map<Item, Item> ITEM_TRANSFORM_MAP = (new ImmutableMap.Builder<Item, Item>()).put(ModItems.RUNE.getItem(), ModItems.DARK_RUNE.getItem()).put(ModItems.RUNE_BAG.getItem(), ModItems.DARK_RUNE_BAG.getItem()).put(ModBlocks.RUNESTONE.getItem(), ModBlocks.DARK_RUNESTONE.getItem()).build();
			Item transformedItem = ITEM_TRANSFORM_MAP.get(stack.getItem());
			if (transformedItem != null) {
				ItemStackUtils.shrinkStack(player, stack);
				if (!player.addItemStackToInventory(new ItemStack(transformedItem))) {
					player.dropItem(new ItemStack(transformedItem), false);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return super.onBlockActivated(state, world, pos, player, hand, result);
	}

	@Override
	public int getLightValue(BlockState state) {
		return 14;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity entity, ItemStack stack) {
		world.playSound(pos.getX(), pos.getY() + 0.5D, pos.getZ(), ModSounds.runic_tenebris_core_activated, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
		super.onBlockPlacedBy(world, pos, p_180633_3_, entity, stack);
	}

	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
		for (int l = 0; l < 2; l++) {
			double d0 = pos.getX() + random.nextFloat();
			double d1 = pos.getY() + random.nextFloat();
			double d2 = pos.getZ() + random.nextFloat();
			double d3 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			double d4 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			double d5 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			world.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, d3, d4, d5);
		}
		world.playSound(pos.getX(), pos.getY() + 0.5D, pos.getZ(), ModSounds.runic_tenebris_core_ambient, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
	}
}
