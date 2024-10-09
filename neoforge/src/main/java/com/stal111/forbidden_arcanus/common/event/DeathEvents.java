package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.common.item.enchantment.ModEnchantmentHelper;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

/**
 * @author stal111
 * @since 2022-11-02
 */
public final class DeathEvents {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        RandomSource random = entity.getRandom();
        ItemStack stack = event.getSource().getWeaponItem();

        if (stack != null && entity.level() instanceof ServerLevel level) {
            this.spawnLostSoul(ModEnchantmentHelper.getLostSoulSpawnChance(level, stack), entity, random);
        }
    }

    private void spawnLostSoul(float spawnChance, LivingEntity entity, RandomSource random) {
        EntityType<?> type = entity.getType();
        Level level = entity.level();

        if (random.nextDouble() >= spawnChance) {
            return;
        }

        if (type.is(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE)) {
            level.addFreshEntity(new LostSoul(level, entity.getX(), entity.getY(), entity.getZ()));
        } else if (type.is(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE)) {
            LostSoul lostSoul = new LostSoul(level, entity.getX(), entity.getY(), entity.getZ());
            lostSoul.setVariant(LostSoul.Variant.CORRUPT_LOST_SOUL);

            level.addFreshEntity(lostSoul);
        }
    }
}
