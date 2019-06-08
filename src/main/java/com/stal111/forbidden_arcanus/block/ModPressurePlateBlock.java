package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.util.ResourceLocation;

public class ModPressurePlateBlock extends PressurePlateBlock {

	public ModPressurePlateBlock(String name, PressurePlateBlock.Sensitivity sensitivity, Properties properties) {
		super(sensitivity, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
