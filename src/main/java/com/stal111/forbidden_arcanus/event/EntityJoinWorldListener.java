package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.entity.PixieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityJoinWorldListener {

    @SubscribeEvent
    public static void onWorldJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof SkeletonEntity) {
            SkeletonEntity skeletonEntity = (SkeletonEntity) entity;
            skeletonEntity.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(skeletonEntity, PixieEntity.class, true));
        }
    }
}
