package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;

/**
 * @author stal111
 * @since 2023-08-21
 */
public class DarkTraderAI {

    public static final int SPAWN_DURATION = 60;

    public static void updateActivity(DarkTrader trader) {
        trader.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.EMERGE));
    }

    protected static List<ActivityData<DarkTrader>> getActivities() {
        return List.of(initCoreActivity(), initSpawnActivity());
    }

    private static ActivityData<DarkTrader> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(new Swim<>(0.8F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static ActivityData<DarkTrader> initSpawnActivity() {
        return ActivityData.create(Activity.EMERGE, 5, ImmutableList.of(new DarkTraderSpawning<>(SPAWN_DURATION)), MemoryModuleType.IS_EMERGING);
    }
}
