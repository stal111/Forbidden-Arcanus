package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;

public record MoltenMaterial(Holder<MoltenMaterialType> type, int amount) {

    public static final Codec<MoltenMaterial> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MoltenMaterialType.CODEC.fieldOf("type").forGetter(MoltenMaterial::type),
            ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(MoltenMaterial::amount)
    ).apply(instance, MoltenMaterial::new));
}
