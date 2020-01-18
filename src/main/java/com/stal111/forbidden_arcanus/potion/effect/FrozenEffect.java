package com.stal111.forbidden_arcanus.potion.effect;

import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class FrozenEffect extends Effect {

    protected FrozenEffect(EffectType effectType, int color) {
        super(effectType, color);
    }

    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, int x, int y, float z, float alpha) {
        Minecraft.getInstance().getRenderManager().textureManager.bindTexture(ModUtils.location("textures/mob_effect/frozen.png"));
    }
}
