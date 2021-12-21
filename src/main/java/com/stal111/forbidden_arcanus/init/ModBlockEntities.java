package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.*;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * Mod Block Entities <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.init.ModBlockEntities
 *
 * @author stal111
 * @version 2.0.0
 */
public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<BlockEntityType<BlackHoleBlockEntity>> BLACK_HOLE = register("black_hole", () -> BlockEntityType.Builder.of(BlackHoleBlockEntity::new, ModBlocks.BLACK_HOLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ObsidianSkullBlockEntity>> OBSIDIAN_SKULL = register("obsidian_skull", () -> BlockEntityType.Builder.of(ObsidianSkullBlockEntity::new, ModBlocks.OBSIDIAN_SKULL.get(), ModBlocks.OBSIDIAN_WALL_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UtremJarBlockEntity>> UTREM_JAR = register("utrem_jar", () -> BlockEntityType.Builder.of(UtremJarBlockEntity::new, ModBlocks.UTREM_JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NipaBlockEntity>> NIPA = register("nipa", () -> BlockEntityType.Builder.of(NipaBlockEntity::new, ModBlocks.NIPA.get()).build(null));
    public static final RegistryObject<BlockEntityType<HephaestusForgeBlockEntity>> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> BlockEntityType.Builder.of(HephaestusForgeBlockEntity::new, ModBlocks.HEPHAESTUS_FORGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = register("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.ARCANE_DARKSTONE_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ArcaneCrystalObeliskBlockEntity>> ARCANE_CRYSTAL_OBELISK = register("arcane_crystal_obelisk", () -> BlockEntityType.Builder.of(ArcaneCrystalObeliskBlockEntity::new, ModBlocks.ARCANE_CRYSTAL_OBELISK.get()).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> tileEntityType) {
        return TILE_ENTITIES.register(name, tileEntityType);
    }
}
