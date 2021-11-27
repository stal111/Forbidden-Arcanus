package com.stal111.forbidden_arcanus.common.block.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * Mod Block State Properties <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-26
 */
public class ModBlockStateProperties {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty SPECK = BooleanProperty.create("speck");
    public static final BooleanProperty RITUAL = BooleanProperty.create("ritual");

    public static final EnumProperty<PillarType> PILLAR_TYPE = EnumProperty.create("type", PillarType.class);
}
