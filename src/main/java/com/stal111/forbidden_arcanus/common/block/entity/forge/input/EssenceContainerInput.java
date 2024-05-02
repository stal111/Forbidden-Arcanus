package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.other.ModForgeInputTypes;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class EssenceContainerInput extends HephaestusForgeInput {

    public static final MapCodec<EssenceContainerInput> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("essence_type").forGetter(HephaestusForgeInput::getEssenceType),
            ExtraCodecs.POSITIVE_INT.fieldOf("extraction_speed").forGetter(input -> input.extractionSpeed)
    ).apply(instance, EssenceContainerInput::new));

    private final int extractionSpeed;

    public EssenceContainerInput(EssenceType type, int extractionSpeed) {
        super(type);
        this.extractionSpeed = extractionSpeed;
    }

    @Override
    public boolean canInputStack(ItemStack stack) {
        return EssenceHelper.hasEssence(stack, this.getEssenceType());
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceData(stack).map(data -> Math.min(data.amount(), this.extractionSpeed)).orElse(0);
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity blockEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            EssenceHelper.addEssence(stack, -inputValue);

            if (EssenceHelper.isEmpty(stack)) {
                Optional.ofNullable(stack.get(ModDataComponents.EMPTY_ITEM)).ifPresent(item -> blockEntity.setStack(slot, new ItemStack(item)));
            }
        }
    }

    @Override
    public HephaestusForgeInputType<?> type() {
        return ModForgeInputTypes.ESSENCE_CONTAINER.get();
    }
}
