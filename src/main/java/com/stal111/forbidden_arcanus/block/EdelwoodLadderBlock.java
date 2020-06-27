package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.block.LadderBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EdelwoodLadderBlock extends LadderBlock {

	public EdelwoodLadderBlock(Properties builder) {
		super(builder);
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> list, ITooltipFlag flag) {
		list.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".edelwood_ladder").func_240699_a_(TextFormatting.GRAY));
		super.addInformation(stack, world, list, flag);
	}
}
