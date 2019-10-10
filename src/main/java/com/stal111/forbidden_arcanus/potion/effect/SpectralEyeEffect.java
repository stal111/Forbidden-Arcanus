package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.*;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class SpectralEyeEffect extends Effect {

    protected SpectralEyeEffect(EffectType effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        double k = entity.posX;
        double l = entity.posY;
        double i1 = entity.posZ;

        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(k, l, i1, (k + 1), (l + 1), (i1 + 1))).grow(70).expand(0.0D, (double)entity.world.getHeight(), 0.0D);
        List<LivingEntity> list = entity.world.getEntitiesWithinAABB(LivingEntity.class, axisalignedbb);

        for(LivingEntity livingEntity : list) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.GLOWING, 10, 0, true, true, false));
        }
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/spectral_vision.png"));
    }
}
