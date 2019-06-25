package com.stal111.forbidden_arcanus.block.tileentity.container;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MOD_ID)
public class ModContainers {

	public static final ContainerType<DarkBeaconContainer> dark_beacon = null;

	public static void register(RegistryEvent.Register<ContainerType<?>> registry) {
		registerAll(registry, IForgeContainerType.create((windowId, inv, data) -> {
			return new DarkBeaconContainer(windowId, inv);
		}).setRegistryName(Main.MOD_ID, "dark_beacon"));
	}

	public static void registerAll(RegistryEvent.Register<ContainerType<?>> registry, ContainerType<?>... containers) {
		for (ContainerType<?> container : containers) {
			registry.getRegistry().register(container);
		}
	}

}
