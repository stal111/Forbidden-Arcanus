package com.stal111.forbidden_arcanus.core.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

/**
 * Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.config.Config
 *
 * @author Valhelsia Team
 */
@Mod.EventBusSubscriber
public class Config {

	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

	public static final ForgeConfigSpec COMMON_CONFIG;
	public static final ForgeConfigSpec CLIENT_CONFIG;

	static {
		ItemConfig.init(COMMON_BUILDER);
		BlockConfig.init(COMMON_BUILDER);
		RenderingConfig.init(CLIENT_BUILDER);
		ModifierConfig.init(COMMON_BUILDER);
		AurealConfig.init(COMMON_BUILDER);

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}
}
