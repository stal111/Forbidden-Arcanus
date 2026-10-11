package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Consumer;

public interface BlockFlyingLabel extends FlyingLabel<BlockHitResult> {

    void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockPos pos, Consumer<Component> adder);

    @Override
    default void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockHitResult result, Consumer<Component> adder) {
        this.getComponents(level, player, stack, result.getBlockPos(), adder);
    }
}
