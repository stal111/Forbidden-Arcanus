package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.forge.ritual.EssenceType;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInputs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Input Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.InputSlot
 *
 * @author stal111
 * @since 2021-07-02
 */
public class InputSlot extends SlotItemHandler {

    private final EssenceType inputType;

    public InputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, EssenceType inputType) {
        super(itemHandler, index, xPosition, yPosition);
        this.inputType = inputType;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return HephaestusForgeInputs.getInputs().stream().anyMatch(input -> input.canInput(inputType, stack));
    }

    public EssenceType getInputType() {
        return inputType;
    }
}
