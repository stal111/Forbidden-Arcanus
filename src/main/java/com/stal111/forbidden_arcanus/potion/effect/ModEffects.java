package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MOD_ID)
public class ModEffects {

    public static final Effect
            fly = null,
            spectral_vision = null,
            darkened = null,
            frozen = null;

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Effect> registry) {
        register("fly", new FlyEffect(EffectType.BENEFICIAL, 745784));
        register("spectral_vision", new SpectralEyeEffect(EffectType.BENEFICIAL, 745784));
        register("darkened", new DarkenedEffect(EffectType.BENEFICIAL, 745784)).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
        //register("frozen", (new FrozenEffect(EffectType.HARMFUL, 5926017)));
    }

    private static <T extends Effect> T register(String name, T effect) {
        effect.setRegistryName(ModUtils.location(name));
        ForgeRegistries.POTIONS.register(effect);
        return effect;
    }
}
