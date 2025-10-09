package com.stal111.forbidden_arcanus.common.essence;

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
