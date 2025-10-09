package com.stal111.forbidden_arcanus.common.block.entity.forge.tick;

import com.stal111.forbidden_arcanus.common.block.entity.TickEffect;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceAccess;
import it.unimi.dsi.fastutil.objects.Object2FloatArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public record CollectBloodTickEffect(EssenceAccess essenceAccess,
                                     Object2FloatArrayMap<LivingEntity> cachedHealth) implements TickEffect {

    private static final int ENTITY_CHECK_RADIUS = 5;

    public static CollectBloodTickEffect create(EssenceAccess essenceAccess) {
        return new CollectBloodTickEffect(essenceAccess, new Object2FloatArrayMap<>());
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(ENTITY_CHECK_RADIUS));

        for (LivingEntity entity : entities) {
            if (this.cachedHealth.containsKey(entity)) {
                float healthDifference = this.cachedHealth.getFloat(entity) - entity.getHealth();

                if (healthDifference > 0) {
                    this.essenceAccess.addEssence(EssenceType.BLOOD, (int) healthDifference * 20);
                }
            }
        }

        this.cachedHealth.clear();

        for (LivingEntity entity : entities) {
            this.cachedHealth.put(entity, entity.getHealth());
        }
    }

    @Override
    public int getTickInterval() {
        return 20;
    }
}
