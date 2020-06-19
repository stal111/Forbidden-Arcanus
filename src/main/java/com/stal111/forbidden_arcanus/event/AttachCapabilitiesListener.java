package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.capability.FlightTimeLeftCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttachCapabilitiesListener {

    @SubscribeEvent
    public static void onCapabilitiesAttached(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getCapabilities().containsKey(new ResourceLocation(Main.MOD_ID, "flight_time_left"))) {
                event.addCapability(new ResourceLocation(Main.MOD_ID, "flight_time_left"), new FlightTimeLeftCapability());
            }
        }
    }
}
