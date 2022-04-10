package com.stal111.forbidden_arcanus.core.init.world;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.world.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Mod Foliage Placers <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.init.world.ModFoliagePlacers
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-08
 */
public class ModFoliagePlacers {

    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<CherryFoliagePlacer>> CHERRY_FOLIAGE_PLACER = register("cherry_foliage_placer", CherryFoliagePlacer.CODEC);

    private static<T extends FoliagePlacer> RegistryObject<FoliagePlacerType<T>> register(String name, Codec<T> codec) {
        return FOLIAGE_PLACERS.register(name, () -> new FoliagePlacerType<>(codec));
    }

    public static void load() {

    }
}
