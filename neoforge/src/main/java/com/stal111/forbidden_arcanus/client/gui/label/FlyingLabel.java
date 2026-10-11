package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;

import java.util.function.Consumer;

public interface FlyingLabel<R extends HitResult> {

    void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, R result, Consumer<Component> adder);
}
