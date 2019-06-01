package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.entity.ModEntities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy {

	@Override
	public void setup(FMLCommonSetupEvent event) {
		
	}
	
	@Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    @Override
    public World getClientWorld() {
        return Minecraft.getInstance().world;
    }
    
    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModEntities.initModels();
    }
}
