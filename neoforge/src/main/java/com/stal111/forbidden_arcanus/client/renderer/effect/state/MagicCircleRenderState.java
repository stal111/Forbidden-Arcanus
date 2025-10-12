package com.stal111.forbidden_arcanus.client.renderer.effect.state;

import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleController;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;

public class MagicCircleRenderState {
    public MagicCircleType magicCircleType;
    public int lightCoords;
    public float ageInTicks;
    public int duration;

    public static MagicCircleRenderState create(int lightCoords, int duration, MagicCircleController controller, float partialTick) {
        MagicCircleRenderState renderState = new MagicCircleRenderState();
        renderState.magicCircleType = controller.getMagicCircleType();
        renderState.lightCoords = lightCoords;
        renderState.ageInTicks = controller.getAgeInTicks(partialTick);
        renderState.duration = duration;

        return renderState;
    }
}
