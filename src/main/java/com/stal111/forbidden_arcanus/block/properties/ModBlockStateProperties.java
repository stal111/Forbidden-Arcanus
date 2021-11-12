package com.stal111.forbidden_arcanus.block.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties {
    public static final IntegerProperty CANDLES_0_3 = IntegerProperty.create("candles", 0, 3);
    public static final IntegerProperty CANDLES_1_3 = IntegerProperty.create("candles", 1, 3);
    public static final BooleanProperty CANDLE = BooleanProperty.create("candle");
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty SPECK = BooleanProperty.create("speck");
    public static final BooleanProperty RITUAL = BooleanProperty.create("ritual");
}
