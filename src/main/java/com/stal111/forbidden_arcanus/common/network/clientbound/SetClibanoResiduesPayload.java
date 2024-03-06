package com.stal111.forbidden_arcanus.common.network.clientbound;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.ClientPayloadHandler;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ResiduesStorage;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.residue.ResidueType;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Map;

/**
 * @author stal111
 * @since 02.03.2024
 */
public record SetClibanoResiduesPayload(Map<Holder<ResidueType>, Integer> residueAmounts) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(ForbiddenArcanus.MOD_ID, "set_clibano_residues");

    public SetClibanoResiduesPayload(FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(ResiduesStorage.MAP_CODEC));
    }

    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeJsonWithCodec(ResiduesStorage.MAP_CODEC, this.residueAmounts);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        ClientPayloadHandler.getInstance().handle(this, context);
    }
}
