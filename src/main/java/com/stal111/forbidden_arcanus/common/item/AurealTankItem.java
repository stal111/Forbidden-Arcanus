package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceContainer;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
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
public class AurealTankItem extends Item implements EssenceContainer {

    public static final int DEFAULT_CAPACITY = 100;
    public static final int MAX_CAPACITY = 3000;

    public static final EssenceData DEFAULT_DATA = EssenceData.createEmpty(DEFAULT_CAPACITY);

    private static final int BAR_COLOR = FastColor.ARGB32.color(255, 159, 226, 253);

    public AurealTankItem(Properties properties) {
        super(properties);
    }

    public static ItemStack create(Item item, int aureal) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_DATA, new EssenceData(aureal, DEFAULT_CAPACITY));

        return stack;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        if (data != null) {
            components.add(Component.translatable("tooltip.forbidden_arcanus.aureal_tank.tier", data.limit() / DEFAULT_CAPACITY).withStyle(ChatFormatting.GRAY));

            data.addToTooltip(context, component -> components.add(this.getType(stack).getComponent().copy().withStyle(ChatFormatting.GRAY).append(component)), flag);
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
        EssenceData data = stack.get(ModDataComponents.ESSENCE_DATA);

        return data != null ? Math.round(13.0F * data.getFillPercentage()) : 0;
    }

    @Override
    public EssenceType getType(ItemStack stack) {
        return EssenceType.AUREAL;
    }
}
