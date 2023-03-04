package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectCondition;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.EffectConditionType;
import com.stal111.forbidden_arcanus.common.item.enhancer.condition.TimeCondition;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-03-03
 */
public class ModEnhancerEffectConditions implements RegistryClass {

    public static final MappedRegistryHelper<EffectConditionType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.ENHANCER_EFFECT_CONDITION);

    public static final RegistryObject<EffectConditionType<TimeCondition>> TIME = register("time", TimeCondition.CODEC);

    public static <T extends EffectCondition> RegistryObject<EffectConditionType<T>> register(String name, Codec<T> codec) {
        return HELPER.register(name, () -> new EffectConditionType<>(codec));
    }
}
