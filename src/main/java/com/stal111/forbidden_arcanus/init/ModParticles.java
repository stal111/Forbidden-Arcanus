package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Main.MOD_ID);

    public static final RegistryObject<ParticleType<BasicParticleType>> SOUL = register("soul", new BasicParticleType(false));
    public static final RegistryObject<ParticleType<BasicParticleType>> ITEM_SEED_BULLET = register("item_seed_bullet", new BasicParticleType(false));

    private static <T extends BasicParticleType> RegistryObject<ParticleType<T>> register(String name, ParticleType<T> particleType) {
        return PARTICLE_TYPES.register(name, () -> particleType);
    }
}
