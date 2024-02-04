package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

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

//    public static void consume(UpdateResidueTypesPacket packet, CustomPayloadEvent.Context context) {
//        context.enqueueWork(() -> {
//            assert context.getDirection() == NetworkDirection.PLAY_TO_CLIENT;
//
//            ResiduesStorage.RESIDUE_TYPES.clear();
//            ResiduesStorage.RESIDUE_TYPES.addAll(packet.residueTypes());
//        });
//        context.setPacketHandled(true);
//    }
}
