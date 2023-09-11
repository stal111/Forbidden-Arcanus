package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullShieldItem;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntity();

        //On Player damaged
        if (source.is(DamageTypes.PLAYER_ATTACK) && source.getEntity() instanceof Player player) {
            if (player.getMainHandItem().is(ModItems.MYSTICAL_DAGGER.get())) {
                BloodTestTubeItem.collectBlood(player, event.getAmount());
            }
        }
    }


    //TODO
//    @SubscribeEvent
//    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
//        LivingEntity entity = event.getEntity();
//
//        if (AurealHelper.canEntityBeAureal(entity) && entity.getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
//            entity.getPersistentData().putBoolean("aureal", true);
//        }
//    }
}
