package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.effect.SpectralEyeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModMobEffects implements RegistryClass {

    public static final MappedRegistryHelper<MobEffect> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.MOB_EFFECT);

    public static final RegistryEntry<MobEffect, SpectralEyeEffect> SPECTRAL_VISION = HELPER.register("spectral_vision", () -> new SpectralEyeEffect(MobEffectCategory.BENEFICIAL, 745784));

}
