package com.stal111.forbidden_arcanus.block;

import com.google.common.collect.ImmutableMap;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class RunicTenebrisCoreBlock extends WaterloggedBlock {

	public RunicTenebrisCoreBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getMainHandItem();
		if (!stack.isEmpty()) {
			Map<Item, Item> ITEM_TRANSFORM_MAP = (new ImmutableMap.Builder<Item, Item>()).put(ModItems.RUNE.get(), ModItems.DARK_RUNE.get()).put(ModItems.RUNE_BAG.get(), ModItems.DARK_RUNE_BAG.get()).put(ModBlocks.RUNESTONE.getItem(), ModBlocks.DARK_RUNESTONE.getItem()).build();
			Item transformedItem = ITEM_TRANSFORM_MAP.get(stack.getItem());
			if (transformedItem != null) {
				ItemStackUtils.shrinkStack(player, stack);
				if (!player.addItem(new ItemStack(transformedItem))) {
					player.drop(new ItemStack(transformedItem), false);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState p_180633_3_, @Nullable LivingEntity entity, ItemStack stack) {
		world.playLocalSound(pos.getX(), pos.getY() + 0.5D, pos.getZ(), ModSounds.runic_tenebris_core_activated, SoundSource.BLOCKS, 1.0F, 1.0F, true);
		super.setPlacedBy(world, pos, p_180633_3_, entity, stack);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
		for (int l = 0; l < 2; l++) {
			double d0 = pos.getX() + random.nextFloat();
			double d1 = pos.getY() + random.nextFloat();
			double d2 = pos.getZ() + random.nextFloat();
			double d3 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			double d4 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			double d5 = (random.nextFloat() - 0.5D) * 0.5000000014901161D;
			world.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, d3, d4, d5);
		}
		world.playLocalSound(pos.getX(), pos.getY() + 0.5D, pos.getZ(), ModSounds.runic_tenebris_core_ambient, SoundSource.BLOCKS, 1.0F, 1.0F, true);
	}
}
