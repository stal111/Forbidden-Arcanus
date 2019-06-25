package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.ModBlocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModTileEntities {

	public static final TileEntityType<ModSignTileEntity> sign = null;

	public static final TileEntityType<DarkBeaconTileEntity> dark_beacon = null;
	
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> registry) {
		registerAll(registry,
				TileEntityType.Builder.create(ModSignTileEntity::new, ModBlocks.edelwood_sign, ModBlocks.edelwood_wall_sign, ModBlocks.cherrywood_sign, ModBlocks.cherrywood_wall_sign, ModBlocks.mysterywood_sign, ModBlocks.mysterywood_wall_sign).build(null).setRegistryName(Main.MOD_ID, "sign"),
				TileEntityType.Builder.create(DarkBeaconTileEntity::new, ModBlocks.dark_beacon).build(null).setRegistryName(Main.MOD_ID, "dark_beacon"));
	}
	
	public static void registerAll(RegistryEvent.Register<TileEntityType<?>> registry, TileEntityType<?>... tiles) {
		for (TileEntityType<?> tile : tiles) {
			registry.getRegistry().register(tile);
		}
	}

}
