package com.stal111.forbidden_arcanus.core.init.world;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

/**
 * Mod Trunk Placers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.init.world.ModTrunkPlacers
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-03-26
 */
public class ModTrunkPlacers {

    public static final TrunkPlacerType<CherryTrunkPlacer> CHERRY_TRUNK_PLACER = register("cherry_trunk_placer", CherryTrunkPlacer.CODEC);

    //TODO
    private static <P extends TrunkPlacer> TrunkPlacerType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registries.TRUNK_PLACER_TYPE, new ResourceLocation(ForbiddenArcanus.MOD_ID, name), new TrunkPlacerType<>(codec));
    }

    public static void load() {

    }
}
