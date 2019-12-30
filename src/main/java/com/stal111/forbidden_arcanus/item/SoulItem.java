package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.block.dispenser.SoulDispenseBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;

public class SoulItem extends Item {

    public SoulItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, new SoulDispenseBehavior());
    }
}
