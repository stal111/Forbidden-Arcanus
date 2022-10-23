package com.stal111.forbidden_arcanus.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.valueproviders.UniformInt;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2021-01-28
 */
public class AurealMoteParticle extends TextureSheetParticle {

    private static final UniformInt DEFAULT_LIFE_TIME = UniformInt.of(60, 72);

    private float alpha = 0.0F;

    private AurealMoteParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yd = ySpeed;

        if (xSpeed == 0.0D) {
            this.xd = 0.0D;
        }

        if (zSpeed == 0.0D) {
            this.zd = 0.0D;
        }

        this.quadSize = 0.1F * (this.random.nextFloat() * 0.3F + 0.8F);
        this.lifetime = DEFAULT_LIFE_TIME.sample(this.random);
        this.setAlpha(alpha);
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        float f = ((float) this.age + scaleFactor) / (float) this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    @Override
    public void tick() {
        if (alpha < 1.0F) {
            alpha += 0.05F;
            this.setAlpha(alpha);
        }
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.yd *= 0.91;
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    public record Factory(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {

        @Override
        public Particle createParticle(@Nonnull SimpleParticleType type, @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AurealMoteParticle particle = new AurealMoteParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet);
            return particle;
        }
    }
}
