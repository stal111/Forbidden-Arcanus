package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.particle.HugeMagicExplosionParticle;
import com.stal111.forbidden_arcanus.client.particle.AurealMoteParticle;
import com.stal111.forbidden_arcanus.client.particle.SoulParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SOUL = register("soul", new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AUREAL_MOTE = register("aureal_mote", new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MAGIC_EXPLOSION = register("magic_explosion", new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HUGE_MAGIC_EXPLOSION = register("magic_explosion_emitter", new SimpleParticleType(true));

    private static <T extends SimpleParticleType> RegistryObject<T> register(String name, T particleType) {
        return PARTICLE_TYPES.register(name, () -> particleType);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleTypes(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.SOUL.get(), SoulParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.AUREAL_MOTE.get(), AurealMoteParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.MAGIC_EXPLOSION.get(), HugeExplosionParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.HUGE_MAGIC_EXPLOSION.get(), new HugeMagicExplosionParticle.Factory());
    }
}
