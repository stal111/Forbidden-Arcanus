package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullShieldItem;
import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntityLiving();

        //On Player damaged
        if (entity instanceof Player player) {
            Inventory inventory = player.getInventory();

            if (ObsidianSkullItem.shouldProtectFromDamage(source, inventory) || ObsidianSkullShieldItem.shouldProtectFromDamage(source, inventory)) {
                event.setCanceled(true);
                return;
            }
        }


        if (source.msgId.equals("player") && source.getEntity() instanceof Player player) {
            if (player.getItemInHand(player.getUsedItemHand()).is(ModItems.MYSTICAL_DAGGER.get())) {
                BloodTestTubeItem.collectBlood(player, event.getAmount());
            }
        }
    }

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        LivingEntity entity = event.getEntityLiving();
        MobCategory category = entity.getType().getCategory();

        if (category == MobCategory.AMBIENT || category == MobCategory.CREATURE) {
            if (entity.getCommandSenderWorld().getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
                entity.getPersistentData().putBoolean("aureal", true);
            }
        }
    }
}
