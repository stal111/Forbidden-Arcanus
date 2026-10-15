package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle.MagicCircleClientData;

public class HephaestusForgeClientData {

    private final MagicCircleClientData magicCircleClientData = new MagicCircleClientData();
    private int ageInTicks;
    private int validRitualIndicatorTicks;

    public void incrementAgeInTicks(boolean canStartRitual) {
        this.ageInTicks++;
        this.validRitualIndicatorTicks = canStartRitual ? this.validRitualIndicatorTicks + 1 : 0;

        this.magicCircleClientData.incrementAgeInTicks();
    }

    public int getAgeInTicks() {
        return this.ageInTicks;
    }

    public MagicCircleClientData getMagicCircleClientData() {
        return this.magicCircleClientData;
    }

    public int getValidRitualIndicatorTicks() {
        return this.validRitualIndicatorTicks;
    }
}
