package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
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
        return this.asStorage(type).data().amount();
    }

    default void setAmount(EssenceType type, int amount) {
        EssenceStorage storage = this.asStorage(type);

        this.setStorage(new EssenceStorage(EssenceData.of(type, Math.min(amount, storage.limit())), storage.limit(), storage.showInTooltip()));
    }

    default void updateAmount(EssenceType type, UnaryOperator<Integer> operator) {
        this.setAmount(type, operator.apply(this.getAmount(type)));
    }

    default int getLimit(EssenceType type) {
        return this.asStorage(type).limit();
    }

    default void setLimit(EssenceType type, int limit) {
        EssenceStorage storage = this.asStorage(type);

        this.setStorage(new EssenceStorage(storage.data(), limit, storage.showInTooltip()));
    }

    default boolean isFull(EssenceType type) {
        return this.asStorage(type).isFull();
    }
}
