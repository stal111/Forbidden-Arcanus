package com.stal111.forbidden_arcanus.core.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.SpriteSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GlowParticle.class)
public interface GlowParticleAccessor {
    @Invoker("<init>")
    static GlowParticle createGlowParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        throw new UnsupportedOperationException();
    }
}
