package com.stal111.forbidden_arcanus.item;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;

import net.minecraft.world.item.Item.Properties;

public class GoldenOrchidItem extends BlockItem {

    public GoldenOrchidItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public String getDescriptionId() {
        return this.getOrCreateDescriptionId();
    }
}
