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
public record EssenceValue(EssenceType type, int amount) {

    public static final EssenceValue EMPTY = new EssenceValue(null, 0);

    public static final MapCodec<EssenceValue> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(EssenceValue::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceValue::amount)
    ).apply(instance, EssenceValue::new));

    public static final Codec<EssenceValue> CODEC = MAP_CODEC.codec();

    public static final StreamCodec<FriendlyByteBuf, EssenceValue> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceValue::type,
            ByteBufCodecs.INT,
            EssenceValue::amount,
            EssenceValue::new
    );

    public static EssenceValue of(EssenceType type, int amount) {
        return new EssenceValue(type, amount);
    }

    public static EssenceValue createEmpty(EssenceType type) {
        return EssenceValue.of(type, 0);
    }

    public EssenceValue combine(EssenceValue data) {
        return new EssenceValue(this.type, this.amount + data.amount);
    }
}
