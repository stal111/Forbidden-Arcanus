package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import com.stal111.forbidden_arcanus.common.essence.input.EssenceInput;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceAccess;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
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
        return FARegistries.ESSENCE_INPUT_REGISTRY.listElements()
                .map(Holder.Reference::value)
                .anyMatch(input -> input.isValidInput(resource.toStack(), this.essenceTypes.get(index)));
    }

    public void tick(EssenceAccess essenceAccess, RegistryAccess registryAccess) {
        for (int i = 0; i < this.essenceTypes.size(); i++) {
            ItemStack stack = ItemUtil.getStack(this, i);
            EssenceType essenceType = this.essenceTypes.get(i);

            if (stack.isEmpty() || essenceAccess.isEssenceFull(essenceType)) {
                continue;
            }

            EssenceInput input = registryAccess.lookupOrThrow(FARegistries.ESSENCE_INPUT).listElements()
                    .map(Holder.Reference::value)
                    .filter(forgeInput -> forgeInput.isValidInput(stack, essenceType))
                    .findFirst().orElse(null);

            if (input != null) {
                int value = input.getAmount(stack, essenceType);

                essenceAccess.addEssence(essenceType, value);

                ItemStack result = input.finishInput(stack, value);
                this.set(i, ItemResource.of(result), result.getCount());
            }
        }
    }
}
