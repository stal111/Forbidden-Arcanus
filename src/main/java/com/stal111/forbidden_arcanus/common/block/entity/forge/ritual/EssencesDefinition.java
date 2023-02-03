package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author stal111
 * @since 2023-01-04
 */
public class EssencesDefinition {

    public static final Codec<EssencesDefinition> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("aureal").forGetter(definition -> {
                return definition.get(EssenceType.AUREAL);
            }),
            Codec.INT.fieldOf("souls").forGetter(definition -> {
                return definition.get(EssenceType.SOULS);
            }),
            Codec.INT.fieldOf("blood").forGetter(definition -> {
                return definition.get(EssenceType.BLOOD);
            }),
            Codec.INT.fieldOf("experience").forGetter(definition -> {
                return definition.get(EssenceType.EXPERIENCE);
            })
    ).apply(instance, EssencesDefinition::new));

    private final EnumMap<EssenceType, Integer> essences = new EnumMap<>(EssenceType.class);

    public EssencesDefinition(int aureal, int souls, int blood, int experience) {
        this.essences.put(EssenceType.AUREAL, aureal);
        this.essences.put(EssenceType.SOULS, souls);
        this.essences.put(EssenceType.BLOOD, blood);
        this.essences.put(EssenceType.EXPERIENCE, experience);
    }

    public static EssencesDefinition of(Map<EssenceType, Integer> map) {
        return new EssencesDefinition(map.getOrDefault(EssenceType.AUREAL, 0), map.getOrDefault(EssenceType.SOULS, 0), map.getOrDefault(EssenceType.BLOOD, 0), map.getOrDefault(EssenceType.EXPERIENCE, 0));
    }

    public int get(EssenceType type) {
        return this.essences.get(type);
    }

    public void forEach(BiConsumer<EssenceType, Integer> consumer) {
        this.essences.forEach(consumer);
    }

    public void serializeToNetwork(FriendlyByteBuf buffer) {
        for (EssenceType type : EssenceType.values()) {
            buffer.writeVarInt(this.get(type));
        }
    }

    public static EssencesDefinition fromNetwork(FriendlyByteBuf buffer) {
        return new EssencesDefinition(buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }
}
