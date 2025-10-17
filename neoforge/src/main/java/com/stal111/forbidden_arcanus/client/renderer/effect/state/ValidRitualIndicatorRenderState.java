package com.stal111.forbidden_arcanus.client.renderer.effect.state;

import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.ValidRitualIndicatorController;

public class ValidRitualIndicatorRenderState {
    public boolean isVisible;
    public int lightCoords;
    public float ageInTicks;

    public static ValidRitualIndicatorRenderState create(int lightCoords, ValidRitualIndicatorController controller, float partialTick) {
        ValidRitualIndicatorRenderState renderState = new ValidRitualIndicatorRenderState();
        renderState.isVisible = controller.hasIndicator();
        renderState.lightCoords = lightCoords;
        renderState.ageInTicks = controller.getAgeInTicks(partialTick);

        return renderState;
    }
}
