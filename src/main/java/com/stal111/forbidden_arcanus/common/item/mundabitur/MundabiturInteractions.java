package com.stal111.forbidden_arcanus.common.item.mundabitur;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.ModBlockPatterns;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 * @since 05.11.2023
 */
public class MundabiturInteractions {

    public static final MappedRegistryHelper<MundabiturInteraction<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(FARegistries.MUNDABITUR_INTERACTION);

    public static final RegistryEntry<ChargeCreeperInteraction> CHARGE_CREEPER = HELPER.register("charge_creeper", ChargeCreeperInteraction::new);
    public static final RegistryEntry<TransformPatternInteraction> CREATE_HEPHAESTUS_FORGE = HELPER.register("create_hephaestus_forge", () -> new CreateForgeInteraction(BlockStatePredicate.forBlock(Blocks.SMITHING_TABLE), ModBlockPatterns.HEPHAESTUS_PATTERN));
    public static final RegistryEntry<TransformPatternInteraction> CREATE_OBELISK = HELPER.register("create_obelisk", () -> new CreateObeliskInteraction(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK.get()).or(BlockStatePredicate.forBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())), ModBlockPatterns.ARCANE_CRYSTAL_OBELISK_PATTERN));
    public static final RegistryEntry<TransformPatternInteraction> CREATE_CLIBANO = HELPER.register("create_clibano", () -> new CreateClibanoInteraction(BlockStatePredicate.forBlock(ModBlocks.CLIBANO_CORE.get()), ModBlockPatterns.CLIBANO_COMBUSTION_BASE));


}
