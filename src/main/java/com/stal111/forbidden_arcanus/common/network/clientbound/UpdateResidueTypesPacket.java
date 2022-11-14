package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResidueType;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2022-11-14
 */
public record UpdateResidueTypesPacket(List<ResidueType> residueTypes) {

    public static void encode(UpdateResidueTypesPacket packet, FriendlyByteBuf buffer) {
        buffer.writeCollection(packet.residueTypes(), (friendlyByteBuf, residueType) -> friendlyByteBuf.writeUtf(residueType.name()));
    }

    public static UpdateResidueTypesPacket decode(FriendlyByteBuf buffer) {
        return new UpdateResidueTypesPacket(buffer.readList(friendlyByteBuf -> new ResidueType(friendlyByteBuf.readUtf())));
    }

    public static void consume(UpdateResidueTypesPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            ResiduesStorage.RESIDUE_TYPES.clear();
            ResiduesStorage.RESIDUE_TYPES.addAll(packet.residueTypes());
        });
        ctx.get().setPacketHandled(true);
    }
}
