package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.capability.eternalStellaActive.EternalStellaActiveCapability;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class EternalStellaItem extends Item {

    public EternalStellaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        player.getCapability(EternalStellaActiveCapability.ETERNAL_STELLA_ACTIVE_CAPABILITY).ifPresent(iEternalStellaActive ->
                iEternalStellaActive.setEternalStellaActive(true));

        if (!player.abilities.isCreativeMode) {
            setUsesLeft(stack, getUsesLeft(stack) - 1);
        }

        if (getUsesLeft(stack) == 0) {
            stack.shrink(1);
        }

        return ActionResult.resultSuccess(stack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, world, tooltip, flag);

        tooltip.add(new TranslationTextComponent("tooltip." + Main.MOD_ID + ".uses_left").func_240702_b_(": " + getUsesLeft(stack)).func_240699_a_(TextFormatting.GRAY));
    }

    private int getUsesLeft(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();

        if (!compoundnbt.contains("uses_left")) {
            return ItemConfig.ETERNAL_STELLA_USES.get();
        } else {
            return compoundnbt.getInt("uses_left");
        }
    }

    private void setUsesLeft(ItemStack stack, int usesLeft) {
        CompoundNBT compoundnbt = stack.getOrCreateTag();

        compoundnbt.putInt("uses_left", usesLeft);
    }
}
