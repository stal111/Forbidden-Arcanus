package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssencesDefinition;

public record MultiEssenceStorage(EssenceStorage aureal,
                                  EssenceStorage souls,
                                  EssenceStorage blood,
                                  EssenceStorage experience) {

    public static final Codec<MultiEssenceStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceStorage.codec(EssenceType.AUREAL).fieldOf("aureal").forGetter(MultiEssenceStorage::aureal),
            EssenceStorage.codec(EssenceType.SOULS).fieldOf("souls").forGetter(MultiEssenceStorage::souls),
            EssenceStorage.codec(EssenceType.BLOOD).fieldOf("blood").forGetter(MultiEssenceStorage::blood),
            EssenceStorage.codec(EssenceType.EXPERIENCE).fieldOf("experience").forGetter(MultiEssenceStorage::experience)
    ).apply(instance, MultiEssenceStorage::new));

    public static MultiEssenceStorage empty(EssencesDefinition limit) {
        return new MultiEssenceStorage(
                EssenceStorage.createEmpty(EssenceType.AUREAL, limit.aureal()),
                EssenceStorage.createEmpty(EssenceType.SOULS, limit.souls()),
                EssenceStorage.createEmpty(EssenceType.BLOOD, limit.blood()),
                EssenceStorage.createEmpty(EssenceType.EXPERIENCE, limit.experience())
        );
    }

    public EssenceStorage getStorage(EssenceType type) {
        return switch (type) {
            case AUREAL -> this.aureal;
            case SOULS -> this.souls;
            case BLOOD -> this.blood;
            case EXPERIENCE -> this.experience;
        };
    }

    public int getAmount(EssenceType type) {
        return this.getStorage(type).amount();
    }

    public MultiEssenceStorage setAmount(EssenceType type, int amount) {
        return switch (type) {
            case AUREAL ->
                    new MultiEssenceStorage(this.aureal.setAmount(amount), this.souls, this.blood, this.experience);
            case SOULS ->
                    new MultiEssenceStorage(this.aureal, this.souls.setAmount(amount), this.blood, this.experience);
            case BLOOD ->
                    new MultiEssenceStorage(this.aureal, this.souls, this.blood.setAmount(amount), this.experience);
            case EXPERIENCE ->
                    new MultiEssenceStorage(this.aureal, this.souls, this.blood, this.experience.setAmount(amount));
        };
    }

    public EssencesDefinition getSnapshot() {
        return new EssencesDefinition(this.aureal.amount(), this.souls.amount(), this.blood.amount(), this.experience.amount());
    }
}
