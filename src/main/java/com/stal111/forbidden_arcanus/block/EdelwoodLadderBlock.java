package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodLadderBlock extends LadderBlock {

	public EdelwoodLadderBlock(Properties builder) {
		super(builder);
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
		return true;
	}

	@Override
	public void addInformation(@Nonnull ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> list, @Nonnull ITooltipFlag flag) {
		list.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + ".edelwood_ladder").mergeStyle(TextFormatting.GRAY));
		super.addInformation(stack, world, list, flag);
	}
}
