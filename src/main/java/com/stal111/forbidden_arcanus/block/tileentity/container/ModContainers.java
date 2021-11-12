package com.stal111.forbidden_arcanus.block.tileentity.container;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(ForbiddenArcanus.MOD_ID)
public class ModContainers {

	public static MenuType<DarkBeaconContainer> dark_beacon;

	public static void register(RegistryEvent.Register<MenuType<?>> registry) {
		dark_beacon = IForgeContainerType.create((windowID, inv, data) ->
				new DarkBeaconContainer(windowID, ForbiddenArcanus.proxy.getClientWorld(), data.readBlockPos(), inv, ForbiddenArcanus.proxy.getClientPlayer()));
		registerContainer(dark_beacon, "dark_beacon");
		registry.getRegistry().register(dark_beacon);
	}

	public static <T extends AbstractContainerMenu> MenuType<T> registerContainer(MenuType<T> type, String id) {
		type.setRegistryName(ForbiddenArcanus.MOD_ID, id);
		return type;
	}
}
