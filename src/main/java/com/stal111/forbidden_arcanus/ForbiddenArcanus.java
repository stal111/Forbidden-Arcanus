package com.stal111.forbidden_arcanus;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.core.config.Config;
import com.stal111.forbidden_arcanus.core.registry.ModRegistryCollector;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
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

	public ForbiddenArcanus() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModDefinition.of(ForbiddenArcanus.MOD_ID)
				.withRegistryManager(REGISTRY_MANAGER)
				.withEventHandler(new ModEventHandler(modEventBus))
				.clientSetup(() -> ClientSetup::new)
				.create();

		modEventBus.addListener(CommonSetup::setup);

		this.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		this.registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
	}

	private void registerConfig(ModConfig.Type type, ForgeConfigSpec configSpec) {
		ModLoadingContext.get().registerConfig(type, configSpec);

		this.loadConfig(configSpec, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-" + type.name().toLowerCase(Locale.ROOT) + ".toml").toString());
	}

	private void loadConfig(ForgeConfigSpec configSpec, String path) {
		CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().sync().autosave().writingMode(WritingMode.REPLACE).build();

		file.load();
		configSpec.setConfig(file);
	}

	private void setup(final FMLCommonSetupEvent event) {

		//CounterHelper.addCounter(CounterCreator.of(resourceLocation -> new SerializableCounter(resourceLocation, 0, false), new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer")));
	}
}
