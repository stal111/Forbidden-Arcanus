package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class EdelwoodLadderBlock extends LadderBlock {

	public EdelwoodLadderBlock(Properties builder) {
		super(builder);
	}

	@Override
	public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
		return true;
	}

	@Override
	public void appendHoverText(@Nonnull ItemStack stack, @Nullable BlockGetter world, List<Component> list, @Nonnull TooltipFlag flag) {
		list.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".edelwood_ladder").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, world, list, flag);
	}
}
