package com.stal111.forbidden_arcanus.core.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.io.File;

/**
 * Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.config.Config
 *
 * @author Valhelsia Team
 * @version 16.2.0
 */
@Mod.EventBusSubscriber
public class Config {

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec COMMON_CONFIG;
	public static final ForgeConfigSpec CLIENT_CONFIG;

	static {
		WorldGenConfig.init(COMMON_BUILDER);
		ItemConfig.init(COMMON_BUILDER);
		BlockConfig.init(COMMON_BUILDER);
		RenderingConfig.init(CLIENT_BUILDER);
		EnchantmentConfig.init(COMMON_BUILDER);
		AurealConfig.init(COMMON_BUILDER);

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}

	public static void loadConfig(ForgeConfigSpec config, String path) {
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).preserveInsertionOrder().sync().autosave().writingMode(WritingMode.REPLACE).build();
		file.load();
		config.setConfig(file);
	}
}
