package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.capability.flightTimeLeft.FlightTimeLeftCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.atomic.AtomicInteger;

@Mod.EventBusSubscriber
public class PlayerCloneListener {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            PlayerEntity player = event.getPlayer();

            IAureal capability = event.getOriginal().getCapability(AurealProvider.CAPABILITY).resolve().get();

            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                aureal.setAureal(capability.getAureal());
                aureal.setCorruption(capability.getCorruption());
                aureal.setCorruptionTimer(capability.getCorruptionTimer());
                for (IConsequence consequence : capability.getActiveConsequences()) {
                    aureal.addActiveConsequence(consequence);
                }
            });

            AtomicInteger i = new AtomicInteger();

            event.getOriginal().getCapability(FlightTimeLeftCapability.FLIGHT_TIME_LEFT_CAPABILITY).ifPresent(iFlightTimeLeft ->
                    i.set(iFlightTimeLeft.getFlightTimeLeft()));

            player.getCapability(FlightTimeLeftCapability.FLIGHT_TIME_LEFT_CAPABILITY).ifPresent(iFlightTimeLeft ->
                    iFlightTimeLeft.setFlightTimeLeft(i.get()));
        }
    }
}
