package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.util.RandomSource;

public record SpellExplosionProvider(SpriteSet spriteSet) implements ParticleProvider<ColorParticleOption> {

    @Override
    public Particle createParticle(ColorParticleOption particleOption, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        HugeExplosionParticle particle = new HugeExplosionParticle(level, x, y, z, xSpeed, this.spriteSet);
        particle.setColor(particleOption.getRed(), particleOption.getGreen(), particleOption.getBlue());

        return particle;
    }
}
