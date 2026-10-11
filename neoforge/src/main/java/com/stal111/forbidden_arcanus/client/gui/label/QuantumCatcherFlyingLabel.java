package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 12.05.2024
 */
public class QuantumCatcherFlyingLabel implements EntityFlyingLabel {

    @Override
    public void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, Entity entity, Consumer<Component> adder) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!stack.has(ModDataComponents.STORED_ENTITY) && stack.getItem() instanceof QuantumCatcherItem item && item.isValidEntity(livingEntity)) {
            EssenceHelper.getEssenceAccess(player).ifPresent(provider -> {
                EssenceValue cost = QuantumCatcherItem.calculateAurealCost(livingEntity);

                adder.accept(cost.asComponent(provider.getEssenceAmount(EssenceType.AUREAL) < cost.amount() ? ChatFormatting.RED : ChatFormatting.WHITE));
            });
        }
    }
}
