package com.stal111.forbidden_arcanus.client.particle;

import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nonnull;

/**
 * Huge Magic Explosion Particle <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.particle.HugeMagicExplosionParticle
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-08-01
 */
public class HugeMagicExplosionParticle extends NoRenderParticle {

    protected HugeMagicExplosionParticle(ClientLevel world, double x, double y, double z) {
        super(world, x, y, z);
        this.lifetime = 9;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; i++) {
            double x = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
            double y = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;
            double z = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0D;

            this.level.addParticle(ModParticles.MAGIC_EXPLOSION.get(), x, y, z, (float) this.age / (float) this.lifetime, 0.0D, 0.0D);
        }

        if (this.age++ >= this.lifetime) {
            this.remove();
        }
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        public Particle createParticle(@Nonnull SimpleParticleType type, @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HugeMagicExplosionParticle(level, x, y, z);
        }
    }
}
