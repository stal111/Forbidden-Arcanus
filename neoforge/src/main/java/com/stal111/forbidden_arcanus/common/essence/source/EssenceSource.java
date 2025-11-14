package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record EssenceSource(DataComponentType<?> dataComponentType) {

    public static final Codec<EssenceSource> DIRECT_CODEC = DataComponentType.CODEC.xmap(EssenceSource::new, EssenceSource::dataComponentType);

    public static final Codec<EssenceSource> CODEC = FARegistries.ESSENCE_SOURCE_TYPE_REGISTRY.byNameCodec();
    public static final StreamCodec<RegistryFriendlyByteBuf, EssenceSource> STREAM_CODEC = ByteBufCodecs.registry(FARegistries.ESSENCE_SOURCE_TYPE);

    public EssenceValue getEssenceValue(DataComponentHolder componentHolder) {
        if (componentHolder.get(this.dataComponentType) instanceof EssenceProvider essenceProvider) {
            return essenceProvider.getEssenceValue();
        }

        return null;
    }
}
