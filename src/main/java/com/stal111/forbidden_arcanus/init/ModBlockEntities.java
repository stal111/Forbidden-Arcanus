package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.*;
import com.stal111.forbidden_arcanus.common.block.entity.NipaBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity;
import com.stal111.forbidden_arcanus.common.tile.ArcaneCrystalObeliskTileEntity;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
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

    public static final RegistryObject<BlockEntityType<BlackHoleTileEntity>> BLACK_HOLE = register("black_hole", () -> BlockEntityType.Builder.of(BlackHoleTileEntity::new, ModBlocks.BLACK_HOLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ObsidianSkullTileEntity>> OBSIDIAN_SKULL = register("obsidian_skull", () -> BlockEntityType.Builder.of(ObsidianSkullTileEntity::new, ModBlocks.OBSIDIAN_SKULL.get(), ModBlocks.OBSIDIAN_WALL_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UtremJarTileEntity>> UTREM_JAR = register("utrem_jar", () -> BlockEntityType.Builder.of(UtremJarTileEntity::new, ModBlocks.UTREM_JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NipaBlockEntity>> NIPA = register("nipa", () -> BlockEntityType.Builder.of(NipaBlockEntity::new, ModBlocks.NIPA.get()).build(null));
    public static final RegistryObject<BlockEntityType<HephaestusForgeTileEntity>> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> BlockEntityType.Builder.of(HephaestusForgeTileEntity::new, ModBlocks.HEPHAESTUS_FORGE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalBlockEntity>> PEDESTAL = register("pedestal", () -> BlockEntityType.Builder.of(PedestalBlockEntity::new, ModBlocks.DARKSTONE_PEDESTAL.get(), ModBlocks.ARCANE_DARKSTONE_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ArcaneCrystalObeliskTileEntity>> ARCANE_CRYSTAL_OBELISK = register("arcane_crystal_obelisk", () -> BlockEntityType.Builder.of(ArcaneCrystalObeliskTileEntity::new, ModBlocks.ARCANE_CRYSTAL_OBELISK.get()).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> tileEntityType) {
        return TILE_ENTITIES.register(name, tileEntityType);
    }
}
