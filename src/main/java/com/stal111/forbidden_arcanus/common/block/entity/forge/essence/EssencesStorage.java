package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import java.util.EnumMap;
import java.util.function.UnaryOperator;

/**
 * The mutable equivalent of {@link EssencesDefinition} that can be used if the essence values are expected to change over time. <br>
 * An immutable snapshot of the current values can be created with the {@link EssencesStorage#immutable()} method.
 *
 * @author stal111
 * @since 2023-01-04
 */
public class EssencesStorage extends EnumMap<EssenceType, Integer> {

    public EssencesStorage() {
        super(EssenceType.class);
    }

    public EssencesStorage(int aureal, int souls, int blood, int experience) {
        super(EssenceType.class);

        this.put(EssenceType.AUREAL, aureal);
        this.put(EssenceType.SOULS, souls);
        this.put(EssenceType.BLOOD, blood);
        this.put(EssenceType.EXPERIENCE, experience);
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

    public void modify(EssenceType type, UnaryOperator<Integer> modifier) {
        this.put(type, modifier.apply(this.get(type)));
    }

    public EssencesDefinition immutable() {
        return new EssencesDefinition(this.getOrDefault(EssenceType.AUREAL, 0), this.getOrDefault(EssenceType.SOULS, 0), this.getOrDefault(EssenceType.BLOOD, 0), this.getOrDefault(EssenceType.EXPERIENCE, 0));
    }
}
