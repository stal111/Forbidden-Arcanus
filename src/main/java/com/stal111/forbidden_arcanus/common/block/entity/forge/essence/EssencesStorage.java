package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import java.util.EnumMap;

/**
 * @author stal111
 * @since 2023-01-04
 */
public class EssencesStorage extends EnumMap<EssenceType, Integer> {

    public EssencesStorage() {
        super(EssenceType.class);
    }

    public boolean hasEnough(EssencesDefinition definition) {
        for (EssenceType type : EssenceType.values()) {
            if (this.getOrDefault(type, 0) < definition.get(type)) {
                return false;
            }
        }
        return true;
    }

    public void reduce(EssencesDefinition definition) {
        definition.forEach((type, integer) -> {
            this.put(type, this.getOrDefault(type, 0) - integer);
        });
    }

    @Override
    public Integer put(EssenceType key, Integer value) {
        return super.put(key, Math.max(value, 0));
    }
}
