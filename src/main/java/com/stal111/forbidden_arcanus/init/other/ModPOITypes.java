package com.stal111.forbidden_arcanus.init.other;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.PedestalBlock;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Mod POI Types
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModPOITypes
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-31
 */
public class ModPOITypes {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<PoiType> PEDESTAL = register("pedestal", () -> new PoiType("pedestal", ImmutableSet.of(NewModBlocks.DARKSTONE_PEDESTAL.get().defaultBlockState().setValue(PedestalBlock.RITUAL, true)), 0, 1));
    public static final RegistryObject<PoiType> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> new PoiType("hephaestus_forge", ImmutableSet.of(NewModBlocks.HEPHAESTUS_FORGE.get().defaultBlockState().setValue(HephaestusForgeBlock.ACTIVATED, true)), 0, 1));

    private static <T extends PoiType> RegistryObject<T> register(String name, Supplier<T> type) {
        return POI_TYPES.register(name, type);
    }
}
