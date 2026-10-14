package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class HephaestusForgeFlyingLabel implements BlockFlyingLabel {

    @Override
    public void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockPos pos, Consumer<Component> adder) {
        if (level.getBlockEntity(pos) instanceof HephaestusForgeBlockEntity blockEntity) {
            ItemStack displayStack = blockEntity.getMainSlotInventory().getStack();

            if (!displayStack.isEmpty()) {
                adder.accept(displayStack.getHoverName());
                adder.accept(Component.empty());
            }

            for (EssenceType type : EssenceType.values()) {
                adder.accept(blockEntity.getEssence(type).asComponent(ChatFormatting.WHITE));
            }
        }
    }
}
