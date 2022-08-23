package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.valhelsia.valhelsia_core.core.registry.RegistryClass;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;

public class ModParticles implements RegistryClass {

    public static final RegistryHelper<ParticleType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(ForgeRegistries.Keys.PARTICLE_TYPES);

    public static final RegistryObject<SimpleParticleType> SOUL = HELPER.register("soul", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AUREAL_MOTE = HELPER.register("aureal_mote", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MAGIC_EXPLOSION = HELPER.register("magic_explosion", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HUGE_MAGIC_EXPLOSION = HELPER.register("magic_explosion_emitter", () -> new SimpleParticleType(true));
}
