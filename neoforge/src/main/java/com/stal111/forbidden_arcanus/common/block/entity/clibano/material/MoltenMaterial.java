package com.stal111.forbidden_arcanus.common.block.entity.clibano.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public record MoltenMaterial(Holder<MoltenMaterialType> type, int amount) {

    public static final Codec<MoltenMaterial> CODEC = RecordCodecBuilder.<MoltenMaterial>create(instance -> instance.group(
            MoltenMaterialType.CODEC.fieldOf("type").forGetter(MoltenMaterial::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(MoltenMaterial::amount)
    ).apply(instance, MoltenMaterial::new)).validate(MoltenMaterial::validate);

    public static final StreamCodec<RegistryFriendlyByteBuf, MoltenMaterial> STREAM_CODEC = StreamCodec.composite(
            MoltenMaterialType.STREAM_CODEC,
            MoltenMaterial::type,
            ByteBufCodecs.INT,
            MoltenMaterial::amount,
            MoltenMaterial::new
    );

    public DataResult<MoltenMaterial> validate() {
        if (this.amount > this.type().value().maxAmount()) {
            return DataResult.error(() -> "Material " + this.type() + " has too much amount: " + this.amount + ", max amount: " + this.type().value().maxAmount());
        }

        return DataResult.success(this);
    }
}
