package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.common.network.UpdateCounterPacket;

import java.util.Objects;

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
            if (!world.isClientSide()) {
                AurealHelper.playerTick(player);

                player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                    SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

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

                        NetworkHandler.sendTo(player, new UpdateCounterPacket(counter));
                    }
                });
            }

            if (player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
                MobEffectInstance instance = player.getEffect(MobEffects.FIRE_RESISTANCE);
                int duration = Objects.requireNonNull(instance).getDuration();

                if (duration == 32767 && !player.getInventory().contains(new ItemStack(NewModItems.ETERNAL_OBSIDIAN_SKULL.get()))) {
                    player.removeEffectNoUpdate(instance.getEffect());
                }
            }
        }
    }
}
