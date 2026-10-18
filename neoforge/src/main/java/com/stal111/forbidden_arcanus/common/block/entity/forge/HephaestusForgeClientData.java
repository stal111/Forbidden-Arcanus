package com.stal111.forbidden_arcanus.common.block.entity.forge;

public class HephaestusForgeClientData {

    private int ageInTicks;
    private int validRitualIndicatorTicks;
    private int ritualCounter;

    public void incrementAgeInTicks(boolean canStartRitual, boolean hasActiveRitual) {
        this.ageInTicks++;
        this.validRitualIndicatorTicks = canStartRitual || hasActiveRitual ? this.validRitualIndicatorTicks + 1 : 0;
        this.ritualCounter = hasActiveRitual ? this.ritualCounter + 1 : 0;
    }

    public int getAgeInTicks() {
        return this.ageInTicks;
    }

    public int getValidRitualIndicatorTicks() {
        return this.validRitualIndicatorTicks;
    }

    public int getRitualCounter() {
        return this.ritualCounter;
    }
}
