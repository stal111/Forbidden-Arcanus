package com.stal111.forbidden_arcanus.client.gui.label;

import com.stal111.forbidden_arcanus.common.block.entity.EssenceStorageBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 08.05.2024
 */
public class EssenceStorageFlyingLabel implements BlockFlyingLabel {

    @Override
    public void getComponents(ClientLevel level, LocalPlayer player, ItemStack stack, BlockPos pos, Consumer<Component> adder) {
        if (level.getBlockEntity(pos) instanceof EssenceStorageBlockEntity blockEntity) {
            EssenceStorage storage = blockEntity.getEssenceStorage();

            adder.accept(storage.asComponent(ChatFormatting.WHITE));
        }
    }
}
