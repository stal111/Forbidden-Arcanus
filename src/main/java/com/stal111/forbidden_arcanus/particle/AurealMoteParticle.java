package com.stal111.forbidden_arcanus.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

/**
 * Aureal Mote Particle
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.particle.AurealMoteParticle
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-28
 */
public class AurealMoteParticle extends TextureSheetParticle {

    private float alpha = 0.0F;

    private AurealMoteParticle(ClientLevel world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.x = x;
        this.y = y;
        this.z = z;
        this.yd = motionY;
        this.quadSize = 0.1F * (this.random.nextFloat() * 0.3F + 0.8F);
        this.lifetime = 60 + this.random.nextInt(12);
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
            this.move(0, this.yd, 0);
            this.yd *= 0.91;
        }
    }

    @Override
    protected int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AurealMoteParticle particle = new AurealMoteParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(spriteSet);
            return particle;
        }
    }
}
