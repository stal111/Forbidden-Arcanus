package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityJoinWorldListener {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && !event.getWorld().isClientSide()) {
            if (entity instanceof Player) {
                Player player = (Player) entity;

                AurealHelper.sendAurealUpdatePacket(player);
            }
        }
    }
}
