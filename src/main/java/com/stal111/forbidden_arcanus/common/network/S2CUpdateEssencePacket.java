package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;
import com.stal111.forbidden_arcanus.common.inventory.HephaestusForgeMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record S2CUpdateEssencePacket(EssencesDefinition essences) {

    public static void encode(S2CUpdateEssencePacket packet, FriendlyByteBuf buffer) {
        // Записываем каждый тип как int (4 байта), а не short
        buffer.writeInt(packet.essences.aureal());
        buffer.writeInt(packet.essences.souls());
        buffer.writeInt(packet.essences.blood());
        buffer.writeInt(packet.essences.experience());
    }

    public static S2CUpdateEssencePacket decode(FriendlyByteBuf buffer) {
        return new S2CUpdateEssencePacket(new EssencesDefinition(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt()
        ));
    }

    public static void handle(S2CUpdateEssencePacket packet, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
            if (mc.player != null && mc.player.containerMenu instanceof HephaestusForgeMenu menu) {
                menu.setClientEssences(packet.essences());
            }
        });
        context.setPacketHandled(true);
    }
}
