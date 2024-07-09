package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * An immutable class that defines the amounts of the four different essence types. <br>
 * Can be transformed into a mutable {@link EssencesStorage} if needed.
 *
 * @author stal111
 * @since 2023-01-04
 */
public record EssencesDefinition(int aureal, int souls, int blood, int experience) {

    public static final EssencesDefinition EMPTY = new EssencesDefinition(0, 0, 0, 0);

    public static final Codec<EssencesDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("aureal", 0).forGetter(EssencesDefinition::aureal),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("souls", 0).forGetter(EssencesDefinition::souls),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("blood", 0).forGetter(EssencesDefinition::blood),
            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("experience", 0).forGetter(EssencesDefinition::experience)
    ).apply(instance, EssencesDefinition::new));

    public static EssencesDefinition of(int aureal, int souls, int blood, int experience) {
        return new EssencesDefinition(aureal, souls, blood, experience);
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

    public boolean hasMoreThan(EssencesDefinition definition) {
        for (EssenceType type : EssenceType.values()) {
            if (this.get(type) < definition.get(type)) {
                return false;
            }
        }
        return true;
    }

    public EssencesDefinition applyModifiers(List<EssenceModifier> modifiers) {
        EssencesStorage storage = this.mutable();

        storage.applyModifiers(modifiers);

        return storage.immutable();
    }

    public EssencesStorage mutable() {
        return new EssencesStorage(this.aureal, this.souls, this.blood, this.experience);
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
