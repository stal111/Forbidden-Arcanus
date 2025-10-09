package com.stal111.forbidden_arcanus.common.essence;

import java.util.function.UnaryOperator;

public interface EssenceAccess {
    EssenceStorage getEssence(EssenceType type);
    void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater);

    default int getEssenceAmount(EssenceType type) {
        return this.getEssence(type).amount();
    }

    default int getEssenceLimit(EssenceType type) {
        return this.getEssence(type).limit();
    }

    default void setEssenceAmount(EssenceType type, int amount) {
        this.updateEssence(type, storage -> storage.setAmount(amount));
    }

    default void addEssence(EssenceType type, int amount) {
        this.updateEssence(type, storage -> storage.addEssence(amount));
    }

    default boolean isEssenceFull(EssenceType type) {
        return this.getEssence(type).isFull();
    }
}
