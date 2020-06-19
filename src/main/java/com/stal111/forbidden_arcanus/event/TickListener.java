package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.capability.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.network.FlightTimeLeftPacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

@Mod.EventBusSubscriber
public class TickListener {

    public static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        ItemStack stack = player.getHeldItemMainhand();

            if (stack != ItemStack.EMPTY && stack.isDamageable()) {
                if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE.get())) {
                    if (stack.getDamage() > 0) {
                        stack.setDamage(0);
                    }
                }
            }

            if (tickCounter == 0) {
                tickCounter++;

                return;
            } else {
                tickCounter = 0;
            }

            if (!player.world.isRemote()) {
                player.getCapability(FlightTimeLeftCapability.FLIGHT_TIME_LEFT_CAPABILITY)
                        .ifPresent(iFlightTimeLeft -> {
                            if (!player.abilities.allowFlying && iFlightTimeLeft.getFlightTimeLeft() > 0) {
                                player.abilities.allowFlying = true;
                            }

                            if (iFlightTimeLeft.getFlightTimeLeft() <= 2 && iFlightTimeLeft.getFlightTimeLeft() != 0) {
                                player.abilities.allowFlying = false;
                                player.abilities.isFlying = false;

                                iFlightTimeLeft.setFlightTimeLeft(0);
                            }

                            if (iFlightTimeLeft.getFlightTimeLeft() != 0) {
                                iFlightTimeLeft.setFlightTimeLeft(iFlightTimeLeft.getFlightTimeLeft() - 1);
                            }

                            NetworkHandler.INSTANCE.sendTo(new FlightTimeLeftPacket(iFlightTimeLeft.getFlightTimeLeft()), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                        });
            }
    }
}
