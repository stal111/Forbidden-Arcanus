package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import net.minecraft.network.FriendlyByteBuf;

import java.util.EnumMap;
import java.util.function.BiConsumer;

/**
 * @author stal111
 * @since 2023-01-04
 */
public class EssencesDefinition {

    private final EnumMap<EssenceType, Integer> essences = new EnumMap<>(EssenceType.class);

    public EssencesDefinition(int aureal, int souls, int blood, int experience) {
        this.essences.put(EssenceType.AUREAL, aureal);
        this.essences.put(EssenceType.SOULS, souls);
        this.essences.put(EssenceType.BLOOD, blood);
        this.essences.put(EssenceType.EXPERIENCE, experience);
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
