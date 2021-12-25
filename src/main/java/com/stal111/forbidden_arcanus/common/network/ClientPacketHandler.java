package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.network.clientbound.*;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Client Packet Handler <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.network.ClientPacketHandler
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-12-25
 */
public class ClientPacketHandler {

    public static void handleUpdateAureal(UpdateAurealPacket packet) {
        Player player = Minecraft.getInstance().player;

        if (player != null) {
            AurealHelper.load(packet.tag(), AurealHelper.getCapability(player));
        }
    }

    public static void handleUpdatePedestal(UpdatePedestalPacket packet) {
        Level level = Minecraft.getInstance().level;

        if (level != null && level.getBlockEntity(packet.pos()) instanceof PedestalBlockEntity blockEntity) {
            blockEntity.setStack(packet.stack());
            blockEntity.setItemHeight(packet.itemHeight());
        }
    }

    public static void handleAddItemParticle(AddItemParticlePacket packet) {
        Level level = Minecraft.getInstance().level;

        if (level != null) {
            RenderUtils.addItemParticles(level, packet.stack(), packet.pos(), 16);
        }
    }

    public static void handleUpdateRitual(UpdateForgeRitualPacket packet) {
        Level level = Minecraft.getInstance().level;

        if (level == null || !(level.getBlockEntity(packet.pos()) instanceof HephaestusForgeBlockEntity blockEntity)) {
            return;
        }

        blockEntity.getRitualManager().setActiveRitual(packet.ritual());
    }

    public static void handleUpdateItemInSlot(UpdateItemInSlotPacket packet) {
        Level level = Minecraft.getInstance().level;

        if (level != null && level.getBlockEntity(packet.pos()) instanceof Container container) {
            container.setItem(packet.slot(), packet.stack());
        }
    }
}
