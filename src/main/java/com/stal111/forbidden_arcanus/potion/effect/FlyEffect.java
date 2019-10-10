package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class FlyEffect extends Effect {

    public FlyEffect(EffectType effectType, int p_i50391_2_) {
        super(effectType, p_i50391_2_);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity livingEntity, int amplifier) {
        int i = livingEntity.getActivePotionEffect(ModEffects.fly).getDuration();
        if (livingEntity instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) livingEntity;
            if (i >= 20) {
                playerEntity.abilities.allowFlying = true;
            } else {
                playerEntity.abilities.allowFlying = false;
                playerEntity.abilities.isFlying = false;
            }
        }
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/fly.png"));
    }
}
