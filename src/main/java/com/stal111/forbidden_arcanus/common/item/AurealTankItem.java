package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.aureal.ItemAurealProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author stal111
 * @since 17.09.2023
 */
public class AurealTankItem extends Item {

    public static final int DEFAULT_CAPACITY = 100;
    public static final int MAX_CAPACITY = 3000;

    public AurealTankItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag isAdvanced) {
        stack.getCapability(ItemAurealProvider.AUREAL).ifPresent(aurealProvider -> {
            components.add(Component.translatable("tooltip.forbidden_arcanus.aureal_tank.tier", aurealProvider.getTrueAurealLimit() / DEFAULT_CAPACITY).withStyle(ChatFormatting.GRAY));
            components.add(Component.translatable("tooltip.forbidden_arcanus.aureal_tank.aureal", aurealProvider.getAureal(), aurealProvider.getAurealLimit()).withStyle(ChatFormatting.AQUA));
        });
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemAurealProvider(stack, DEFAULT_CAPACITY);
    }
}
