package com.stal111.forbidden_arcanus.particle;

import com.stal111.forbidden_arcanus.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class ModBreakingParticle extends SpriteTexturedParticle {

    private final float field_217571_C;
    private final float field_217572_F;

    private ModBreakingParticle(World p_i47644_1_, double p_i47644_2_, double p_i47644_4_, double p_i47644_6_, double p_i47644_8_, double p_i47644_10_, double p_i47644_12_, ItemStack p_i47644_14_) {
        this(p_i47644_1_, p_i47644_2_, p_i47644_4_, p_i47644_6_, p_i47644_14_);
        this.motionX *= (double)0.1F;
        this.motionY *= (double)0.1F;
        this.motionZ *= (double)0.1F;
        this.motionX += p_i47644_8_;
        this.motionY += p_i47644_10_;
        this.motionZ += p_i47644_12_;
    }

    public ModBreakingParticle(World p_i47645_1_, double p_i47645_2_, double p_i47645_4_, double p_i47645_6_, ItemStack p_i47645_8_) {
        super(p_i47645_1_, p_i47645_2_, p_i47645_4_, p_i47645_6_, 0.0D, 0.0D, 0.0D);
        this.setSprite(Minecraft.getInstance().getItemRenderer().getModelWithOverrides(p_i47645_8_, p_i47645_1_, (LivingEntity)null).getParticleTexture());
        this.particleGravity = 1.0F;
        this.particleScale /= 2.0F;
        this.field_217571_C = this.rand.nextFloat() * 3.0F;
        this.field_217572_F = this.rand.nextFloat() * 3.0F;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.TERRAIN_SHEET;
    }

    protected float getMinU() {
        return this.sprite.getInterpolatedU((double)((this.field_217571_C + 1.0F) / 4.0F * 16.0F));
    }

    protected float getMaxU() {
        return this.sprite.getInterpolatedU((double)(this.field_217571_C / 4.0F * 16.0F));
    }

    protected float getMinV() {
        return this.sprite.getInterpolatedV((double)(this.field_217572_F / 4.0F * 16.0F));
    }

    protected float getMaxV() {
        return this.sprite.getInterpolatedV((double)((this.field_217572_F + 1.0F) / 4.0F * 16.0F));
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {

        @Nullable
        @Override
        public Particle makeParticle(BasicParticleType typeIn, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ModBreakingParticle(world, x, y, z, new ItemStack(ModItems.seed_bullet));
        }
    }
}
