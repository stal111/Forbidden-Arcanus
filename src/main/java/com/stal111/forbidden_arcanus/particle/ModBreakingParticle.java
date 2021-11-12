package com.stal111.forbidden_arcanus.particle;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class ModBreakingParticle extends TextureSheetParticle {

    private final float uo;
    private final float vo;

    private ModBreakingParticle(ClientLevel p_i47644_1_, double p_i47644_2_, double p_i47644_4_, double p_i47644_6_, double p_i47644_8_, double p_i47644_10_, double p_i47644_12_, ItemStack p_i47644_14_) {
        this(p_i47644_1_, p_i47644_2_, p_i47644_4_, p_i47644_6_, p_i47644_14_);
        this.xd *= (double)0.1F;
        this.yd *= (double)0.1F;
        this.zd *= (double)0.1F;
        this.xd += p_i47644_8_;
        this.yd += p_i47644_10_;
        this.zd += p_i47644_12_;
    }

    public ModBreakingParticle(ClientLevel p_i47645_1_, double p_i47645_2_, double p_i47645_4_, double p_i47645_6_, ItemStack p_i47645_8_) {
        super(p_i47645_1_, p_i47645_2_, p_i47645_4_, p_i47645_6_, 0.0D, 0.0D, 0.0D);
        //this.setSprite(Minecraft.getInstance().getItemRenderer().getModel(p_i47645_8_, p_i47645_1_, (LivingEntity)null).getParticleIcon());
        this.gravity = 1.0F;
        this.quadSize /= 2.0F;
        this.uo = this.random.nextFloat() * 3.0F;
        this.vo = this.random.nextFloat() * 3.0F;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    protected float getU0() {
        return this.sprite.getU((double)((this.uo + 1.0F) / 4.0F * 16.0F));
    }

    protected float getU1() {
        return this.sprite.getU((double)(this.uo / 4.0F * 16.0F));
    }

    protected float getV0() {
        return this.sprite.getV((double)(this.vo / 4.0F * 16.0F));
    }

    protected float getV1() {
        return this.sprite.getV((double)((this.vo + 1.0F) / 4.0F * 16.0F));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType typeIn, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ModBreakingParticle(world, x, y, z, new ItemStack(ModItems.SEED_BULLET.get()));
        }
    }
}
