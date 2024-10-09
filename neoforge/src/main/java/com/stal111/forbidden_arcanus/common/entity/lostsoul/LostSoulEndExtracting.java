package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2022-10-05
 */
public class LostSoulEndExtracting extends Behavior<LostSoul> {

    public LostSoulEndExtracting() {
        super(ImmutableMap.of());
    }

    @Override
    protected void start(@Nonnull ServerLevel level, @Nonnull LostSoul entity, long gameTime) {
        Brain<?> brain = entity.getBrain();

        brain.eraseMemory(MemoryModuleType.PATH);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
        brain.eraseMemory(MemoryModuleType.BREED_TARGET);
        brain.eraseMemory(MemoryModuleType.INTERACTION_TARGET);

        entity.setDeltaMovement(Vec3.ZERO);

        if (!entity.isExtracting()) {
            entity.getBrain().setActiveActivityIfPossible(Activity.PANIC);
        }
    }
}
