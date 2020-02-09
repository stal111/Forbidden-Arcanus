package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.potion.effect.DarkenedEffect;
import com.stal111.forbidden_arcanus.potion.effect.FlyEffect;
import com.stal111.forbidden_arcanus.potion.effect.SpectralEyeEffect;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEffects {

    public static final Effect FLY = register("fly", new FlyEffect(EffectType.BENEFICIAL, 745784));
    public static final Effect SPECTRAL_VISION = register("spectral_vision", new SpectralEyeEffect(EffectType.BENEFICIAL, 745784));
    public static final Effect DARKENED = register("darkened", new DarkenedEffect(EffectType.HARMFUL,74578).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    @SubscribeEvent
    public static void registerEffects(RegistryEvent.Register<Effect> event) {
        Main.LOGGER.debug("Registering Effects... [Total Count: " + Data.EFFECTS.size() + "]");
        Data.EFFECTS.forEach(effect -> event.getRegistry().register(effect));
    }

    private static <T extends Effect> T register(String name, T effect) {
        effect.setRegistryName(ModUtils.location(name));
        Data.EFFECTS.add(effect);
        return effect;
    }
}
