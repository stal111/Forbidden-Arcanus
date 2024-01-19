package com.stal111.forbidden_arcanus.common.aureal;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author stal111
 * @since 17.01.2024
 */
public class AurealStorage {

    public static final AurealStorage EMPTY = new AurealStorage(0, 0);

    public static final Codec<AurealStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("value").forGetter(AurealStorage::value),
            Codec.INT.fieldOf("limit").forGetter(AurealStorage::limit)
    ).apply(instance, AurealStorage::new));

    private int value;
    private int limit;

    public AurealStorage(int value, int limit) {
        this.value = value;
        this.limit = limit;
    }

    public int value() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int limit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void combine(AurealStorage storage) {
        this.value += storage.value();
        this.limit += storage.limit();
    }
}
