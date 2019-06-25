package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import com.stal111.forbidden_arcanus.block.tileentity.container.ModContainers;
import com.stal111.forbidden_arcanus.block.tileentity.render.DarkBeaconTileEntityRenderer;
import com.stal111.forbidden_arcanus.block.tileentity.render.ModSignTileEntityRenderer;
import com.stal111.forbidden_arcanus.block.tileentity.screen.DarkBeaconScreen;
import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.world.gen.OreGenerator;
import com.stal111.forbidden_arcanus.world.gen.WorldGenerator;

import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SideProxy {

	public SideProxy() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::processIMC);

		MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
	}

	private static void commonSetup(FMLCommonSetupEvent event) {
		OreGenerator.setupOregen();
		WorldGenerator.setupWorldGen();
	}

	private static void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private static void processIMC(final InterModProcessEvent event) {
	}

	private static void serverStarting(FMLServerStartingEvent event) {
	}

	public static class Client extends SideProxy {

		public Client() {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
		}

		@OnlyIn(Dist.CLIENT)
		private static void clientSetup(FMLClientSetupEvent event) {
			ModEntities.initModels();
			ClientRegistry.bindTileEntitySpecialRenderer(ModSignTileEntity.class, new ModSignTileEntityRenderer());
			ClientRegistry.bindTileEntitySpecialRenderer(DarkBeaconTileEntity.class, new DarkBeaconTileEntityRenderer());
			ScreenManager.registerFactory(ModContainers.dark_beacon, DarkBeaconScreen::new);
		}
	}

	public static class Server extends SideProxy {

		public Server() {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
		}

		@OnlyIn(Dist.DEDICATED_SERVER)
		private static void serverSetup(FMLDedicatedServerSetupEvent event) {
		}
	}

}
