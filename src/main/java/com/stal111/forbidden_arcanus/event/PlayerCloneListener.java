package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerCloneListener {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player player = event.getPlayer();

            IAureal capability = AurealHelper.getCapability(event.getOriginal());

            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                aureal.setAureal(capability.getAureal());
                aureal.setCorruption(capability.getCorruption());
                aureal.setCorruptionTimer(capability.getCorruptionTimer());
                for (IConsequence consequence : capability.getActiveConsequences()) {
                    aureal.addActiveConsequence(consequence);
                }
            });

           //player.getCapability(CounterProvider.CAPABILITY).ifPresent(iCounterCapability -> iCounterCapability.setCounters(counterCapability.getCounters()));
        }
    }
}
