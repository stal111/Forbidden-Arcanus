package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.Main;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModTileEntities {

	public static final TileEntityType<ModSignTileEntity> sign = null;

	public static final TileEntityType<DarkBeaconTileEntity> dark_beacon = null;
	
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> registry) {
		registerAll(registry,
				TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.EDELWOOD_SIGN.getBlock(), ModBlocks.EDELWOOD_WALL_SIGN.getBlock(), ModBlocks.CHERRYWOOD_SIGN.getBlock(), ModBlocks.CHERRYWOOD_WALL_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_SIGN.getBlock(), ModBlocks.MYSTERYWOOD_WALL_SIGN.getBlock()).build(null).setRegistryName(Main.MOD_ID, "sign"),
				TileEntityType.Builder.create(DarkBeaconTileEntity::new, ModBlocks.DARK_BEACON.getBlock()).build(null).setRegistryName(Main.MOD_ID, "dark_beacon"));
	}
	
	public static void registerAll(RegistryEvent.Register<TileEntityType<?>> registry, TileEntityType<?>... tiles) {
		for (TileEntityType<?> tile : tiles) {
			registry.getRegistry().register(tile);
		}
	}
}
