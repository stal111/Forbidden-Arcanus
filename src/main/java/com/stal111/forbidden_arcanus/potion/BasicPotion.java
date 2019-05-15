package com.stal111.forbidden_arcanus.potion;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class BasicPotion extends Potion {

	public BasicPotion(String name, boolean isBadEffectIn, int iconIndex, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(new ResourceLocation(Main.MODID, name));
		this.setIconIndex(iconIndex, 0);
	}

	@Override
	public boolean hasStatusIcon() {
		Minecraft.getInstance().getRenderManager().textureManager
				.bindTexture(new ResourceLocation(Main.MODID, "textures/gui/container/custom_effects.png"));
		return true;
	}

}
