package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-21
 */
public class DarkTraderSpawning<E extends DarkTrader> extends Behavior<E> {

    public DarkTraderSpawning(int duration) {
        super(ImmutableMap.of(MemoryModuleType.IS_EMERGING, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), duration);
    }

    protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
        return true;
    }

    protected void start(@NotNull ServerLevel level, E entity, long gameTime) {
        entity.setPose(Pose.EMERGING);
        //TODO
        //entity.playSound(SoundEvents.WARDEN_EMERGE, 5.0F, 1.0F);
    }

    protected void stop(ServerLevel level, E entity, long gameTime) {
        if (entity.hasPose(Pose.EMERGING)) {
            entity.setPose(Pose.STANDING);
        }
    }
}
