package com.stal111.forbidden_arcanus.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ServerProxy implements IProxy {

	@Override
	public void setup(FMLCommonSetupEvent event) {

	}

	@Override
	public PlayerEntity getClientPlayer() {
		throw new IllegalStateException("Can't call this server-side!");
	}

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Can't call this server-side!");
	}

}
