package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import java.util.function.UnaryOperator;

public interface EssenceAccess {

    EntityCapability<EssenceAccess, Void> ENTITY_CAPABILITY = EntityCapability.createVoid(ForbiddenArcanus.identifier("essence_provider"), EssenceAccess.class);

    @Nullable
    EssenceStorage getEssence(EssenceType type);

    default Optional<EssenceStorage> getEssenceOptional(EssenceType type) {
        return Optional.ofNullable(this.getEssence(type));
    }

    void updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater);

    default boolean supports(EssenceType type) {
        return this.getEssence(type) != null;
    }

    default int getEssenceAmount(EssenceType type) {
        EssenceStorage storage = this.getEssence(type);
        return storage != null ? storage.amount() : 0;
    }

    default int getEssenceLimit(EssenceType type) {
        EssenceStorage storage = this.getEssence(type);
        return storage != null ? storage.limit() : 0;
    }

    default void setEssenceAmount(EssenceType type, int amount) {
        this.updateEssence(type, storage -> storage.setAmount(amount));
    }

    default void addEssence(EssenceType type, int amount) {
        this.updateEssence(type, storage -> storage.addEssence(amount));
    }

    default void setEssenceLimit(EssenceType type, int limit) {
        this.updateEssence(type, storage -> storage.setLimit(limit));
    }

    default void addEssences(EssenceSet essenceSet) {
        for (EssenceType type : EssenceType.values()) {
            this.addEssence(type, essenceSet.get(type));
        }
    }

    default void removeEssences(EssenceSet essenceSet) {
        for (EssenceType type : EssenceType.values()) {
            this.addEssence(type, -essenceSet.get(type));
        }
    }

    default boolean isEssenceFull(EssenceType type) {
        EssenceStorage storage = this.getEssence(type);
        return storage != null && storage.isFull();
    }
}
