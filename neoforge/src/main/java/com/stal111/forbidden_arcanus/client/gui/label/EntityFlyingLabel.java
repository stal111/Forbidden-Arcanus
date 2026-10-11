package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

import java.util.function.Consumer;

public interface EntityFlyingLabel extends FlyingLabel<EntityHitResult> {

    void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, Entity entity, Consumer<Component> adder);

    @Override
    default void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, EntityHitResult result, Consumer<Component> adder) {
        this.getComponents(level, player, stack, result.getEntity(), adder);
    }
}
