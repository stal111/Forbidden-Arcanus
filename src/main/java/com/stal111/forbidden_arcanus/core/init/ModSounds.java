package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 2022-06-12
 */
public class ModSounds implements RegistryClass {

    public static final MappedRegistryHelper<SoundEvent> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.SOUND_EVENT);

    public static final RegistryEntry<SoundEvent, SoundEvent> ENERGY_BALL_LAUNCH = HELPER.register("energy_ball_launch", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("entity.energy_ball.launch")));
    public static final RegistryEntry<SoundEvent, SoundEvent> ENERGY_BALL_HIT = HELPER.register("energy_ball_hit", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("entity.energy_ball.hit")));
    public static final RegistryEntry<SoundEvent, SoundEvent> QUANTUM_CATCHER_PICK_UP = HELPER.register("quantum_catcher_pick_up", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.quantum_catcher.pick_up")));
    public static final RegistryEntry<SoundEvent, SoundEvent> QUANTUM_CATCHER_RELEASE = HELPER.register("quantum_catcher_release", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.quantum_catcher.release")));
    public static final RegistryEntry<SoundEvent, SoundEvent> FERROGNETIC_MIXTURE_APPLY = HELPER.register("ferrognetic_mixture_apply", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.ferrognetic_mixture.apply")));
    public static final RegistryEntry<SoundEvent, SoundEvent> CLIBANO_FIRE_CRACKLE = HELPER.register("clibano_fire_crackle", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("block.clibano.fire_crackle")));
    public static final RegistryEntry<SoundEvent, SoundEvent> CLIBANO_SOUL_FIRE_CRACKLE = HELPER.register("clibano_soul_fire_crackle", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("block.clibano.soul_fire_crackle")));
    public static final RegistryEntry<SoundEvent, SoundEvent> BLACKSMITH_GAVEL_RITUAL_START = HELPER.register("blacksmith_gavel_ritual_start", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.blacksmith_gavel.ritual_start")));
    public static final RegistryEntry<SoundEvent, SoundEvent> FORBIDDENOMICON_OPEN = HELPER.register("forbiddenomicon_open", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.forbiddenomicon.open")));
    public static final RegistryEntry<SoundEvent, SoundEvent> FORBIDDENOMICON_CLOSE = HELPER.register("forbiddenomicon_close", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.forbiddenomicon.close")));
    public static final RegistryEntry<SoundEvent, SoundEvent> MUNDABITUR_DUST_USE = HELPER.register("mundabitur_dust_use", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("item.mundabitur_dust.use")));
    public static final RegistryEntry<SoundEvent, SoundEvent> PEDESTAL_INTERACT = HELPER.register("pedestal_interact", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("block.pedestal.interact")));
    public static final RegistryEntry<SoundEvent, SoundEvent> OBSIDIAN_SKULL_CRACK = HELPER.register("obsidian_skull_crack", () -> SoundEvent.createVariableRangeEvent(ForbiddenArcanus.location("block.obsidian_skull.crack")));

}
