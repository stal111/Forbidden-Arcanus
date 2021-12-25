package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Capability Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.CapabilityEvents
 *
 * @author Valhelsia Team
 * @version 1.18.1 - 0.1.0
 * @since 2021-09-25
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IAureal.class);
    }
}
