package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 06.05.2024
 */
public class EssenceDataInput extends HephaestusForgeInput {

    @Override
    public boolean canInput(EssenceType type, ItemStack stack) {
        return EssenceHelper.getEssenceData(stack).isPresent();
    }

    @Override
    public int getInputValue(EssenceType type, ItemStack stack, RandomSource random) {
        return EssenceHelper.getEssenceData(stack).map(EssenceData::amount).orElse(0);
    }

    @Override
    public void finishInput(EssenceType type, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        stack.shrink(1);
    }
}
