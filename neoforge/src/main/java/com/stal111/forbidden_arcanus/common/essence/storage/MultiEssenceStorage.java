package com.stal111.forbidden_arcanus.common.essence.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;

import java.util.function.UnaryOperator;

public record MultiEssenceStorage(EssenceSet amounts, EssenceSet limits) {

    public static final Codec<MultiEssenceStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceSet.CODEC.fieldOf("amounts").forGetter(MultiEssenceStorage::amounts),
            EssenceSet.CODEC.fieldOf("limits").forGetter(MultiEssenceStorage::limits)
    ).apply(instance, MultiEssenceStorage::new));

    public static MultiEssenceStorage empty(EssenceSet limits) {
        return new MultiEssenceStorage(EssenceSet.EMPTY, limits);
    }

    public EssenceStorage getStorage(EssenceType type) {
        return new EssenceStorage(type, this.amounts.get(type), this.limits.get(type));
    }

    public MultiEssenceStorage setStorage(EssenceType type, EssenceStorage storage) {
        return new MultiEssenceStorage(
                this.amounts.set(type, storage.amount()),
                this.limits.set(type, storage.limit())
        );
    }

    public MultiEssenceStorage setLimits(EssenceSet limits) {
        return new MultiEssenceStorage(this.amounts.clamp(limits), limits);
    }

    public MultiEssenceStorage updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        return this.setStorage(type, updater.apply(this.getStorage(type)));
    }
}
