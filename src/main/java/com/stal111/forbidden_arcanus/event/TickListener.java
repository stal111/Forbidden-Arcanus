package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.api.common.counter.SerializableCounter;
import net.valhelsia.valhelsia_core.api.common.counter.capability.CounterProvider;
import net.valhelsia.valhelsia_core.api.common.network.UpdateCounterPacket;

/**
 * Tick Listener <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.TickListener
 *
 * @author stal111
 * @version 2.0.0
 */
@Mod.EventBusSubscriber
public class TickListener {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        Level world = player.getCommandSenderWorld();

        if (event.phase == TickEvent.Phase.START) {
            if (player instanceof ServerPlayer serverPlayer) {
                AurealHelper.playerTick(serverPlayer);

                player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                    SerializableCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

                    if (counter.isActive()) {
                        counter.decrease();

                        if (counter.getValue() <= 0) {
                            counter.setActive(false);

                            if (!player.getAbilities().instabuild && !player.isSpectator()) {
                                player.getAbilities().mayfly = false;
                                player.getAbilities().flying = false;

                                player.onUpdateAbilities();
                            }

                        } else if (!player.getAbilities().mayfly) {
                            player.getAbilities().mayfly = true;

                            player.onUpdateAbilities();
                        }

                        NetworkHandler.sendTo(serverPlayer, new UpdateCounterPacket(counter));
                    }
                });
            }
        }
    }
}
