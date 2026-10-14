package com.stal111.forbidden_arcanus.common.block.entity.forge;

import com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle.MagicCircleClientData;

public class HephaestusForgeClientData {

    private final MagicCircleClientData magicCircleClientData = new MagicCircleClientData();
    private int ageInTicks;

    public void incrementAgeInTicks() {
        this.ageInTicks++;

        this.magicCircleClientData.incrementAgeInTicks();
    }

    public int getAgeInTicks() {
        return this.ageInTicks;
    }

    public MagicCircleClientData getMagicCircleClientData() {
        return this.magicCircleClientData;
    }
}
