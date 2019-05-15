package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.util.ResourceLocation;

public class ModFenceGateBlock extends BlockFenceGate {

	public ModFenceGateBlock(String name, Properties properties) {
		super(properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
