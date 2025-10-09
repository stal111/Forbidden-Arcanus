package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * An immutable class that defines the amounts of the four different essence types. <br>
 *
 * @author stal111
 * @since 2023-01-04
 */
public record EssenceSet(int aureal, int souls, int blood, int experience) {

    public static final EssenceSet EMPTY = new EssenceSet(0, 0, 0, 0);

    public static final Codec<EssenceSet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("aureal", 0).forGetter(EssenceSet::aureal),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("souls", 0).forGetter(EssenceSet::souls),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("blood", 0).forGetter(EssenceSet::blood),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("experience", 0).forGetter(EssenceSet::experience)
    ).apply(instance, EssenceSet::new));

    public static EssenceSet of(int aureal, int souls, int blood, int experience) {
        return new EssenceSet(aureal, souls, blood, experience);
    }

    public int get(EssenceType type) {
        return switch (type) {
            case AUREAL -> this.aureal;
            case SOULS -> this.souls;
            case BLOOD -> this.blood;
            case EXPERIENCE -> this.experience;
        };
    }

    public void forEach(BiConsumer<EssenceType, Integer> consumer) {
        for (EssenceType type : EssenceType.values()) {
            consumer.accept(type, this.get(type));
        }
    }

    public boolean hasMoreThan(EssenceSet definition) {
        for (EssenceType type : EssenceType.values()) {
            if (this.get(type) < definition.get(type)) {
                return false;
            }
        }
        return true;
    }

    public EssenceSet applyModifiers(List<EssenceModifier> modifiers) {
        Map<EssenceType, Integer> values = new EnumMap<>(EssenceType.class);

        values.put(EssenceType.AUREAL, aureal);
        values.put(EssenceType.SOULS, souls);
        values.put(EssenceType.BLOOD, blood);
        values.put(EssenceType.EXPERIENCE, experience);

        for (EssenceModifier modifier : modifiers) {
            values.computeIfPresent(modifier.getEssenceType(), (k, v) -> modifier.getModifiedValue(v));
        }

        return EssenceSet.of(values.get(EssenceType.AUREAL), values.get(EssenceType.SOULS), values.get(EssenceType.BLOOD), values.get(EssenceType.EXPERIENCE));
    }

    @Override
    public String toString() {
        return "EssencesDefinition{" +
                "aureal=" + this.aureal +
                ", souls=" + this.souls +
                ", blood=" + this.blood +
                ", experience=" + this.experience +
                '}';
    }
}
