package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.Holder;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

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
        return FARegistries.FORGE_INPUT_REGISTRY.listElements()
                .map(Holder.Reference::value)
                .anyMatch(input -> input.canInput(this.essenceTypes.get(index), resource.toStack()));
    }
}
