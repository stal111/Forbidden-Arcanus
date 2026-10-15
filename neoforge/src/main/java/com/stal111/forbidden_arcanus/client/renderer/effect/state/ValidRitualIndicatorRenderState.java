package com.stal111.forbidden_arcanus.client.renderer.effect.state;

public class ValidRitualIndicatorRenderState {
    public boolean isVisible;
    public int lightCoords;
    public float ageInTicks;

    public static ValidRitualIndicatorRenderState create(int lightCoords, boolean isVisible, float ageInTicks) {
        ValidRitualIndicatorRenderState renderState = new ValidRitualIndicatorRenderState();
        renderState.isVisible = isVisible;
        renderState.lightCoords = lightCoords;
        renderState.ageInTicks = ageInTicks;

        return renderState;
    }
}
