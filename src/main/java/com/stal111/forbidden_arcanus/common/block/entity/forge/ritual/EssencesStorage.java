package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import java.util.EnumMap;

/**
 * @author stal111
 * @since 2023-01-04
 */
public class EssencesStorage extends EnumMap<EssenceType, Integer> {

    public EssencesStorage() {
        super(EssenceType.class);
    }

    public boolean hasMoreThan(EssencesDefinition definition) {
        for (EssenceType type : EssenceType.values()) {
            if (this.get(type) < definition.get(type)) {
                return false;
            }
        }
        return true;
    }

    public void reduce(EssencesDefinition definition) {
        definition.forEach((type, integer) -> {
            this.put(type, this.get(type) - integer);
        });
    }

    @Override
    public Integer put(EssenceType key, Integer value) {
        return super.put(key, Math.max(value, 0));
    }
}
