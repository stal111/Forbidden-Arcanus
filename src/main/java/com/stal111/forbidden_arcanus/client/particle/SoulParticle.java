package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

import javax.annotation.Nonnull;

/**
 * Soul Particle <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.particle.SoulParticle
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class SoulParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    private SoulParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(level, x, y, z, 0.5D - level.getRandom().nextDouble(), ySpeed, 0.5D - level.getRandom().nextDouble());
        this.sprites = spriteSet;
        this.yd *= 0.20000000298023224D;
        if (xSpeed == 0.0D && zSpeed == 0.0D) {
            this.xd *= 0.10000000149011612D;
            this.zd *= 0.10000000149011612D;
        }

        this.quadSize *= 1.1F;
        this.lifetime = (int)(9.5D / (level.getRandom().nextFloat() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.setSpriteFromAge(spriteSet);
    }

    @Nonnull
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.sprites);
            this.yd += 0.004D;
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.xd *= 0.9599999785423279D;
            this.yd *= 0.9599999785423279D;
            this.zd *= 0.9599999785423279D;
            if (this.onGround) {
                this.xd *= 0.699999988079071D;
                this.zd *= 0.699999988079071D;
            }

        }
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@Nonnull SimpleParticleType type, @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoulParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
