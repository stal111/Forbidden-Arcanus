package com.stal111.forbidden_arcanus.core.init.other;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.item.enhancer.*;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class ModEnhancerEffects implements RegistryClass {

    public static final MappedRegistryHelper<EnhancerEffectType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(FARegistries.ENHANCER_EFFECT);

    public static final RegistryObject<EnhancerEffectType<MultiplyRequiredEssenceEffect>> MULTIPLY_REQUIRED_ESSENCE = register("multiply_required_essence", MultiplyRequiredEssenceEffect.CODEC, EnhancerTarget.HEPHAESTUS_FORGE);
    public static final RegistryObject<EnhancerEffectType<ModifyRequiredEssenceEffect>> MODIFY_REQUIRED_ESSENCE = register("modify_required_essence", ModifyRequiredEssenceEffect.CODEC, EnhancerTarget.HEPHAESTUS_FORGE);

    public static <T extends EnhancerEffect> RegistryObject<EnhancerEffectType<T>> register(String name, Codec<T> codec, EnhancerTarget target) {
        return HELPER.register(name, () -> new EnhancerEffectType<>(codec, target));
    }
}
