package com.stal111.forbidden_arcanus.network;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.loader.RitualLoader;
import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.Ritual;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * Update Ritual Packet
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.network.UpdateRitualPacket
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-17
 */
public class UpdateRitualPacket {

    private final BlockPos pos;
    @Nullable private final Ritual ritual;

    public UpdateRitualPacket(BlockPos pos, @Nullable Ritual ritual) {
        this.pos = pos;
        this.ritual = ritual;
    }

    public static void encode(UpdateRitualPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBlockPos(packet.pos);
        ResourceLocation empty = new ResourceLocation("", "");
        buffer.writeResourceLocation(packet.ritual != null ? packet.ritual.getName() : empty);
    }

    public static UpdateRitualPacket decode(FriendlyByteBuf buffer) {
        return new UpdateRitualPacket(buffer.readBlockPos(), RitualLoader.getRitual(buffer.readResourceLocation()));
    }

    public static void consume(UpdateRitualPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = Minecraft.getInstance().level;

            if (world == null || !(world.getBlockEntity(packet.pos) instanceof HephaestusForgeBlockEntity)) {
                return;
            }

            HephaestusForgeBlockEntity tileEntity = (HephaestusForgeBlockEntity) world.getBlockEntity(packet.pos);

            if (tileEntity != null) {
                tileEntity.getRitualManager().setActiveRitual(packet.ritual);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
