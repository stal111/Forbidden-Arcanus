package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.effect.DarkenedEffect;
import com.stal111.forbidden_arcanus.effect.FlyEffect;
import com.stal111.forbidden_arcanus.effect.SpectralEyeEffect;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {

    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, Main.MOD_ID);

    public static final RegistryObject<Effect> FLY = register("fly", new FlyEffect(EffectType.BENEFICIAL, 745784));
    public static final RegistryObject<Effect> SPECTRAL_VISION = register("spectral_vision", new SpectralEyeEffect(EffectType.BENEFICIAL, 745784));
    public static final RegistryObject<Effect> DARKENED = register("darkened", new DarkenedEffect(EffectType.HARMFUL,74578).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    private static <T extends Effect> RegistryObject<T> register(String name, T effect) {
        return EFFECTS.register(name, () -> effect);
    }
}
