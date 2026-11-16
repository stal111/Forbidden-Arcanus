package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.essence.EssenceCost;
import com.stal111.forbidden_arcanus.common.item.component.EntityCatcher;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
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

        EntityCatcher entityCatcher = stack.get(ModDataComponents.ENTITY_CATCHER);

        if (!stack.has(ModDataComponents.STORED_ENTITY) && entityCatcher != null && entityCatcher.isValidEntity(livingEntity)) {
            EssenceCost cost = EntityCatcher.calculateAurealCost(livingEntity);

            adder.accept(cost.asFormattedComponent(player));
        }
    }
}
