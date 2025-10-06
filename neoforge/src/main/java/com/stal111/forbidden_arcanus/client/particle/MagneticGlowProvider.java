package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public record MagneticGlowProvider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

    @Override
    public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        GlowParticle particle = new GlowParticle(level, x, y, z, 0.0D, 0.0D, 0.0D, this.spriteSet);

        particle.setParticleSpeed(xSpeed * 0.01D / 2.0D, ySpeed * 0.01D, zSpeed * 0.01D / 2.0D);
        particle.setLifetime(level.getRandom().nextInt(30) + 10);

        return particle;
    }
}
