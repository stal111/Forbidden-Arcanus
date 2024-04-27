package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHolder;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class BloodTestTubeItem extends Item implements EssenceHolder {

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
        return 0;
        //TODO
        //return stack.getOrCreateTag().getInt("Blood");
    }

    public static ItemStack setBlood(ItemStack stack, int blood) {
//        CompoundTag compound = stack.getOrCreateTag();
//        compound.putInt("Blood", blood);

        return stack;
    }

    public static ItemStack removeBlood(ItemStack stack, int blood) {
        setBlood(stack, Math.max(getBlood(stack) - blood, 0));

        if (getBlood(stack) == 0) {
            stack = new ItemStack(ModItems.TEST_TUBE.get());
        }
        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        if (data != null) {
            data.addToTooltip(context, components::add, flag);
        }
    }

    @Override
    public EssenceType getType() {
        return EssenceType.BLOOD;
    }

    @Override
    public int getLimit() {
        return MAX_BLOOD;
    }
}
