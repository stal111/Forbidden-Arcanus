package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;

public record StaticValueEssenceSource(EssenceType essenceType, IntProvider value) implements EssenceSource {

    public static final MapCodec<StaticValueEssenceSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(StaticValueEssenceSource::essenceType),
            IntProvider.CODEC.fieldOf("value").forGetter(StaticValueEssenceSource::value)
    ).apply(instance, StaticValueEssenceSource::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, IntProvider> INT_PROVIDER_STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(IntProvider.CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, StaticValueEssenceSource> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            StaticValueEssenceSource::essenceType,
            INT_PROVIDER_STREAM_CODEC,
            StaticValueEssenceSource::value,
            StaticValueEssenceSource::new
    );

    @Override
    public EssenceValue getEssenceValue(ItemStack stack, RandomSource random) {
        return new EssenceValue(this.essenceType, this.value.sample(random));
    }

    @Override
    public EssenceSourceType<?> getType() {
        return EssenceSourceTypes.STATIC_VALUE.get();
    }
}
