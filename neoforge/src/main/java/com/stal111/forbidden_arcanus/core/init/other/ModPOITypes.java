package com.stal111.forbidden_arcanus.core.init.other;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.HephaestusForgeBlock;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.BlockRegistryEntry;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2021-07-31
 */
public class ModPOITypes implements RegistryClass {

    private static final List<BlockRegistryEntry<HephaestusForgeBlock>> HEPHAESTUS_FORGE_BLOCKS = ImmutableList.of(
            ModBlocks.HEPHAESTUS_FORGE_TIER_1,
            ModBlocks.HEPHAESTUS_FORGE_TIER_2,
            ModBlocks.HEPHAESTUS_FORGE_TIER_3,
            ModBlocks.HEPHAESTUS_FORGE_TIER_4,
            ModBlocks.HEPHAESTUS_FORGE_TIER_5
    );

    public static final MappedRegistryHelper<PoiType> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.POINT_OF_INTEREST_TYPE);

    public static final RegistryEntry<PoiType, PoiType> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> new PoiType(getStates(HEPHAESTUS_FORGE_BLOCKS, state -> state.getValue(ModBlockStateProperties.ACTIVATED)), 0, 1));
    public static final RegistryEntry<PoiType, PoiType> CLIBANO_MAIN_PART = HELPER.register("clibano_main_part", () -> new PoiType(getStates(ModBlocks.CLIBANO_MAIN_PART.get()), 0, 1));
    public static final RegistryEntry<PoiType, PoiType> AUREAL_UTREM_JAR = HELPER.register("aureal_utrem_jar", () -> new PoiType(getStates(ModBlocks.ESSENCE_UTREM_JAR.get(), state -> state.getValue(ModBlockStateProperties.ESSENCE_TYPE) == EssenceType.AUREAL), 0, 1));
    public static final RegistryEntry<PoiType, PoiType> SOULS_UTREM_JAR = HELPER.register("souls_utrem_jar", () -> new PoiType(getStates(ModBlocks.ESSENCE_UTREM_JAR.get(), state -> state.getValue(ModBlockStateProperties.ESSENCE_TYPE) == EssenceType.SOULS), 0, 1));
    public static final RegistryEntry<PoiType, PoiType> BLOOD_UTREM_JAR = HELPER.register("blood_utrem_jar", () -> new PoiType(getStates(ModBlocks.ESSENCE_UTREM_JAR.get(), state -> state.getValue(ModBlockStateProperties.ESSENCE_TYPE) == EssenceType.BLOOD), 0, 1));
    public static final RegistryEntry<PoiType, PoiType> EXPERIENCE_UTREM_JAR = HELPER.register("experience_utrem_jar", () -> new PoiType(getStates(ModBlocks.ESSENCE_UTREM_JAR.get(), state -> state.getValue(ModBlockStateProperties.ESSENCE_TYPE) == EssenceType.EXPERIENCE), 0, 1));

    private static ImmutableSet<BlockState> getStates(Block block) {
        return getStates(block, state -> true);
    }

    private static ImmutableSet<BlockState> getStates(List<? extends Supplier<? extends Block>> blocks) {
        return getStates(blocks, state -> true);
    }

    private static ImmutableSet<BlockState> getStates(Block block, Predicate<BlockState> predicate) {
        return getStates(ImmutableList.of(() -> block), predicate);
    }

    private static ImmutableSet<BlockState> getStates(List<? extends Supplier<? extends Block>> blocks, Predicate<BlockState> predicate) {
        return ImmutableSet.copyOf(blocks.stream().flatMap(block -> block.get().getStateDefinition().getPossibleStates().stream()).filter(predicate).toList());
    }
}
