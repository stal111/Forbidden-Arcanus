package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
            this.increaseCorruption(player, entity, random);
            this.spawnLostSoul(entity, random);
        }
    }

    private void increaseCorruption(ServerPlayer player, LivingEntity entity, RandomSource random) {
        MobCategory category = entity.getType().getCategory();

        if (category == MobCategory.AMBIENT || category == MobCategory.CREATURE) {
            boolean aurealEntity = entity.getPersistentData().getBoolean("aureal");

            double chance = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_CHANCE.get(): AurealConfig.ENTITY_DEATH_INCREASEMENT_CHANCE.get();
            int amount = aurealEntity ? AurealConfig.AUREAL_ENTITY_DEATH_INCREASEMENT_AMOUNT.get() : AurealConfig.ENTITY_DEATH_INCREASEMENT_AMOUNT.get();

            if (random.nextDouble() < chance) {
                AurealHelper.increaseCorruption(player, amount);
            }
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
