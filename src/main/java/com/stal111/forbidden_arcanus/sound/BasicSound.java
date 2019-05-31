package com.stal111.forbidden_arcanus.sound;

import com.stal111.forbidden_arcanus.Main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class BasicSound extends SoundEvent {

	public BasicSound(String name) {
		super(new ResourceLocation(Main.MODID, name));
	}

}
