package com.stal111.forbidden_arcanus.common.block.entity.forge;

public class HephaestusForgeClientData {

    private int ageInTicks;
    private int indicatorTicks;

    public void tick(boolean showIndicator) {
        this.ageInTicks++;
        this.indicatorTicks = showIndicator ? this.indicatorTicks + 1 : 0;
    }

    public int getAgeInTicks() {
        return this.ageInTicks;
    }

    public int getIndicatorTicks() {
        return this.indicatorTicks;
    }
}
