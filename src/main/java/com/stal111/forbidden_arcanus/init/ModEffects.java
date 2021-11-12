package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.effect.DarkenedEffect;
import com.stal111.forbidden_arcanus.effect.FlyEffect;
import com.stal111.forbidden_arcanus.effect.SpectralEyeEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<MobEffect> FLY = register("fly", new FlyEffect(MobEffectCategory.BENEFICIAL, 745784));
    public static final RegistryObject<MobEffect> SPECTRAL_VISION = register("spectral_vision", new SpectralEyeEffect(MobEffectCategory.BENEFICIAL, 745784));
    public static final RegistryObject<MobEffect> DARKENED = register("darkened", new DarkenedEffect(MobEffectCategory.HARMFUL,74578).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    private static <T extends MobEffect> RegistryObject<T> register(String name, T effect) {
        return EFFECTS.register(name, () -> effect);
    }
}
