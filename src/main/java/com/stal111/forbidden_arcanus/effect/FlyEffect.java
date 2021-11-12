package com.stal111.forbidden_arcanus.effect;

import com.stal111.forbidden_arcanus.init.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FlyEffect extends MobEffect {

    public FlyEffect(MobEffectCategory effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        int i = livingEntity.getEffect(ModEffects.FLY.get()).getDuration();
        if (livingEntity instanceof Player) {
            Player player = (Player) livingEntity;
            if (i <= 30 && !player.getAbilities().instabuild) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            } else {
                player.getAbilities().mayfly = true;
            }
        }
    }

//    @Override
//    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/fly.png"));
//    }
}
