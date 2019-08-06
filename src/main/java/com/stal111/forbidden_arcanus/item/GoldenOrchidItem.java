package com.stal111.forbidden_arcanus.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class GoldenOrchidItem extends BlockItem {

    public GoldenOrchidItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Override
    public String getTranslationKey() {
        return this.getDefaultTranslationKey();
    }
}
