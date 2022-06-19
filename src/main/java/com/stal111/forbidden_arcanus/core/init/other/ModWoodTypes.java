package com.stal111.forbidden_arcanus.core.init.other;

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

    public static final WoodType FUNGYSS = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "fungyss").toString());
    public static final WoodType CHERRY = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "cherry").toString());
    public static final WoodType AURUM = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aurum").toString());
    public static final WoodType EDELWOOD = WoodType.create(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood").toString());

    public static void registerWoodTypes() {
        WoodType.register(FUNGYSS);
        WoodType.register(CHERRY);
        WoodType.register(AURUM);
        WoodType.register(EDELWOOD);
    }
}
