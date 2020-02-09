package com.stal111.forbidden_arcanus.util;

import net.minecraft.block.WoodType;

public class ModWoodType extends WoodType {

    public static final WoodType EDELWOOD = ModWoodType.register(new ModWoodType("edelwood"));
    public static final WoodType CHERRYWOOD = ModWoodType.register(new ModWoodType("cherrywood"));
    public static final WoodType MYSTERYWOOD = ModWoodType.register(new ModWoodType("mysterywood"));


    public ModWoodType(String p_i225775_1_) {
        super(p_i225775_1_);
    }

    public static WoodType register(WoodType woodType) {
        VALUES.add(woodType);
        return woodType;
    }
}
