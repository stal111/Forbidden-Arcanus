package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.*;
import com.stal111.forbidden_arcanus.common.tile.ArcaneCrystalObeliskTileEntity;
import com.stal111.forbidden_arcanus.common.tile.forge.HephaestusForgeTileEntity;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModTileEntities {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<BlockEntityType<ModSignTileEntity>> SIGN = register("sign", () -> BlockEntityType.Builder.of(ModSignTileEntity::new, ModBlocks.EDELWOOD_SIGN.getBlock(), ModBlocks.EDELWOOD_WALL_SIGN.getBlock(), ModBlocks.CHERRYWOOD_SIGN.getBlock(), ModBlocks.CHERRYWOOD_WALL_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_WALL_SIGN.getBlock()).build(null));
    public static final RegistryObject<BlockEntityType<BlackHoleTileEntity>> BLACK_HOLE = register("black_hole", () -> BlockEntityType.Builder.of(BlackHoleTileEntity::new, NewModBlocks.BLACK_HOLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ObsidianSkullTileEntity>> OBSIDIAN_SKULL = register("obsidian_skull", () -> BlockEntityType.Builder.of(ObsidianSkullTileEntity::new, NewModBlocks.OBSIDIAN_SKULL.get(), NewModBlocks.OBSIDIAN_WALL_SKULL.get(), NewModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get()).build(null));
    public static final RegistryObject<BlockEntityType<UtremJarTileEntity>> UTREM_JAR = register("utrem_jar", () -> BlockEntityType.Builder.of(UtremJarTileEntity::new, NewModBlocks.UTREM_JAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<NipaTileEntity>> NIPA = register("nipa", () -> BlockEntityType.Builder.of(NipaTileEntity::new, NewModBlocks.NIPA.get()).build(null));
    public static final RegistryObject<BlockEntityType<HephaestusForgeTileEntity>> HEPHAESTUS_FORGE = register("hephaestus_forge", () -> BlockEntityType.Builder.of(HephaestusForgeTileEntity::new, ModBlocks.HEPHAESTUS_FORGE.getBlock()).build(null));
    public static final RegistryObject<BlockEntityType<PedestalTileEntity>> PEDESTAL = register("pedestal", () -> BlockEntityType.Builder.of(PedestalTileEntity::new, NewModBlocks.DARKSTONE_PEDESTAL.get(), NewModBlocks.ARCANE_DARKSTONE_PEDESTAL.get()).build(null));
    public static final RegistryObject<BlockEntityType<ArcaneCrystalObeliskTileEntity>> ARCANE_CRYSTAL_OBELISK = register("arcane_crystal_obelisk", () -> BlockEntityType.Builder.of(ArcaneCrystalObeliskTileEntity::new, NewModBlocks.ARCANE_CRYSTAL_OBELISK.get()).build(null));

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> tileEntityType) {
        return TILE_ENTITIES.register(name, tileEntityType);
    }
}
