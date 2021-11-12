package com.stal111.forbidden_arcanus.common.container.input;

import com.stal111.forbidden_arcanus.common.container.InputType;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.BloodTestTubeItem;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * Blood Test Tube Input
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.input.BloodTestTubeInput
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-08
 */
public class BloodTestTubeInput implements IHephaestusForgeInput {

    @Override
    public boolean canInput(InputType inputType, ItemStack stack) {
        return inputType == InputType.BLOOD && stack.getItem() == NewModItems.BLOOD_TEST_TUBE.get();
    }

    @Override
    public int getInputValue(InputType inputType, ItemStack stack, Random random) {
        int value = BloodTestTubeItem.getBlood(stack);

        return value == 0 ? 0 : Math.min(value, 10);
    }

    @Override
    public void finishInput(InputType inputType, ItemStack stack, HephaestusForgeTileEntity tileEntity, int slot, int inputValue) {
        if (inputValue != 0) {
            ItemStack newStack = BloodTestTubeItem.removeBlood(stack, inputValue);

            if (!stack.sameItem(newStack)) {
                tileEntity.setItem(slot, newStack);
            }
        }
    }
}
