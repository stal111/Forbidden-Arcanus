package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;

/**
 * @author stal111
 * @since 2023-02-19
 */
public interface EssenceModifier {
    EssenceType getEssenceType();

    default boolean matches(EssenceType essenceType) {
        return essenceType == this.getEssenceType();
    }

    Integer getModifiedValue(Integer originalValue);
}
