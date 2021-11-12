package com.stal111.forbidden_arcanus.effect;

import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;

public class DarkenedEffect extends MobEffect {

    public DarkenedEffect(MobEffectCategory effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isDurationEffectTick(int p_76397_1_, int p_76397_2_) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int i = livingEntity.getEffect(ModEffects.DARKENED.get()).getDuration();
        if (i >= 20) {
            livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 99999, 0, false, false, false));
        } else {
            livingEntity.removeEffect(MobEffects.BLINDNESS);
        }
    }

//    @Override
//    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/darkened.png"));
//    }
}
