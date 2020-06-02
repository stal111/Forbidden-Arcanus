package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.particle.ModBreakingParticle;
import com.stal111.forbidden_arcanus.particle.SoulParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, Main.MOD_ID);

    public static final RegistryObject<BasicParticleType> SOUL = register("soul", new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> ITEM_SEED_BULLET = register("item_seed_bullet", new BasicParticleType(false));

    private static <T extends BasicParticleType> RegistryObject<T> register(String name, T particleType) {
        return PARTICLE_TYPES.register(name, () -> particleType);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerParticleTypes(ParticleFactoryRegisterEvent event) {
        if (checkForNonNullWithReflection(ModParticles.SOUL)) {
            Minecraft.getInstance().particles.registerFactory(ModParticles.SOUL.get(), SoulParticle.Factory::new);
        }
        if (checkForNonNullWithReflection(ModParticles.ITEM_SEED_BULLET)) {
            Minecraft.getInstance().particles.registerFactory(ModParticles.ITEM_SEED_BULLET.get(), new ModBreakingParticle.Factory());
        }
    }

    private static boolean checkForNonNullWithReflection(RegistryObject<BasicParticleType> registryObject) {
        return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, registryObject, "value") != null;
    }
}
