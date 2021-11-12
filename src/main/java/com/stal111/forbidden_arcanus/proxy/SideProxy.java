package com.stal111.forbidden_arcanus.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class SideProxy {

	public SideProxy() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::processIMC);
	}

	private static void commonSetup(FMLCommonSetupEvent event) {
		//OreGenerator.setupOreGen();
		//WorldGenerator.setupWorldGen();
	}

	private static void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private static void processIMC(final InterModProcessEvent event) {
	}


	public abstract Level getClientWorld();

	public abstract Player getClientPlayer();

	public static class Client extends SideProxy {

		public Level getClientWorld() {
			return Minecraft.getInstance().level;
		}

		public Player getClientPlayer() {
			return Minecraft.getInstance().player;
		}
	}

	public static class Server extends SideProxy {

		public Server() {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
		}

		@OnlyIn(Dist.DEDICATED_SERVER)
		private static void serverSetup(FMLDedicatedServerSetupEvent event) {
		}

		public Level getClientWorld() {
			throw new IllegalStateException("Only run on the client");
		}

		public Player getClientPlayer() {
			throw new IllegalStateException("Only run on the client");
		}
	}

}
