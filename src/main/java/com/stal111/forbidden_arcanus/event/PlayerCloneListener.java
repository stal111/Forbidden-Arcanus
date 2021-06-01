package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.ICounterCapability;

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

            ICounterCapability counterCapability = event.getOriginal().getCapability(CounterProvider.CAPABILITY).resolve().get();

            player.getCapability(CounterProvider.CAPABILITY).ifPresent(iCounterCapability -> iCounterCapability.setCounters(counterCapability.getCounters()));
        }
    }
}
