package com.stal111.forbidden_arcanus.client;

import com.stal111.forbidden_arcanus.common.inventory.clibano.ClibanoMenu;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

/**
 * @author stal111
 * @since 02.03.2024
 */
public class ClientPayloadHandler {

    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }

    public void handle(SetClibanoResiduesPayload payload, PlayPayloadContext context) {
        context.player().ifPresent(player -> {
            if (player.hasContainerOpen() && player.containerMenu instanceof ClibanoMenu menu) {
                menu.setResidueData(payload.residueAmounts());
            }
        });
    }
}
