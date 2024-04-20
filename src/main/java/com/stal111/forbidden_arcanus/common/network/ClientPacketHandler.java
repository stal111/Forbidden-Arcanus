package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.common.network.clientbound.UpdateItemInSlotPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.api.common.block.entity.neoforge.ValhelsiaContainerBlockEntity;

/**
 * @author stal111
 * @since 2021-12-25
 */
public class ClientPacketHandler {

    public static void handleUpdateItemInSlot(UpdateItemInSlotPacket packet) {
        Level level = getLevel();

        if (level != null && level.getBlockEntity(packet.pos()) instanceof ValhelsiaContainerBlockEntity blockEntity) {
            blockEntity.setStack(packet.slot(), packet.stack());
        }
    }

    private static ClientLevel getLevel() {
        return Minecraft.getInstance().level;
    }

    private static LevelRenderer getLevelRenderer() {
        return Minecraft.getInstance().levelRenderer;
    }
}
