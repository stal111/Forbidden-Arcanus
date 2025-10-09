package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.util.Mth;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

import java.util.function.UnaryOperator;

/**
 * @author stal111
 * @since 17.09.2023
 */
public interface EssenceProvider {

    //TODO: Move this to a better place
    ItemCapability<EssenceProvider, Void> ITEM_ESSENCE = ItemCapability.createVoid(ForbiddenArcanus.location("essence_provider"), EssenceProvider.class);
    EntityCapability<EssenceProvider, Void> ENTITY_ESSENCE = EntityCapability.createVoid(ForbiddenArcanus.location("essence_provider"), EssenceProvider.class);

    EssenceStorage asStorage(EssenceType type);

    void setStorage(EssenceStorage storage);

    default int getAmount(EssenceType type) {
        return this.asStorage(type).amount();
    }

    default void setAmount(EssenceType type, int amount) {
        EssenceStorage storage = this.asStorage(type);

        this.setStorage(new EssenceStorage(type, Mth.clamp(amount, 0, storage.limit()), storage.limit()));
    }

    default void updateAmount(EssenceType type, UnaryOperator<Integer> operator) {
        this.setAmount(type, operator.apply(this.getAmount(type)));
    }

    default int getLimit(EssenceType type) {
        return this.asStorage(type).limit();
    }

    default void setLimit(EssenceType type, int limit) {
        EssenceStorage storage = this.asStorage(type);

        this.setStorage(new EssenceStorage(storage.type(), storage.amount(), limit));
    }

    default boolean isFull(EssenceType type) {
        return this.asStorage(type).isFull();
    }
}
