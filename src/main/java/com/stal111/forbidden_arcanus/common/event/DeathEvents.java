package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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

        if (event.getSource().getEntity() instanceof ServerPlayer player) {
            this.spawnLostSoul(entity, random);
        }
    }

    private void spawnLostSoul(LivingEntity entity, RandomSource random) {
        EntityType<?> type = entity.getType();
        Level level = entity.level();

        double chance = LostSoul.ENTITY_DEATH_SPAWN_CHANCE;

        if (type.is(ModTags.EntityTypes.SPAWNS_LOST_SOUL_CHANCE) && random.nextDouble() < chance) {
            level.addFreshEntity(new LostSoul(level, entity.getX(), entity.getY(), entity.getZ()));
        } else if (type.is(ModTags.EntityTypes.SPAWNS_CORRUPT_LOST_SOUL_CHANCE) && random.nextDouble() < chance) {
            LostSoul lostSoul = new LostSoul(level, entity.getX(), entity.getY(), entity.getZ());
            lostSoul.setVariant(LostSoul.Variant.CORRUPT_LOST_SOUL);

            level.addFreshEntity(lostSoul);
        }
    }
}
