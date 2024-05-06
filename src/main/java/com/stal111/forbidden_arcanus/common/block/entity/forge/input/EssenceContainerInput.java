package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class EssenceContainerInput extends HephaestusForgeInput {

    public static final int EXTRACTION_SPEED = 10;

    @Override
    public boolean canInput(EssenceType type, ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> storage.data().type() == type).orElse(false);
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceStorage(stack).map(storage -> Math.min(storage.data().amount(), EXTRACTION_SPEED)).orElse(0);
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity blockEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
                storage.addEssence(stack, -inputValue);

                if (storage.isEmpty()) {
                    Optional.ofNullable(stack.get(ModDataComponents.EMPTY_ITEM)).ifPresent(item -> blockEntity.setStack(slot, new ItemStack(item)));
                }
            });
        }
    }
}
