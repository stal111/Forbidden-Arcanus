package com.stal111.forbidden_arcanus.common.aureal;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

/**
 * @author stal111
 * @since 17.09.2023
 */
public interface AurealProvider {

    //TODO: Move this to a better place
    ItemCapability<AurealProvider, Void> ITEM_AUREAL = ItemCapability.createVoid(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aureal_provider"), AurealProvider.class);
    EntityCapability<AurealProvider, Void> ENTITY_AUREAL = EntityCapability.createVoid(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aureal_provider"), AurealProvider.class);

    AurealStorage getAurealStorage();

    /**
     * @return the current amount of Aureal.
     */
    default int getAureal() {
        return this.getAurealStorage().value();
    }

    /**
     * Updates the currently available Aureal.
     *
     * @param aureal the new Aureal value
     */
    default void setAureal(int aureal) {
        this.getAurealStorage().setValue(aureal);
    }

    /**
     * @return the max amount of Aureal this object can contain before applying modifiers such as enchantments.
     */
    default int getAurealLimit() {
        return this.getAurealStorage().limit();
    }

    /**
     * @return the max amount of Aureal this object can contain.
     */
    default int getModifiedAurealLimit() {
        return this.getAurealLimit();
    }

    /**
     * Updates the max amount of Aureal this object can contain.
     *
     * @param limit the new limit
     */
    default void setAurealLimit(int limit) {
        this.getAurealStorage().setLimit(limit);
    }
}
