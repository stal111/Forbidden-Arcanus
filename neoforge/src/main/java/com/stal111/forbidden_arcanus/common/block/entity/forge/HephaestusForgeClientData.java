package com.stal111.forbidden_arcanus.common.block.entity.forge;

public class HephaestusForgeClientData {

    private int ageInTicks;
    private int validRitualIndicatorTicks;

    public void incrementAgeInTicks(boolean showIndicator) {
        this.ageInTicks++;
        this.validRitualIndicatorTicks = showIndicator ? this.validRitualIndicatorTicks + 1 : 0;
    }

    public int getAgeInTicks() {
        return this.ageInTicks;
    }

    public int getValidRitualIndicatorTicks() {
        return this.validRitualIndicatorTicks;
    }
}
