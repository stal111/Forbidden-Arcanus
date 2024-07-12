package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.core.config.Config;
import com.stal111.forbidden_arcanus.core.registry.ModRegistryCollector;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForbiddenArcanus.MOD_ID)
public final class ForbiddenArcanus {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);


	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModRegistryCollector(ForbiddenArcanus.MOD_ID));

	public ForbiddenArcanus(IEventBus modEventBus, ModContainer modContainer) {
		ModDefinition.of(ForbiddenArcanus.MOD_ID)
				.withRegistryManager(REGISTRY_MANAGER)
				.withEventHandler(new ModEventHandler(modEventBus))
				.clientSetup(() -> helper -> new ClientSetup(helper, modEventBus))
				.create();

		modEventBus.addListener(CommonSetup::setup);

		modContainer.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ForbiddenArcanus.MOD_ID, path);
	}
}
