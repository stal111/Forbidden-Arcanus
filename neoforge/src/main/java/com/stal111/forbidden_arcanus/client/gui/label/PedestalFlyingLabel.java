package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class PedestalFlyingLabel implements BlockFlyingLabel {

    @Override
    public void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockPos pos, Consumer<Component> adder) {
        if (level.getBlockEntity(pos) instanceof PedestalBlockEntity blockEntity) {
            ItemStack pedestalStack = blockEntity.getStack();

            if (pedestalStack.isEmpty()) {
                return;
            }

            adder.accept(pedestalStack.getHoverName());
        }
    }
}
