package com.stal111.forbidden_arcanus.common.effect;

import com.stal111.forbidden_arcanus.core.init.ModMobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

/**
 * Darkened Effect <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.effect.DarkenedEffect
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class DarkenedEffect extends MobEffect {

    public DarkenedEffect(MobEffectCategory effectCategory, int color) {
        super(effectCategory, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int i = livingEntity.getEffect(ModMobEffects.DARKENED).getDuration();
        if (i >= 20) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 99999, 0, false, false, false));
        } else {
            livingEntity.removeEffect(MobEffects.BLINDNESS);
        }

        return true;
    }
}
