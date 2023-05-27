package com.stal111.forbidden_arcanus.common.block.entity.forge.input;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModForgeInputTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * @author stal111
 * @since 2021-07-08
 */
public class BloodInput extends HephaestusForgeInput {

    public static final Codec<BloodInput> CODEC = Codec.unit(BloodInput::new);

    public BloodInput() {
        super(EssenceType.BLOOD);
    }

    @Override
    public boolean additionalInputChecks(ItemStack stack) {
        return stack.is(ModItems.BLOOD_TEST_TUBE.get());
    }

    @Override
    public int getInputValue(EssenceType inputType, ItemStack stack, RandomSource random) {
        int value = BloodTestTubeItem.getBlood(stack);

        return value == 0 ? 0 : Math.min(value, 10);
    }

    @Override
    public void finishInput(EssenceType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            ItemStack newStack = BloodTestTubeItem.removeBlood(stack, inputValue);

            if (!stack.sameItem(newStack)) {
                tileEntity.setStack(slot, newStack);
            }
        }
    }

    @Override
    public HephaestusForgeInputType<?> type() {
        return ModForgeInputTypes.BLOOD.get();
    }
}
