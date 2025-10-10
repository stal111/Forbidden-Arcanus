package com.stal111.forbidden_arcanus.common.inventory;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ResourceHandlerSlot;

/**
 * Input Slot
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.InputSlot
 *
 * @author stal111
 * @since 2021-07-02
 */
public class InputSlot extends ResourceHandlerSlot {

    private final EssenceType inputType;

    public InputSlot(ItemStacksResourceHandler itemHandler, int index, int xPosition, int yPosition, EssenceType inputType) {
        super(itemHandler, itemHandler::set, index, xPosition, yPosition);
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
