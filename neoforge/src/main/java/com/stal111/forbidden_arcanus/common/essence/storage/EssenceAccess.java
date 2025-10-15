package com.stal111.forbidden_arcanus.common.essence.storage;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.neoforged.neoforge.capabilities.EntityCapability;

import java.util.function.UnaryOperator;

public interface EssenceAccess {

    EntityCapability<EssenceAccess, Void> ENTITY_CAPABILITY = EntityCapability.createVoid(ForbiddenArcanus.location("essence_provider"), EssenceAccess.class);

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
        return this.getEssence(type).isFull();
    }
}
