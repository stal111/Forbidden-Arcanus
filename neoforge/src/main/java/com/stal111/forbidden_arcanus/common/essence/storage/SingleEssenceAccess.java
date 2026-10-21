package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import org.jspecify.annotations.Nullable;

import java.util.function.UnaryOperator;

public interface SingleEssenceAccess extends EssenceAccess {
    EssenceStorage getEssenceStorage();
    void setEssenceStorage(EssenceStorage storage);

    @Override
    default boolean supports(EssenceType type) {
        return this.getEssenceStorage().type() == type;
    }
    @Nullable
    @Override
    default EssenceStorage getEssence(EssenceType type) {
        return this.supports(type) ? this.getEssenceStorage() : null;
    }
    @Override
    default void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        if (this.supports(type)) {
            this.setEssenceStorage(updater.apply(this.getEssenceStorage()));
        }
    }
}
