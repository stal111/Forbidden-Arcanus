package com.stal111.forbidden_arcanus.init;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.ObsidianSkullTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.UtremJarTileEntity;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModTileEntities {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ForbiddenArcanus.MOD_ID);

    public static final RegistryObject<TileEntityType<ModSignTileEntity>> SIGN = register("sign", () -> TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.EDELWOOD_SIGN.getBlock(), ModBlocks.EDELWOOD_WALL_SIGN.getBlock(), ModBlocks.CHERRYWOOD_SIGN.getBlock(), ModBlocks.CHERRYWOOD_WALL_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_WALL_SIGN.getBlock()).build(null));
    public static final RegistryObject<TileEntityType<BlackHoleTileEntity>> BLACK_HOLE = register("black_hole", () -> TileEntityType.Builder.create(BlackHoleTileEntity::new, NewModBlocks.BLACK_HOLE.get()).build(null));
    public static final RegistryObject<TileEntityType<ObsidianSkullTileEntity>> OBSIDIAN_SKULL = register("obsidian_skull", () -> TileEntityType.Builder.create(ObsidianSkullTileEntity::new, NewModBlocks.OBSIDIAN_SKULL.get(), NewModBlocks.OBSIDIAN_WALL_SKULL.get(), NewModBlocks.ETERNAL_OBSIDIAN_SKULL.get(), NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get()).build(null));
    public static final RegistryObject<TileEntityType<UtremJarTileEntity>> UTREM_JAR = register("utrem_jar", () -> TileEntityType.Builder.create(UtremJarTileEntity::new, NewModBlocks.UTREM_JAR.get()).build(null));
    public static final RegistryObject<TileEntityType<NipaTileEntity>> NIPA = register("nipa", () -> TileEntityType.Builder.create(NipaTileEntity::new, NewModBlocks.NIPA.get()).build(null));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType<T>> tileEntityType) {
        return TILE_ENTITIES.register(name, tileEntityType);
    }
}
