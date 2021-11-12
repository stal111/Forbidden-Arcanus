package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
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

            if (entity instanceof Skeleton) {
                Skeleton skeletonEntity = (Skeleton) entity;
                skeletonEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(skeletonEntity, PixieEntity.class, true));
            }
        }
    }
}
