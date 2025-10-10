package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

public class EnhancerResourceHandler extends ItemStacksResourceHandler {

    public EnhancerResourceHandler(int size) {
        super(size);
    }

    @Override
    protected int getCapacity(int index, ItemResource resource) {
        return 1;
    }

    @Override
    public boolean isValid(int index, ItemResource resource) {
        return EnhancerHelper.getEnhancer(resource.toStack()).isPresent();
    }
}
