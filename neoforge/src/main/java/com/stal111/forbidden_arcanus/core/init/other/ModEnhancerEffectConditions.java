package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectConditionType;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.TimeCondition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-03-03
 */
public class ModEnhancerEffectConditions implements RegistryClass {

    public static final MappedRegistryHelper<EffectConditionType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ENHANCER_EFFECT_CONDITION);

    public static final RegistryEntry<EffectConditionType<?>, EffectConditionType<TimeCondition>> TIME = register("time", TimeCondition.CODEC);

    public static <T extends EffectCondition> RegistryEntry<EffectConditionType<?>, EffectConditionType<T>> register(String name, MapCodec<T> codec) {
        return HELPER.register(name, () -> new EffectConditionType<>(codec));
    }
}
