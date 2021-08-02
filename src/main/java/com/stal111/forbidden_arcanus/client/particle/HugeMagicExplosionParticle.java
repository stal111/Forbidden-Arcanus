package com.stal111.forbidden_arcanus.client.particle;

import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;

import javax.annotation.Nonnull;

/**
 * Magic Smoke Particle
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.particle.MagicSmokeParticle
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-08-01
 */
public class HugeMagicExplosionParticle extends MetaParticle {

    protected HugeMagicExplosionParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.maxAge = 9;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; i++) {
            double x = this.posX + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double y = this.posY + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;
            double z = this.posZ + (this.rand.nextDouble() - this.rand.nextDouble()) * 4.0D;

            this.world.addParticle(ModParticles.MAGIC_EXPLOSION.get(), x, y, z, (float) this.age / (float) this.maxAge, 0.0D, 0.0D);
        }

        if (this.age++ >= this.maxAge) {
            this.setExpired();
        }
    }

    public static class Factory implements IParticleFactory<BasicParticleType> {
        public Particle makeParticle(@Nonnull BasicParticleType type, @Nonnull ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HugeMagicExplosionParticle(world, x, y, z);
        }
    }
}
