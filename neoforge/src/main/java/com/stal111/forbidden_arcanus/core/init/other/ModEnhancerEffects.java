package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerTarget;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.*;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.RegistryHelper;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class ModEnhancerEffects {

    public static final RegistryHelper<EnhancerEffectType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ENHANCER_EFFECT);

    public static final RegistryEntry<EnhancerEffectType<?>, EnhancerEffectType<MultiplyRequiredEssenceEffect>> MULTIPLY_REQUIRED_ESSENCE = register("multiply_required_essence", ConditionalEnhancerEffect.codec(MultiplyRequiredEssenceEffect.CODEC), EnhancerTarget.HEPHAESTUS_FORGE);
    public static final RegistryEntry<EnhancerEffectType<?>, EnhancerEffectType<ModifyRequiredEssenceEffect>> MODIFY_REQUIRED_ESSENCE = register("modify_required_essence", ConditionalEnhancerEffect.codec(ModifyRequiredEssenceEffect.CODEC), EnhancerTarget.HEPHAESTUS_FORGE);
    public static final RegistryEntry<EnhancerEffectType<?>, EnhancerEffectType<MultiplySoulDurationEffect>> MULTIPLY_SOUL_DURATION = register("multiply_soul_duration", ConditionalEnhancerEffect.codec(MultiplySoulDurationEffect.CODEC), EnhancerTarget.CLIBANO);

    public static <T extends EnhancerEffect> RegistryEntry<EnhancerEffectType<?>, EnhancerEffectType<T>> register(String name, MapCodec<ConditionalEnhancerEffect<T>> codec, EnhancerTarget target) {
        return HELPER.register(name, () -> new EnhancerEffectType<>(codec, target));
    }
}
