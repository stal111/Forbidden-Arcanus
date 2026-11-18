package com.stal111.forbidden_arcanus.common.essence.source;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface EssenceSource {

    Codec<Holder<EssenceSource>> HOLDER_CODEC = FARegistries.ESSENCE_SOURCE_REGISTRY.holderByNameCodec();
    StreamCodec<RegistryFriendlyByteBuf, Holder<EssenceSource>> HOLDER_STREAM_CODEC = ByteBufCodecs.holderRegistry(FARegistries.ESSENCE_SOURCE);

    record Match(EssenceSource source, EssenceType type) {
    }

    static Optional<Match> findValidSource(ItemStack stack) {
        for (EssenceSource source : FARegistries.ESSENCE_SOURCE_REGISTRY) {
            EssenceValue value = source.getEssence(stack);

            if (value != null && value.amount() > 0) {
                return Optional.of(new Match(source, value.type()));
            }
        }

        return Optional.empty();
    }

    static Optional<EssenceSource> findValidSource(ItemStack stack, EssenceType type) {
        for (EssenceSource source : FARegistries.ESSENCE_SOURCE_REGISTRY) {
            if (source.isValidSource(stack, type)) {
                return Optional.of(source);
            }
        }

        return Optional.empty();
    }

    @Nullable
    EssenceValue getEssence(ItemStack stack);

    default boolean isValidSource(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type) > 0;
    }

    default int getAmount(ItemStack stack, EssenceType type) {
        EssenceValue value = this.getEssence(stack);
        return (value != null && value.type() == type) ? value.amount() : 0;
    }

    default int getMaxAmount(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type);
    }

    default int getExtractionRate(ItemStack stack, EssenceType type) {
        return this.getAmount(stack, type);
    }

    ItemStack finishExtraction(ItemStack stack, int extractedAmount);
}
