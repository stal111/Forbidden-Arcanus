package com.stal111.forbidden_arcanus.common.block.entity.forge.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.BiConsumer;

/**
 * An immutable class that defines the amounts of the four different essence types. <br>
 * Can be transformed into a mutable {@link EssencesStorage} if needed.
 *
 * @author stal111
 * @since 2023-01-04
 */
public record EssencesDefinition(int aureal, int souls, int blood, int experience) {

    public static final Codec<EssencesDefinition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("aureal").forGetter(EssencesDefinition::aureal),
            Codec.INT.fieldOf("souls").forGetter(EssencesDefinition::souls),
            Codec.INT.fieldOf("blood").forGetter(EssencesDefinition::blood),
            Codec.INT.fieldOf("experience").forGetter(EssencesDefinition::experience)
    ).apply(instance, EssencesDefinition::new));

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

    public EssencesStorage mutable() {
        return new EssencesStorage(this.aureal, this.souls, this.blood, this.experience);
    }
}
