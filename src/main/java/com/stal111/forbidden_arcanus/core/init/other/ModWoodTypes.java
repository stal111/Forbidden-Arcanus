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

    public static final WoodType FUNGYSS = new WoodType(new ResourceLocation(ForbiddenArcanus.MOD_ID, "fungyss").toString(), ModBlocks.BlockSetTypes.FUNGYSS);
    public static final WoodType CHERRY = new WoodType(new ResourceLocation(ForbiddenArcanus.MOD_ID, "cherry").toString(), ModBlocks.BlockSetTypes.CHERRY);
    public static final WoodType AURUM = new WoodType(new ResourceLocation(ForbiddenArcanus.MOD_ID, "aurum").toString(), ModBlocks.BlockSetTypes.AURUM);
    public static final WoodType EDELWOOD = new WoodType(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood").toString(), ModBlocks.BlockSetTypes.EDELWOOD);

    public static void registerWoodTypes() {
        WoodType.register(FUNGYSS);
        WoodType.register(CHERRY);
        WoodType.register(AURUM);
        WoodType.register(EDELWOOD);
    }
}
