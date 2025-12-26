package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.lostsoul.AbstractLostSoul;
import com.stal111.forbidden_arcanus.common.item.enchantment.ModEnchantmentHelper;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * @author stal111
 * @since 2022-11-02
 */
public final class DeathEvents {

    public static final double ENCHANTED_LOST_SOUL_CHANCE = 0.04D;

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        RandomSource random = entity.getRandom();
        ItemStack stack = event.getSource().getWeaponItem();

        if (stack != null && entity.level() instanceof ServerLevel level) {
            this.spawnLostSoul(level, ModEnchantmentHelper.getLostSoulSpawnChance(level, stack), entity, random);
        }
    }

    private void spawnLostSoul(ServerLevel level, float spawnChance, LivingEntity entity, RandomSource random) {

        if (random.nextDouble() >= spawnChance) {
            return;
        }

        EntityType<? extends AbstractLostSoul> lostSoulType = null;

        if (entity.is(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE)) {
            lostSoulType = ModEntities.LOST_SOUL.get();
        } else if (entity.is(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE)) {
            lostSoulType = ModEntities.CORRUPT_LOST_SOUL.get();
        }

        if (lostSoulType != null) {
            if (random.nextDouble() < ENCHANTED_LOST_SOUL_CHANCE) {
                lostSoulType = ModEntities.ENCHANTED_LOST_SOUL.get();
            }

            lostSoulType.spawn(level, entity.blockPosition(), EntitySpawnReason.TRIGGERED);
        }
    }
}
