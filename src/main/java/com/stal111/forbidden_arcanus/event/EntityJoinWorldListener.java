package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.network.AurealUpdatePacket;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityJoinWorldListener {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity && !event.getWorld().isRemote()) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                NetworkHandler.sendTo(player, new AurealUpdatePacket(player));
            }

            if (entity instanceof SkeletonEntity) {
                SkeletonEntity skeletonEntity = (SkeletonEntity) entity;
                skeletonEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(skeletonEntity, PixieEntity.class, true));
            }
        }
    }
}
