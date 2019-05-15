package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.util.ResourceLocation;

public class ModPressurePlateBlock extends BlockPressurePlate {

	public ModPressurePlateBlock(String name, BlockPressurePlate.Sensitivity sensitivity, Properties properties) {
		super(sensitivity, properties);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
	}

}
