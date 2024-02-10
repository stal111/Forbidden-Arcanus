package com.stal111.forbidden_arcanus.common.block.properties;

import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.clibano.ClibanoSideType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

/**
 * Mod Block State Properties <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties
 *
 * @author stal111
 * @since 2021-11-26
 */
public class ModBlockStateProperties {
    public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");
    public static final BooleanProperty SPECK = BooleanProperty.create("speck");
    public static final BooleanProperty RITUAL = BooleanProperty.create("ritual");
    public static final BooleanProperty OILY = BooleanProperty.create("oily");
    public static final BooleanProperty LEAVES = BooleanProperty.create("leaves");
    public static final BooleanProperty MIRRORED = BooleanProperty.create("mirrored");

    public static final IntegerProperty AGE_6 = IntegerProperty.create("age", 0, 6);
    public static final IntegerProperty TIER = IntegerProperty.create("tier", 1, 5);

    public static final EnumProperty<PillarType> PILLAR_TYPE = EnumProperty.create("type", PillarType.class);
    public static final EnumProperty<ObeliskPart> OBELISK_PART =  EnumProperty.create("part", ObeliskPart.class);
    public static final EnumProperty<ClibanoCenterType> CLIBANO_CENTER_TYPE =  EnumProperty.create("type", ClibanoCenterType.class);
    public static final EnumProperty<ClibanoSideType> CLIBANO_SIDE_TYPE =  EnumProperty.create("type", ClibanoSideType.class);
}
