package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.capability.flightTimeLeft.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.network.FlightTimeLeftPacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.List;

@Mod.EventBusSubscriber
public class TickListener {

    public static int tickCounter = 0;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != ItemStack.EMPTY && stack.isDamageable()) {
                if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE.get())) {
                    if (stack.getDamage() > 0) {
                        stack.setDamage(stack.getDamage() - 1);
                    }
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
                        if (iFlightTimeLeft.getFlightTimeLeft() != 0) {
                            if (!player.abilities.allowFlying && iFlightTimeLeft.getFlightTimeLeft() > 0) {
                                player.abilities.allowFlying = true;
                            }

                            if (iFlightTimeLeft.getFlightTimeLeft() <= 2 && iFlightTimeLeft.getFlightTimeLeft() != 0 && !player.abilities.isCreativeMode && !player.isSpectator()) {
                                player.abilities.allowFlying = false;
                                player.abilities.isFlying = false;

                                iFlightTimeLeft.setFlightTimeLeft(0);
                            }

                            if (iFlightTimeLeft.getFlightTimeLeft() != 0) {
                                iFlightTimeLeft.setFlightTimeLeft(iFlightTimeLeft.getFlightTimeLeft() - 1);
                            }

                            NetworkHandler.INSTANCE.sendTo(new FlightTimeLeftPacket(iFlightTimeLeft.getFlightTimeLeft()), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                        }
                    });

            List<ItemEntity> itemEntities = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(player.getPosX() - 10.0, player.getPosY() - 10.0, player.getPosZ() - 10.0, player.getPosX() + 10.0, player.getPosY() + 10.0, player.getPosZ() + 10.0));

            itemEntities.forEach(itemEntity -> {
                if (itemEntity.getItem().getItem() == ModItems.DARK_MATTER.get()) {
                    List<ItemEntity> list = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(itemEntity.getPosX() - 0.5, itemEntity.getPosY() - 0.5, itemEntity.getPosZ() - 0.5, itemEntity.getPosX() + 0.5, itemEntity.getPosY() + 0.5, itemEntity.getPosZ() + 0.5));

                    for (ItemEntity itemEntity1 : list) {
                        if (itemEntity1.getItem().getItem() == ModItems.CORRUPTI_DUST.get() && world.getBlockState(new BlockPos(itemEntity.getPosX(), itemEntity.getPosY(), itemEntity.getPosZ())).isAir()) {
                            itemEntity.getItem().setCount(itemEntity.getItem().getCount() -1);
                            itemEntity1.getItem().setCount(itemEntity1.getItem().getCount() -1);

                            world.setBlockState(new BlockPos(itemEntity.getPosX(), itemEntity.getPosY(), itemEntity.getPosZ()), NewModBlocks.BLACK_HOLE.get().getDefaultState());
                        }
                    }
                }
            });
        }
    }
}
