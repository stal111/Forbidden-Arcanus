package com.stal111.forbidden_arcanus.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Mod Wood Types <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModWoodTypes
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-22
 */
public class ModWoodTypes {

    public static final WoodType CHERRYWOOD = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "cherrywood").toString());
    public static final WoodType MYSTERYWOOD = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "mysterywood").toString());
    public static final WoodType EDELWOOD = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood").toString());

    public static void registerWoodTypes() {
        WoodType.register(CHERRYWOOD);
        WoodType.register(MYSTERYWOOD);
        WoodType.register(EDELWOOD);
    }
}
