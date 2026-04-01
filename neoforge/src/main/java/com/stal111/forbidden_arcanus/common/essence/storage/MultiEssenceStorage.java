package com.stal111.forbidden_arcanus.common.essence.storage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceSet;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;

import java.util.function.UnaryOperator;

public record MultiEssenceStorage(EssenceStorage aureal,
                                  EssenceStorage souls,
                                  EssenceStorage blood,
                                  EssenceStorage experience) {

    public static final Codec<MultiEssenceStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceStorage.codec(EssenceType.AUREAL).fieldOf("aureal").forGetter(MultiEssenceStorage::aureal),
            EssenceStorage.codec(EssenceType.ECTOPLASM).fieldOf("ectoplasm").forGetter(MultiEssenceStorage::souls),
            EssenceStorage.codec(EssenceType.BLOOD).fieldOf("blood").forGetter(MultiEssenceStorage::blood),
            EssenceStorage.codec(EssenceType.EXPERIENCE).fieldOf("experience").forGetter(MultiEssenceStorage::experience)
    ).apply(instance, MultiEssenceStorage::new));

    public static MultiEssenceStorage empty(EssenceSet limit) {
        return new MultiEssenceStorage(
                EssenceStorage.createEmpty(EssenceType.AUREAL, limit.aureal()),
                EssenceStorage.createEmpty(EssenceType.ECTOPLASM, limit.ectoplasm()),
                EssenceStorage.createEmpty(EssenceType.BLOOD, limit.blood()),
                EssenceStorage.createEmpty(EssenceType.EXPERIENCE, limit.experience())
        );
    }

    public EssenceStorage getStorage(EssenceType type) {
        return switch (type) {
            case AUREAL -> this.aureal;
            case ECTOPLASM -> this.souls;
            case BLOOD -> this.blood;
            case EXPERIENCE -> this.experience;
        };
    }

    public MultiEssenceStorage setStorage(EssenceType type, EssenceStorage storage) {
        return switch (type) {
            case AUREAL -> new MultiEssenceStorage(storage, this.souls, this.blood, this.experience);
            case ECTOPLASM -> new MultiEssenceStorage(this.aureal, storage, this.blood, this.experience);
            case BLOOD -> new MultiEssenceStorage(this.aureal, this.souls, storage, this.experience);
            case EXPERIENCE -> new MultiEssenceStorage(this.aureal, this.souls, this.blood, storage);
        };
    }

    public MultiEssenceStorage updateEssence(EssenceType type, UnaryOperator<EssenceStorage> updater) {
        return this.setStorage(type, updater.apply(this.getStorage(type)));
    }

    public EssenceSet getSnapshot() {
        return new EssenceSet(this.aureal.amount(), this.souls.amount(), this.blood.amount(), this.experience.amount());
    }
}
