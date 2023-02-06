package com.stal111.forbidden_arcanus.core.init.other;

import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Mod POI Types
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.other.ModPOITypes
 *
 * @author stal111
 * @since 2021-07-31
 */
public class ModPOITypes {

    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, ForbiddenArcanus.MOD_ID);

    //TODO: Add POI Tags
    public static final RegistryObject<PoiType> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> new PoiType(ModBlocks.HEPHAESTUS_FORGE.get().getStateDefinition().getPossibleStates().stream().filter(state -> state.getValue(HephaestusForgeBlock.ACTIVATED)).collect(Collectors.toUnmodifiableSet()), 0, 1));
    public static final RegistryObject<PoiType> CLIBANO_MAIN_PART = register("clibano_main_part", () -> new PoiType(ImmutableSet.of(ModBlocks.CLIBANO_MAIN_PART.get().defaultBlockState()), 0, 1));

    private static <T extends PoiType> RegistryObject<T> register(String name, Supplier<T> type) {
        return POI_TYPES.register(name, type);
    }
}
