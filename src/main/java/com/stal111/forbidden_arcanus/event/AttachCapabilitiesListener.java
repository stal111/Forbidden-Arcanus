package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.capability.eternalStellaActive.EternalStellaActiveCapability;
import com.stal111.forbidden_arcanus.capability.flightTimeLeft.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.capability.item.timer.TimerProvider;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.EntitySpawningBlockingCapability;
import com.stal111.forbidden_arcanus.item.ITimerItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttachCapabilitiesListener {

    @SubscribeEvent
    public static void onPlayerCapabilityAttachEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getCapabilities().containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_time_left"))) {
                event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_time_left"), new FlightTimeLeftCapability());
            }

            if (!event.getCapabilities().containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "eternal_stella_active"))) {
                event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "eternal_stella_active"), new EternalStellaActiveCapability());
            }
            event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aureal"), new AurealProvider());
        }
    }

    @SubscribeEvent
    public static void onWorldCapabilityAttachEvent(AttachCapabilitiesEvent<World> event) {
        if (!event.getCapabilities().containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "enity_spawning_blocking_blocks"))) {
            event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity_spawning_blocking_blocks"), new EntitySpawningBlockingCapability());
        }
    }

    @SubscribeEvent
    public static void onItemStackCapabilityAttachEvent(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() instanceof ITimerItem) {
            event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "timer"), new TimerProvider());
        }
    }
}
