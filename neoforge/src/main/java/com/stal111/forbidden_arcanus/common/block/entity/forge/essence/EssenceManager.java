package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

/**
 * Essence Manager <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.tile.forge.ritual.EssenceManager
 *
 * @author stal111
 * @since 2021-07-10
 */
public class EssenceManager {

    private static final int ENTITY_CHECK_RADIUS = 5;

    private final Object2FloatArrayMap<LivingEntity> cachedHealth = new Object2FloatArrayMap<>();

    public void tick(Level level, BlockPos pos) {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(ENTITY_CHECK_RADIUS));

        for (LivingEntity entity : entities) {
            if (this.cachedHealth.containsKey(entity)) {
                float healthDifference = this.cachedHealth.getFloat(entity) - entity.getHealth();

                if (healthDifference > 0) {
                    //TODO
//                    this.increaseEssence(EssenceType.BLOOD, (int) healthDifference * 20);
                }
            }
        }

        this.cachedHealth.clear();

        for (LivingEntity entity : entities) {
            this.cachedHealth.put(entity, entity.getHealth());
        }
    }
}
