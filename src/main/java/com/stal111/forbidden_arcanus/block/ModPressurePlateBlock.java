package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.block.PressurePlateBlock;

public class ModPressurePlateBlock extends PressurePlateBlock {

	public ModPressurePlateBlock(String name, PressurePlateBlock.Sensitivity sensitivity, Properties properties) {
		super(sensitivity, properties);
		this.setRegistryName(ModUtils.location(name));
	}

}
