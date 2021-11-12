package com.stal111.forbidden_arcanus.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SoulParticle extends TextureSheetParticle {

    private static final Random RANDOM = new Random();
    private final SpriteSet sprites;

    private SoulParticle(ClientLevel world, double p_i51008_2_, double p_i51008_4_, double p_i51008_6_, double p_i51008_8_, double p_i51008_10_, double p_i51008_12_, SpriteSet p_i51008_14_) {
        super(world, p_i51008_2_, p_i51008_4_, p_i51008_6_, 0.5D - RANDOM.nextDouble(), p_i51008_10_, 0.5D - RANDOM.nextDouble());
        this.sprites = p_i51008_14_;
        this.yd *= 0.20000000298023224D;
        if (p_i51008_8_ == 0.0D && p_i51008_12_ == 0.0D) {
            this.xd *= 0.10000000149011612D;
            this.zd *= 0.10000000149011612D;
        }

        this.quadSize *= 1.1F;
        this.lifetime = (int)(9.5D / (Math.random() * 0.8D + 0.2D));
        this.hasPhysics = false;
        this.setSpriteFromAge(p_i51008_14_);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

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

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i50843_1_) {
            this.spriteSet = p_i50843_1_;
        }


        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new SoulParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
        }
    }
}
