package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Blood Test Tube Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.BloodTestTubeItem
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
    public String getTranslationKey(@Nonnull ItemStack stack) {
        return NewModItems.TEST_TUBE.get().getTranslationKey();
    }

    public static int getBlood(ItemStack stack) {
        return stack.getOrCreateTag().getInt("Blood");
    }

    public static ItemStack setBlood(ItemStack stack, int blood) {
        CompoundNBT compound = stack.getOrCreateTag();
        compound.putInt("Blood", blood);

        return stack;
    }

    public static void addBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.min(getBlood(stack) + blood, MAX_BLOOD));
    }

    public static ItemStack removeBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.max(getBlood(stack) - blood, 0));

        if (getBlood(stack) == 0) {
            stack = new ItemStack(NewModItems.TEST_TUBE.get());
        }
        return stack;
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        tooltip.add(new TranslationTextComponent(ForbiddenArcanus.MOD_ID + ".blood").appendString(": " + getBlood(stack) + "/" + MAX_BLOOD).mergeStyle(TextFormatting.GRAY));
    }
}
