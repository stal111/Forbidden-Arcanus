package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Blood Test Tube Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item {

    public static final int MAX_BLOOD = 3000;

    public BloodTestTubeItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public String getDescriptionId(@Nonnull ItemStack stack) {
        return ModItems.TEST_TUBE.get().getDescriptionId();
    }

    public static int getBlood(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Blood");
    }

    public static ItemStack setBlood(ItemStack stack, int blood) {
        CompoundTag compound = stack.getOrCreateTag();
        compound.putInt("Blood", blood);

        return stack;
    }

    public static void addBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.min(getBlood(stack) + blood, MAX_BLOOD));
    }

    public static ItemStack removeBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.max(getBlood(stack) - blood, 0));

        if (getBlood(stack) == 0) {
            stack = new ItemStack(ModItems.TEST_TUBE.get());
        }
        return stack;
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable Level worldIn, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslatableComponent(ForbiddenArcanus.MOD_ID + ".blood").append(": " + getBlood(stack) + "/" + MAX_BLOOD).withStyle(ChatFormatting.GRAY));
    }
}
