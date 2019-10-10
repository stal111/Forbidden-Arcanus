package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModParticles {

    public static final BasicParticleType
            soul = null,
            item_seed_bullet = null;

    public static void register(RegistryEvent.Register<ParticleType<?>> registry) {
        register("soul", new BasicParticleType(false));
        register("item_seed_bullet", new BasicParticleType(false));
    }

    private static <T extends ParticleType<?>> T register(String name, T particleType) {
        particleType.setRegistryName(name);
        ForgeRegistries.PARTICLE_TYPES.register(particleType);
        return particleType;
    }

}
