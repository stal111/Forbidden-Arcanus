package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * @author stal111
 * @since 2023-01-08
 */
public record RitualInput(Ingredient ingredient, short amount) {

    public void toNetwork(FriendlyByteBuf buffer) {
        this.ingredient.toNetwork(buffer);
        buffer.writeShort(this.amount);
    }

    public static RitualInput fromNetwork(FriendlyByteBuf buffer) {
        return new RitualInput(Ingredient.fromNetwork(buffer), buffer.readShort());
    }
}
