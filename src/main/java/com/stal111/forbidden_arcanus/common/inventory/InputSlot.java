package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

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

    //TODO Still needed?
//    @Override
//    public boolean mayPlace(@Nonnull ItemStack stack) {
//        return FARegistries.FORGE_INPUT_TYPE_REGISTRY.get().getValues().stream().anyMatch(input -> input.canInput(inputType, stack));
//    }

    public EssenceType getInputType() {
        return inputType;
    }
}
