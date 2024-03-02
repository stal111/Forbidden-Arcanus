package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.*;
import com.stal111.forbidden_arcanus.common.item.enhancer.effect.*;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class ModEnhancerEffects implements RegistryClass {

    public static final MappedRegistryHelper<EnhancerEffectType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.ENHANCER_EFFECT);

    public static final RegistryEntry<EnhancerEffectType<MultiplyRequiredEssenceEffect>> MULTIPLY_REQUIRED_ESSENCE = register("multiply_required_essence", MultiplyRequiredEssenceEffect.CODEC, EnhancerTarget.HEPHAESTUS_FORGE);
    public static final RegistryEntry<EnhancerEffectType<ModifyRequiredEssenceEffect>> MODIFY_REQUIRED_ESSENCE = register("modify_required_essence", ModifyRequiredEssenceEffect.CODEC, EnhancerTarget.HEPHAESTUS_FORGE);
    public static final RegistryEntry<EnhancerEffectType<MultiplySoulDurationEffect>> MULTIPLY_SOUL_DURATION = register("multiply_soul_duration", MultiplySoulDurationEffect.CODEC, EnhancerTarget.CLIBANO);

    public static <T extends EnhancerEffect> RegistryEntry<EnhancerEffectType<T>> register(String name, Codec<T> codec, EnhancerTarget target) {
        return HELPER.register(name, () -> new EnhancerEffectType<>(codec, target));
    }
}
