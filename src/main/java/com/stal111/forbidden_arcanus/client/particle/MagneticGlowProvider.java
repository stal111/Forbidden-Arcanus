package com.stal111.forbidden_arcanus.client.particle;

import com.stal111.forbidden_arcanus.core.mixin.GlowParticleAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2023-01-03
 */
public class MagneticGlowProvider implements ParticleProvider<SimpleParticleType> {

    private final SpriteSet spriteSet;

    public MagneticGlowProvider(SpriteSet spriteSet) {
        this.spriteSet = spriteSet;
    }

    @Nullable
    @Override
    public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        GlowParticle particle = GlowParticleAccessor.createGlowParticle(level, x, y, z, 0.0D, 0.0D, 0.0D, this.spriteSet);

        particle.setParticleSpeed(xSpeed * 0.01D / 2.0D, ySpeed * 0.01D, zSpeed * 0.01D / 2.0D);
        particle.setLifetime(level.getRandom().nextInt(30) + 10);

        return particle;
    }
}
