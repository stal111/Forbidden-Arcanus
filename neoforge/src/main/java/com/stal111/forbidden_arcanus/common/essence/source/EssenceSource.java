package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.common.MutableDataComponentHolder;
import org.jetbrains.annotations.Nullable;

public interface EssenceSource {

    Codec<Holder<EssenceSource>> CODEC = FARegistries.ESSENCE_SOURCE_REGISTRY.holderByNameCodec();
    StreamCodec<RegistryFriendlyByteBuf, Holder<EssenceSource>> STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.ESSENCE_SOURCE);

    @Nullable EssenceValue extractEssence(MutableDataComponentHolder componentHolder, boolean keepOriginal);
}
