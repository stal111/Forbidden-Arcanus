package com.stal111.forbidden_arcanus.common.network;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.network.clientbound.SetClibanoResiduesPayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.SpawnParticlePayload;
import com.stal111.forbidden_arcanus.common.network.clientbound.TransformPedestalPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

/**
 * @author stal111
 * @since 02.03.2024
 */
public class NetworkEvents {

    @SubscribeEvent
    private void registerPackets(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(ForbiddenArcanus.MOD_ID).versioned("1.0");

        registrar.play(SetClibanoResiduesPayload.ID, SetClibanoResiduesPayload::new, builder -> builder.client(SetClibanoResiduesPayload::handle));
        registrar.play(TransformPedestalPayload.ID, TransformPedestalPayload::new, builder -> builder.client(TransformPedestalPayload::handle));
        registrar.play(SpawnParticlePayload.ID, SpawnParticlePayload::new, builder -> builder.client(SpawnParticlePayload::handle));
    }
}
