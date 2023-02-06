package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

/**
 * @author stal111
 * @since 2023-02-06
 */
public interface EssencesContainer {
    void setEssencesLimit(EssencesDefinition definition);
    EssencesDefinition getEssences();
    void setEssences(EssencesDefinition definition);
}
