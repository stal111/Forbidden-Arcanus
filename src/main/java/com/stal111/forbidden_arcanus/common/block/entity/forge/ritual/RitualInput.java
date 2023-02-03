package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;

/**
 * @author stal111
 * @since 2023-01-08
 */
public record RitualInput(Ingredient ingredient, int amount) {

    public static final Codec<Ingredient> INGREDIENT = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<Ingredient> read(DynamicOps<T> ops, T input) {
            try {
                return DataResult.success(Ingredient.fromJson(ops.convertTo(JsonOps.INSTANCE, input)));
            } catch (JsonParseException e) {
                return DataResult.error("Failed to parse Ingredient: " + e.getMessage());
            }
        }

        @Override
        public <T> T write(DynamicOps<T> ops, Ingredient value) {
            return JsonOps.INSTANCE.convertTo(ops, value.toJson());
        }
    };

    public static final Codec<RitualInput> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            INGREDIENT.fieldOf("ingredient").forGetter(input -> {
                return input.ingredient;
            }),
            Codec.INT.optionalFieldOf("amount").forGetter(input -> {
                return Optional.of(input.amount);
            })
    ).apply(instance, (name, amount) -> {
        return new RitualInput(name, amount.orElse(1));
    }));

    public static RitualInput fromNetwork(FriendlyByteBuf buffer) {
        return new RitualInput(Ingredient.fromNetwork(buffer), buffer.readShort());
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        this.ingredient.toNetwork(buffer);
        buffer.writeShort(this.amount);
    }
}
