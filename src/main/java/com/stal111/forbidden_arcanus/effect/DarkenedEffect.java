package com.stal111.forbidden_arcanus.effect;

import com.stal111.forbidden_arcanus.init.ModEffects;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;

public class DarkenedEffect extends Effect {

    public DarkenedEffect(EffectType effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isReady(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
        int i = livingEntity.getActivePotionEffect(ModEffects.DARKENED.get()).getDuration();
        if (i >= 20) {
            livingEntity.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 99999, 0, false, false, false));
        } else {
            livingEntity.removePotionEffect(Effects.BLINDNESS);
        }
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/darkened.png"));
    }
}
