package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSounds implements RegistryClass {

    public static final RegistryHelper<SoundEvent> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.SOUND_EVENTS);

    public static final RegistryObject<SoundEvent> ENERGY_BALL_LAUNCH = HELPER.register("energy_ball_launch", () -> new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity.energy_ball.launch")));
    public static final RegistryObject<SoundEvent> ENERGY_BALL_HIT = HELPER.register("energy_ball_hit", () -> new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity.energy_ball.hit")));
    public static final RegistryObject<SoundEvent> QUANTUM_CATCHER_PICK_UP = HELPER.register("quantum_catcher_pick_up", () -> new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.quantum_catcher.pick_up")));
    public static final RegistryObject<SoundEvent> QUANTUM_CATCHER_RELEASE = HELPER.register("quantum_catcher_release", () -> new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.quantum_catcher.release")));
    public static final RegistryObject<SoundEvent> FERROGNETIC_MIXTURE_APPLY = HELPER.register("ferrognetic_mixture_apply", () -> new SoundEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.ferrognetic_mixture.apply")));

}
