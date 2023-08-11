package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.effect.DarkenedEffect;
import com.stal111.forbidden_arcanus.common.effect.SpectralEyeEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModMobEffects implements RegistryClass {

    public static final MappedRegistryHelper<MobEffect> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.MOB_EFFECT);

    public static final RegistryEntry<MobEffect> SPECTRAL_VISION = HELPER.register("spectral_vision", () -> new SpectralEyeEffect(MobEffectCategory.BENEFICIAL, 745784));
    public static final RegistryEntry<MobEffect> DARKENED = HELPER.register("darkened", () -> new DarkenedEffect(MobEffectCategory.HARMFUL,74578).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));

}
