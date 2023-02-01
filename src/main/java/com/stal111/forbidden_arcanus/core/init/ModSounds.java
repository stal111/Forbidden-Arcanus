package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSounds implements RegistryClass {

    public static final MappedRegistryHelper<SoundEvent> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getMappedHelper(ForgeRegistries.Keys.SOUND_EVENTS);

    public static final RegistryObject<SoundEvent> ENERGY_BALL_LAUNCH = HELPER.register("energy_ball_launch", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity.energy_ball.launch")));
    public static final RegistryObject<SoundEvent> ENERGY_BALL_HIT = HELPER.register("energy_ball_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "entity.energy_ball.hit")));
    public static final RegistryObject<SoundEvent> QUANTUM_CATCHER_PICK_UP = HELPER.register("quantum_catcher_pick_up", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.quantum_catcher.pick_up")));
    public static final RegistryObject<SoundEvent> QUANTUM_CATCHER_RELEASE = HELPER.register("quantum_catcher_release", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.quantum_catcher.release")));
    public static final RegistryObject<SoundEvent> FERROGNETIC_MIXTURE_APPLY = HELPER.register("ferrognetic_mixture_apply", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "item.ferrognetic_mixture.apply")));
    public static final RegistryObject<SoundEvent> CLIBANO_FIRE_CRACKLE = HELPER.register("clibano_fire_crackle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "block.clibano.fire_crackle")));
    public static final RegistryObject<SoundEvent> CLIBANO_SOUL_FIRE_CRACKLE = HELPER.register("clibano_soul_fire_crackle", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ForbiddenArcanus.MOD_ID, "block.clibano.soul_fire_crackle")));

}
