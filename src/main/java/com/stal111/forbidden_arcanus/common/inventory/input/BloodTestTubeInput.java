package com.stal111.forbidden_arcanus.common.inventory.input;

import com.stal111.forbidden_arcanus.common.inventory.InputType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * Blood Test Tube Input <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.input.BloodTestTubeInput
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-08
 */
public class BloodTestTubeInput implements HephaestusForgeInput {

    @Override
    public boolean canInput(InputType inputType, ItemStack stack) {
        return inputType == InputType.BLOOD && stack.is(ModItems.BLOOD_TEST_TUBE.get());
    }

    @Override
    public int getInputValue(InputType inputType, ItemStack stack, Random random) {
        int value = BloodTestTubeItem.getBlood(stack);

        return value == 0 ? 0 : Math.min(value, 10);
    }

    @Override
    public void finishInput(InputType inputType, ItemStack stack, HephaestusForgeBlockEntity tileEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            ItemStack newStack = BloodTestTubeItem.removeBlood(stack, inputValue);

            if (!stack.sameItem(newStack)) {
                tileEntity.setItem(slot, newStack);
            }
        }
    }
}
