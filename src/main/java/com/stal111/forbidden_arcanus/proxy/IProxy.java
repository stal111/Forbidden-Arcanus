package com.stal111.forbidden_arcanus.proxy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public interface IProxy {
	
	void setup(final FMLCommonSetupEvent event);
	
	PlayerEntity getClientPlayer();
	
	World getClientWorld();

}
