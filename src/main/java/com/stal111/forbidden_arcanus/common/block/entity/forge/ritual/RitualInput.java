package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.util.AdditionalCodecs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;

/**
 * @author stal111
 * @since 2023-01-08
 */
public record RitualInput(Ingredient ingredient, int amount) {

    public static final Codec<RitualInput> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            AdditionalCodecs.INGREDIENT.fieldOf("ingredient").forGetter(input -> {
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
