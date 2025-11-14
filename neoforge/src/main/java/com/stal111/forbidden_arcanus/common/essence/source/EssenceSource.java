package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface EssenceSource {

    Codec<EssenceSource> CODEC = FARegistries.ESSENCE_SOURCE_TYPE_REGISTRY.byNameCodec().dispatch(EssenceSource::getType, EssenceSourceType::mapCodec);
    StreamCodec<RegistryFriendlyByteBuf, EssenceSource> STREAM_CODEC = ByteBufCodecs.registry(FARegistries.ESSENCE_SOURCE_TYPE).dispatch(EssenceSource::getType, EssenceSourceType::streamCodec);

    @Nullable EssenceValue getEssenceValue(ItemStack stack, RandomSource random);

    EssenceSourceType<?> getType();
}
