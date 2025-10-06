package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public class FullbrightAnimatedParticle extends SimpleAnimatedParticle {

    private final SpriteSet sprites;

    protected FullbrightAnimatedParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet) {
        super(level, x, y, z, spriteSet, 0.0F);
        this.sprites = spriteSet;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @Override
    public Layer getLayer() {
        return Layer.OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        return LightTexture.FULL_BLOCK;
    }

    public record Factory(SpriteSet spriteSet, int lifetime) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            var particle = new FullbrightAnimatedParticle(level, x, y, z, this.spriteSet);
            particle.setLifetime(lifetime);

            return particle;
        }
    }
}
