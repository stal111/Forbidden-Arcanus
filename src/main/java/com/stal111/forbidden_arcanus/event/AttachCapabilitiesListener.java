package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttachCapabilitiesListener {

    @SubscribeEvent
    public static void onPlayerCapabilityAttachEvent(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aureal"), new AurealProvider());
        }
    }

    @SubscribeEvent
    public static void onWorldCapabilityAttachEvent(AttachCapabilitiesEvent<Level> event) {
        if (!event.getCapabilities().containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity_spawning_blocking_blocks"))) {
         //   event.addCapability(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity_spawning_blocking_blocks"), new EntitySpawningBlockingCapability());
        }
    }
}
