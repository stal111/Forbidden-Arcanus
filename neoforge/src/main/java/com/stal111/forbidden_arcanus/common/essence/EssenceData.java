package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

/**
 * @author stal111
 * @since 26.04.2024
 */
public record EssenceData(EssenceType type, int amount) {

    public static final EssenceData EMPTY = new EssenceData(null, 0);

    public static final MapCodec<EssenceData> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(EssenceData::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceData::amount)
    ).apply(instance, EssenceData::new));

    public static final Codec<EssenceData> CODEC = MAP_CODEC.codec();

    public static final StreamCodec<FriendlyByteBuf, EssenceData> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceData::type,
            ByteBufCodecs.INT,
            EssenceData::amount,
            EssenceData::new
    );

    public static EssenceData of(EssenceType type, int amount) {
        return new EssenceData(type, amount);
    }

    public static EssenceData createEmpty(EssenceType type) {
        return EssenceData.of(type, 0);
    }

    public EssenceData combine(EssenceData data) {
        return new EssenceData(this.type, this.amount + data.amount);
    }
}
