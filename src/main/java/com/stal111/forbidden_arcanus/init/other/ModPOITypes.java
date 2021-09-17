package com.stal111.forbidden_arcanus.init.other;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.block.PedestalBlock;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
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

    public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<PointOfInterestType> PEDESTAL = register("pedestal", () -> new PointOfInterestType("pedestal", ImmutableSet.of(NewModBlocks.DARKSTONE_PEDESTAL.get().getDefaultState().with(PedestalBlock.RITUAL, true)), 0, 1));
    public static final RegistryObject<PointOfInterestType> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> new PointOfInterestType("hephaestus_forge", ImmutableSet.of(ModBlocks.HEPHAESTUS_FORGE.getBlock().getDefaultState().with(HephaestusForgeBlock.ACTIVATED, true)), 0, 1));

    private static <T extends PointOfInterestType> RegistryObject<T> register(String name, Supplier<T> type) {
        return POI_TYPES.register(name, type);
    }
}
