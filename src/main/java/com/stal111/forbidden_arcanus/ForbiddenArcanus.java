package com.stal111.forbidden_arcanus;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.core.config.Config;
import com.stal111.forbidden_arcanus.core.registry.ModRegistryCollector;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.Locale;

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

		this.registerConfig(modContainer, ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		this.registerConfig(modContainer, ModConfig.Type.COMMON, Config.COMMON_CONFIG);
	}

	public static ResourceLocation location(String path) {
		return ResourceLocation.fromNamespaceAndPath(ForbiddenArcanus.MOD_ID, path);
	}

	private void registerConfig(ModContainer modContainer, ModConfig.Type type, ModConfigSpec configSpec) {
		modContainer.registerConfig(type, configSpec);

		this.loadConfig(configSpec, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-" + type.name().toLowerCase(Locale.ROOT) + ".toml").toString());
	}

	private void loadConfig(ModConfigSpec configSpec, String path) {
		CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().sync().autosave().writingMode(WritingMode.REPLACE).build();

		file.load();
		configSpec.setConfig(file);
	}
}
