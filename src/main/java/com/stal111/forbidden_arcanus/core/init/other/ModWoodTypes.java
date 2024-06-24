package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;

/**
 * Mod Wood Types <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModWoodTypes
 *
 * @author stal111
 * @since 2021-11-22
 */
public class ModWoodTypes {

    public static final WoodType FUNGYSS = new WoodType(ForbiddenArcanus.location("fungyss").toString(), ModBlocks.BlockSetTypes.FUNGYSS);
    public static final WoodType AURUM = new WoodType(ForbiddenArcanus.location("aurum").toString(), ModBlocks.BlockSetTypes.AURUM);
    public static final WoodType EDELWOOD = new WoodType(ForbiddenArcanus.location("edelwood").toString(), ModBlocks.BlockSetTypes.EDELWOOD);

    public static void registerWoodTypes() {
        WoodType.register(FUNGYSS);
        WoodType.register(AURUM);
        WoodType.register(EDELWOOD);
    }
}
