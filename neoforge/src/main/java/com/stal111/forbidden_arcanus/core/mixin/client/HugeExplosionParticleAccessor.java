package com.stal111.forbidden_arcanus.core.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.SpriteSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HugeExplosionParticle.class)
public interface HugeExplosionParticleAccessor {
    @Invoker("<init>")
    static HugeExplosionParticle createParticle(ClientLevel level, double x, double y, double z, double quadSizeMultiplier, SpriteSet sprites) {
        throw new UnsupportedOperationException();
    }
}
