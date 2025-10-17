package com.stal111.forbidden_arcanus.common.block.entity.transfer;

import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerDefinition;
import com.stal111.forbidden_arcanus.common.item.enhancer.EnhancerHelper;
import net.minecraft.core.HolderSet;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;

import java.util.Optional;

public class EnhancerResourceHandler extends ItemStacksResourceHandler {

    private HolderSet<EnhancerDefinition> enhancers = HolderSet.empty();

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

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
        super.onContentsChanged(index, previousContents);

        var enhancers = this.stacks.stream()
                .map(EnhancerHelper::getEnhancerHolder)
                .flatMap(Optional::stream)
                .toList();

        this.enhancers = HolderSet.direct(enhancers);
    }

    public HolderSet<EnhancerDefinition> getEnhancers() {
        return this.enhancers;
    }
}
