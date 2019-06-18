package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.util.ResourceLocation;

public class ModUtils {
	
	public static ResourceLocation location(String path) {
		return new ResourceLocation(Main.MOD_ID, path);
	}

}
