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

    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<BlackHoleBlockEntity>> BLACK_HOLE = HELPER.register("black_hole", () -> BlockEntityType.Builder.of(BlackHoleBlockEntity::new, ModBlocks.BLACK_HOLE.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ObsidianSkullBlockEntity>> OBSIDIAN_SKULL = HELPER.register("obsidian_skull", () -> BlockEntityType.Builder.of(ObsidianSkullBlockEntity::new, ModBlocks.OBSIDIAN_SKULL.getSkull(), ModBlocks.OBSIDIAN_SKULL.getWallSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getSkull(), ModBlocks.CRACKED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getSkull(), ModBlocks.FRAGMENTED_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getSkull(), ModBlocks.FADING_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull(), ModBlocks.AUREALIC_OBSIDIAN_SKULL.getWallSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.getWallSkull()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<UtremJarBlockEntity>> UTREM_JAR = HELPER.register("utrem_jar", () -> BlockEntityType.Builder.of(UtremJarBlockEntity::new, ModBlocks.UTREM_JAR.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<NipaBlockEntity>> NIPA = HELPER.register("nipa", () -> BlockEntityType.Builder.of(NipaBlockEntity::new, ModBlocks.NIPA.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<HephaestusForgeBlockEntity>> HEPHAESTUS_FORGE = HELPER.register("hephaestus_forge", () -> BlockEntityType.Builder.of(HephaestusForgeBlockEntity::new, ModBlocks.HEPHAESTUS_FORGE_TIER_1.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_2.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_3.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_4.get(), ModBlocks.HEPHAESTUS_FORGE_TIER_5.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL = HELPER.register("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.MAGNETIZED_DARKSTONE_PEDESTAL.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ArcaneCrystalObeliskBlockEntity>> ARCANE_CRYSTAL_OBELISK = HELPER.register("arcane_crystal_obelisk", () -> BlockEntityType.Builder.of(ArcaneCrystalObeliskBlockEntity::new, ModBlocks.ARCANE_CRYSTAL_OBELISK.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ClibanoMainBlockEntity>> CLIBANO_MAIN = HELPER.register("clibano_main", () -> BlockEntityType.Builder.of(ClibanoMainBlockEntity::new, ModBlocks.CLIBANO_MAIN_PART.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ClibanoFrameBlockEntity>> CLIBANO = HELPER.register("clibano", () -> BlockEntityType.Builder.of(ClibanoFrameBlockEntity::new, ModBlocks.CLIBANO_CORNER.get(), ModBlocks.CLIBANO_CENTER.get(), ModBlocks.CLIBANO_SIDE_HORIZONTAL.get(), ModBlocks.CLIBANO_SIDE_VERTICAL.get()).build(null));
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<ResearchDeskBlockEntity>> RESEARCH_DESK = HELPER.register("research_desk", () -> BlockEntityType.Builder.of(ResearchDeskBlockEntity::new, ModBlocks.RESEARCH_DESK.get()).build(null));

}
