package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ForbiddenmiconItem extends Item {

    public ForbiddenmiconItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> list, ITooltipFlag flag) {
        list.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".forbiddenmicon").applyTextStyle(TextFormatting.GRAY));
        super.addInformation(stack, world, list, flag);
    }
}
