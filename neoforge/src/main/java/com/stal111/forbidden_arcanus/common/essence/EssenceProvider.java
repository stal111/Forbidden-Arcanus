package com.stal111.forbidden_arcanus.common.essence;

import net.neoforged.neoforge.common.MutableDataComponentHolder;

public interface EssenceProvider {
    EssenceValue getEssenceValue();

    default void extract(MutableDataComponentHolder componentHolder, int amount) {

    }

    default EssenceValue getExtractableValue(boolean extractMaximum) {
        EssenceValue value = getEssenceValue();

        if (extractMaximum) {
            return value;
        }

        return new EssenceValue(value.type(), Math.min(value.amount(), this.getExtractionSpeed()));
    }

    default int getExtractionSpeed() {
        return this.getEssenceValue().amount();
    }
}
