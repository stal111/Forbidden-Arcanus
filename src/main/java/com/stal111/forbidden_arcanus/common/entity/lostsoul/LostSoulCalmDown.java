package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.VillagerPanicTrigger;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.pathfinder.PathType;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * @author stal111
 * @since 2022-09-16
 */
public class LostSoulCalmDown extends Behavior<LostSoul> {

    private static final int SAFE_DISTANCE_FROM_DANGER = 15;

    private static final Function<LostSoul, Boolean> CLOSE_TO_ENTITY_THAT_HURT = lostSoul -> {
        return lostSoul.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY).filter(entity -> {
            return entity.distanceTo(lostSoul) <= SAFE_DISTANCE_FROM_DANGER;
        }).isPresent();
    };

    public LostSoulCalmDown() {
        super(ImmutableMap.of());
    }

    @Override
    protected void start(@Nonnull ServerLevel level, @Nonnull LostSoul entity, long gameTime) {
        boolean flag = VillagerPanicTrigger.isHurt(entity) || VillagerPanicTrigger.hasHostile(entity) || CLOSE_TO_ENTITY_THAT_HURT.apply(entity);

        if (!flag) {
            entity.getBrain().eraseMemory(MemoryModuleType.HURT_BY);
            entity.getBrain().eraseMemory(MemoryModuleType.HURT_BY_ENTITY);

            entity.getEntityData().set(LostSoul.DATA_SCARED, false);

            entity.setPathfindingMalus(PathType.BLOCKED, 16.0F);

            entity.getBrain().useDefaultActivity();
        }
    }
}
