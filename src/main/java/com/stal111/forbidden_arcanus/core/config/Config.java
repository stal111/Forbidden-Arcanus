package com.stal111.forbidden_arcanus.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Config
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.config.Config
 *
 * @author Valhelsia Team
 */
public class Config {

	private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
	private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();

	public static final ModConfigSpec COMMON_CONFIG;
	public static final ModConfigSpec CLIENT_CONFIG;

	static {
		ItemConfig.init(COMMON_BUILDER);
		BlockConfig.init(COMMON_BUILDER);
		RenderingConfig.init(CLIENT_BUILDER);
		AurealConfig.init(COMMON_BUILDER);

		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}
}
