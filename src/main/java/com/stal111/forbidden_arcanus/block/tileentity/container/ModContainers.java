package com.stal111.forbidden_arcanus.block.tileentity.container;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModContainers {

	public static ContainerType<DarkBeaconContainer> dark_beacon;

	public static void register(RegistryEvent.Register<ContainerType<?>> registry) {
		dark_beacon = IForgeContainerType.create((windowID, inv, data) ->
				new DarkBeaconContainer(windowID, Main.proxy.getClientWorld(), data.readBlockPos(), inv, Main.proxy.getClientPlayer()));
		registerContainer(dark_beacon, "dark_beacon");
		registry.getRegistry().register(dark_beacon);
	}

	public static <T extends Container> ContainerType<T> registerContainer(ContainerType<T> type, String id) {
		type.setRegistryName(Main.MOD_ID, id);
		return type;
	}
}
