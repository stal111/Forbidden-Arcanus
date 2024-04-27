package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.other.ModForgeInputTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Optional;

/**
 * @author stal111
 * @since 2021-07-07
 */
public class ItemInput extends HephaestusForgeInput {

    public static final MapCodec<ItemInput> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("essence_type").forGetter(HephaestusForgeInput::getEssenceType),
            Ingredient.CODEC_NONEMPTY.fieldOf("item").forGetter(input -> {
                return input.ingredient;
            }),
            Codec.INT.optionalFieldOf("amount").forGetter(input -> {
                return Optional.of(input.amount);
            })
    ).apply(instance, (type, name, amount) -> {
        return new ItemInput(type, name, amount.orElse(1));
    }));

    private final Ingredient ingredient;
    private final int amount;

    public ItemInput(EssenceType essenceType, Ingredient ingredient, int amount) {
        super(essenceType);
        this.ingredient = ingredient;
        this.amount = amount;
    }

    @Override
    public boolean canInputStack(ItemStack stack) {
        return this.ingredient.test(stack);
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        return this.amount;
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        stack.shrink(1);
    }

    @Override
    public HephaestusForgeInputType<?> type() {
        return ModForgeInputTypes.SIMPLE_ITEM.get();
    }
}
