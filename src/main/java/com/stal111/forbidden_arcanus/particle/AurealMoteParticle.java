package com.stal111.forbidden_arcanus.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Aureal Mote Particle
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.particle.AurealMoteParticle
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-28
 */
public class AurealMoteParticle extends SpriteTexturedParticle {

    private float alpha = 0.0F;

    private AurealMoteParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z, motionX, motionY, motionZ);
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.motionY = motionY;
        this.particleScale = 0.1F * (this.rand.nextFloat() * 0.3F + 0.8F);
        this.maxAge = 60 + this.rand.nextInt(12);
        this.setAlphaF(alpha);
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    @Override
    public float getScale(float scaleFactor) {
        float f = ((float) this.age + scaleFactor) / (float) this.maxAge;
        return this.particleScale * (1.0F - f * f * 0.5F);
    }

    @Override
    public void tick() {
        if (alpha < 1.0F) {
            alpha += 0.05F;
            this.setAlphaF(alpha);
        }
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.age++ >= this.maxAge) {
            this.setExpired();
        } else {
            this.move(0, this.motionY, 0);
            this.motionY *= 0.91;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BasicParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            AurealMoteParticle particle = new AurealMoteParticle(world, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(spriteSet);
            return particle;
        }
    }
}
