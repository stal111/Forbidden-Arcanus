package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.particle.HugeMagicExplosionParticle;
import com.stal111.forbidden_arcanus.particle.AurealMoteParticle;
import com.stal111.forbidden_arcanus.particle.ModBreakingParticle;
import com.stal111.forbidden_arcanus.particle.SoulParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.LargeExplosionParticle;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<BasicParticleType> SOUL = register("soul", new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> ITEM_SEED_BULLET = register("item_seed_bullet", new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> AUREAL_MOTE = register("aureal_mote", new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> MAGIC_EXPLOSION = register("magic_explosion", new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> HUGE_MAGIC_EXPLOSION = register("magic_explosion_emitter", new BasicParticleType(true));

    private static <T extends BasicParticleType> RegistryObject<T> register(String name, T particleType) {
        return PARTICLE_TYPES.register(name, () -> particleType);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleTypes(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.SOUL.get(), SoulParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.ITEM_SEED_BULLET.get(), new ModBreakingParticle.Factory());
        Minecraft.getInstance().particles.registerFactory(ModParticles.AUREAL_MOTE.get(), AurealMoteParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.MAGIC_EXPLOSION.get(), LargeExplosionParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ModParticles.HUGE_MAGIC_EXPLOSION.get(), new HugeMagicExplosionParticle.Factory());
    }
}
