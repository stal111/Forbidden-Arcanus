package com.stal111.forbidden_arcanus.sound;

import com.stal111.forbidden_arcanus.util.ModUtils;

import net.minecraft.util.SoundEvent;

public class BasicSound extends SoundEvent {

	public BasicSound(String name) {
		super(ModUtils.location(name));
	}

}
