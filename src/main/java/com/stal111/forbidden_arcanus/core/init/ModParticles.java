package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

public class ModParticles implements RegistryClass {

    public static final MappedRegistryHelper<ParticleType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.PARTICLE_TYPES);

    public static final RegistryEntry<SimpleParticleType> SOUL = HELPER.register("soul", () -> new SimpleParticleType(false));
    public static final RegistryEntry<SimpleParticleType> AUREAL_MOTE = HELPER.register("aureal_mote", () -> new SimpleParticleType(false));
    public static final RegistryEntry<SimpleParticleType> MAGIC_EXPLOSION = HELPER.register("magic_explosion", () -> new SimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> HUGE_MAGIC_EXPLOSION = HELPER.register("magic_explosion_emitter", () -> new SimpleParticleType(true));
    public static final RegistryEntry<SimpleParticleType> MAGNETIC_GLOW = HELPER.register("magnetic_glow", () -> new SimpleParticleType(true));
}
