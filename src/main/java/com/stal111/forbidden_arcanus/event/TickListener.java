package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.network.UpdateCounterPacket;

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
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();

        if (event.phase == TickEvent.Phase.START) {
            if (!world.isRemote()) {
                AurealHelper.playerTick(player);

                player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                    SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

                    if (counter.isActive()) {
                        counter.decrease();

                        if (counter.getValue() <= 0) {
                            counter.setActive(false);

                            if (!player.abilities.isCreativeMode && !player.isSpectator()) {
                                player.abilities.allowFlying = false;
                                player.abilities.isFlying = false;

                                player.sendPlayerAbilities();
                            }

                        } else if (!player.abilities.allowFlying) {
                            player.abilities.allowFlying = true;

                            player.sendPlayerAbilities();
                        }

                        NetworkHandler.sendTo(player, new UpdateCounterPacket(counter));
                    }
                });
            }

            if (player.isPotionActive(Effects.FIRE_RESISTANCE)) {
                EffectInstance instance = player.getActivePotionEffect(Effects.FIRE_RESISTANCE);
                int duration = Objects.requireNonNull(instance).getDuration();

                if (duration == 32767 && !player.inventory.hasItemStack(new ItemStack(NewModItems.ETERNAL_OBSIDIAN_SKULL.get()))) {
                    player.removeActivePotionEffect(instance.getPotion());
                }
            }
        }
    }
}
