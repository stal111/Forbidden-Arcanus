package com.stal111.forbidden_arcanus.capability.eternalStellaActive;

public class EternalStellaActive implements IEternalStellaActive {

    private boolean eternalStellaActive = false;

    @Override
    public boolean getEternalStellaActive() {
        return eternalStellaActive;
    }

    @Override
    public void setEternalStellaActive(boolean avtive) {
        this.eternalStellaActive = avtive;
    }
}
