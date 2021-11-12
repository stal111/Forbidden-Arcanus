package com.stal111.forbidden_arcanus.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class FrozenEffect extends MobEffect {

    protected FrozenEffect(MobEffectCategory effectType, int color) {
        super(effectType, color);
    }

//    @Override
//    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
//        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/frozen.png"));
//    }
}
