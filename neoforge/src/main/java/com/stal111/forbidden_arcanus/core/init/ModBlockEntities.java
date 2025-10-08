package com.stal111.forbidden_arcanus.core.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.*;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoFrameBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.desk.ResearchDeskBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryClass;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryEntry;
import net.valhelsia.valhelsia_core.api.common.registry.helper.MappedRegistryHelper;

/**
 * @author stal111
 */
public class ModBlockEntities implements RegistryClass {

    public static final MappedRegistryHelper<BlockEntityType<?>> HELPER = ForbiddenArcanus.REGISTRY_MANAGER.getHelper(Registries.BLOCK_ENTITY_TYPE);

    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<BlackHoleBlockEntity>> BLACK_HOLE = HELPER.register("black_hole", () -> new BlockEntityType<>(BlackHoleBlockEntity::new, ModBlocks.BLACK_HOLE.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<EssenceUtremJarBlockEntity>> ESSENCE_UTREM_JAR = HELPER.register("essence_utrem_jar", () -> new BlockEntityType<>(EssenceUtremJarBlockEntity::new, ModBlocks.ESSENCE_UTREM_JAR.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<HephaestusForgeBlockEntity>> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> new BlockEntityType<>(HephaestusForgeBlockEntity::new, ModBlocks.HEPHAESTUS_FORGE_TIER_1.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_2.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_3.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_4.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_5.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL = HELPER.register("pedestal", () -> new BlockEntityType<>(PedestalBlockEntity::new, ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ArcaneCrystalObeliskBlockEntity>> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", () -> new BlockEntityType<>(ArcaneCrystalObeliskBlockEntity::new, ModBlocks.ARCANE_CRYSTAL_OBELISK.get(), ModBlocks.CORRUPTED_ARCANE_CRYSTAL_OBELISK.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ClibanoMainBlockEntity>> CLIBANO_MAIN = HELPER.register("clibano_main", () -> new BlockEntityType<>(ClibanoMainBlockEntity::new, ModBlocks.CLIBANO_MAIN_PART.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ClibanoFrameBlockEntity>> CLIBANO = HELPER.register("clibano", () -> new BlockEntityType<>(ClibanoFrameBlockEntity::new, ModBlocks.CLIBANO_CORNER.get(), ModBlocks.CLIBANO_CENTER.get(), ModBlocks.CLIBANO_SIDE_HORIZONTAL.get(), ModBlocks.CLIBANO_SIDE_VERTICAL.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ResearchDeskBlockEntity>> RESEARCH_DESK = HELPER.register("research_desk", () -> new BlockEntityType<>(ResearchDeskBlockEntity::new, ModBlocks.RESEARCH_DESK.get()));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<QuantumInjectorBlockEntity>> QUANTUM_INJECTOR = HELPER.register("quantum_injector", () -> new BlockEntityType<>(QuantumInjectorBlockEntity::new, ModBlocks.QUANTUM_INJECTOR.get()));

}
