package com.stal111.forbidden_arcanus.common.entity.darktrader;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;

/**
 * @author stal111
 * @since 2023-08-21
 */
public class DarkTraderAI {

    public static final int SPAWN_DURATION = 60;

    private static final List<SensorType<? extends Sensor<? super DarkTrader>>> SENSOR_TYPES = List.of(SensorType.NEAREST_PLAYERS);
    private static final List<MemoryModuleType<?>> MEMORY_TYPES = List.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.IS_EMERGING);

    public static void updateActivity(DarkTrader trader) {
        trader.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.EMERGE));
    }

    protected static Brain<?> makeBrain(DarkTrader trader, Dynamic<?> dynamic) {
        Brain.Provider<DarkTrader> provider = Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
        Brain<DarkTrader> brain = provider.makeBrain(dynamic);
        initCoreActivity(brain);
        initSpawnActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void initCoreActivity(Brain<DarkTrader> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new LookAtTargetSink(45, 90), new MoveToTargetSink()));
    }

    private static void initSpawnActivity(Brain<DarkTrader> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.EMERGE, 5, ImmutableList.of(new DarkTraderSpawning<>(SPAWN_DURATION)), MemoryModuleType.IS_EMERGING);
    }
}
