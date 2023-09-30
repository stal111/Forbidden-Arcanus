package com.stal111.forbidden_arcanus.util;

import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.network.chat.Component;

/**
 * @author stal111
 * @since 2023-02-19
 */
public class AdditionalCodecs {

    public static final Codec<Component> COMPONENT = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<Component> read(DynamicOps<T> ops, T input) {
            try {
                return DataResult.success(Component.Serializer.fromJson(ops.convertTo(JsonOps.INSTANCE, input)));
            } catch (JsonParseException e) {
                return DataResult.error(() -> "Failed to parse Component: " + e.getMessage());
            }
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Component value) {
            return JsonOps.INSTANCE.convertTo(ops, Component.Serializer.toJsonTree(value));
        }
    };
}
