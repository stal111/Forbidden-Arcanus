package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceInput;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;

import java.util.List;

public class EssenceInputResourceHandler extends ItemStacksResourceHandler {

    private final List<EssenceType> essenceTypes;

    public EssenceInputResourceHandler(EssenceType... essenceTypes) {
        this(List.of(essenceTypes));
    }

    public EssenceInputResourceHandler(List<EssenceType> essenceTypes) {
        super(essenceTypes.size());
        this.essenceTypes = essenceTypes;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return EssenceInput.findValidInput(resource.toStack(), this.essenceTypes.get(index)).isPresent();
    }

    public void tick(EssenceAccess essenceAccess) {
        for (int i = 0; i < this.essenceTypes.size(); i++) {
            ItemStack stack = ItemUtil.getStack(this, i);
            EssenceType essenceType = this.essenceTypes.get(i);

            if (stack.isEmpty() || essenceAccess.isEssenceFull(essenceType)) {
                continue;
            }

            int slot = i;

            EssenceInput.findValidInput(stack, essenceType).ifPresent(input -> {
                int value = input.getAmount(stack, essenceType);

                essenceAccess.addEssence(essenceType, value);

                ItemStack result = input.finishInput(stack, value);
                this.set(slot, ItemResource.of(result), result.getCount());
            });
        }
    }
}
