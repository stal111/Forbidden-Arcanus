package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Main.MOD_ID)
public class ModEffects {

    public static final Effect
            fly = null,
            spectral_vision = null;

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Effect> registry) {
        registerAll(registry,
                 register("fly", new BasicEffect(EffectType.BENEFICIAL, 745784)),
                 register("spectral_vision", new SpectralEyeEffect(EffectType.BENEFICIAL, 745784)));
    }

    public static Effect register(String name, Effect effect) {
        return effect.setRegistryName(ModUtils.location(name));
    }

    public static void registerAll(RegistryEvent.Register<Effect> registry, Effect... effects) {
        for (Effect effect : effects) {
            registry.getRegistry().register(effect);
        }
    }
}
