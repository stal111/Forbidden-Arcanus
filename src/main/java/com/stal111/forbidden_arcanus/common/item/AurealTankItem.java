package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class AurealTankItem extends Item {

    public static final int DEFAULT_CAPACITY = 100;
    public static final int MAX_CAPACITY = 3000;

    public static final ItemEssenceData DEFAULT_DATA = new ItemEssenceData(EssenceData.createEmpty(EssenceType.AUREAL, DEFAULT_CAPACITY), true);

    private static final int BAR_COLOR = FastColor.ARGB32.color(255, 159, 226, 253);

    public AurealTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        ItemEssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        if (data != null) {
            components.add(Component.translatable("tooltip.forbidden_arcanus.aureal_tank.tier", data.get().limit() / DEFAULT_CAPACITY).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return EssenceHelper.getEssenceData(stack).map(data -> Math.round(13.0F * data.getFillPercentage())).orElse(0);
    }
}
