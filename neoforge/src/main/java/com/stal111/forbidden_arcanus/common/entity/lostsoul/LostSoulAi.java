package com.stal111.forbidden_arcanus.common.entity.lostsoul;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.core.init.ModMemoryModules;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;

import java.util.List;

/**
 * @author stal111
 * @since 2022-09-15
 */
public class LostSoulAi {

    protected static List<ActivityData<AbstractLostSoul>> getActivities() {
        return List.of(initCoreActivity(), initIdleActivity(), initPanicActivity());
    }

    private static ActivityData<AbstractLostSoul> initCoreActivity() {
        return ActivityData.create(Activity.CORE, 0, ImmutableList.of(new LookAtTargetSink(45, 90), new MoveToTargetSink(), new CountDownCooldownTicks(ModMemoryModules.SCARED_TIME.get())));
    }

    private static ActivityData<AbstractLostSoul> initIdleActivity() {
        return ActivityData.create(Activity.IDLE, ImmutableList.of(Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), Pair.of(1, new RunOne<>(ImmutableList.of(Pair.of(RandomStroll.fly(1.0F), 2), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 2), Pair.of(new DoNothing(30, 60), 1))))), ImmutableSet.of());
    }

    private static ActivityData<AbstractLostSoul> initPanicActivity() {
        return ActivityData.create(Activity.PANIC, ImmutableList.of(Pair.of(0, new LostSoulCalmDown()), Pair.of(1, SetWalkTargetAwayFrom.entity(MemoryModuleType.HURT_BY_ENTITY, 1.75F, 15, false)), Pair.of(2, RandomStroll.fly(1.0F))), ImmutableSet.of());
    }
}
