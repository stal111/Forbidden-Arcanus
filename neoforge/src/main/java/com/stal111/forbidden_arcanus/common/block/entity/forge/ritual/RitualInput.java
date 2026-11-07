package com.stal111.forbidden_arcanus.common.block.entity.forge.ritual;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Iterator;
import java.util.List;

/**
 * @author stal111
 * @since 2023-01-08
 */
public record RitualInput(Ingredient ingredient, int amount) {

    public static final Codec<RitualInput> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(RitualInput::ingredient),
            ExtraCodecs.POSITIVE_INT.optionalFieldOf("amount", 1).forGetter(RitualInput::amount)
    ).apply(instance, RitualInput::new));

    public boolean consumeFrom(List<ItemStack> stacks) {
        Iterator<ItemStack> iterator = stacks.iterator();
        int remainingToMatch = this.amount;

        while (iterator.hasNext()) {
            if (this.ingredient.test(iterator.next())) {
                iterator.remove();

                remainingToMatch--;

                if (remainingToMatch == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
