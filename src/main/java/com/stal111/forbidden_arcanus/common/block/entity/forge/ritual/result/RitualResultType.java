package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.result;

import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author stal111
 * @since 2023-02-05
 */
public class RitualResultType<T extends RitualResult> {

    private final NetworkSerializer<T> networkSerializer;
    private final NetworkDeserializer<T> networkDeserializer;

    private final Codec<T> codec;

    public RitualResultType(NetworkSerializer<T> networkSerializer, NetworkDeserializer<T> networkDeserializer, Codec<T> codec) {
        this.networkSerializer = networkSerializer;
        this.networkDeserializer = networkDeserializer;
        this.codec = codec;
    }

    @Nullable
    public T fromNetwork(@Nonnull FriendlyByteBuf buffer) {
        return this.networkDeserializer.fromNetwork(buffer);
    }

    public void toNetwork(@Nonnull FriendlyByteBuf buffer, RitualResult result) {
        this.networkSerializer.toNetwork(buffer, (T) result);
    }

    public Codec<T> codec() {
        return this.codec;
    }

    @FunctionalInterface
    public interface NetworkDeserializer<T extends RitualResult> {
        @Nullable
        T fromNetwork(@Nonnull FriendlyByteBuf buffer);
    }

    @FunctionalInterface
    public interface NetworkSerializer<T extends RitualResult> {
        void toNetwork(@Nonnull FriendlyByteBuf buffer, T result);
    }
}
