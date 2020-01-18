package com.stal111.forbidden_arcanus.proxy;

import com.stal111.forbidden_arcanus.world.gen.OreGenerator;
import com.stal111.forbidden_arcanus.world.gen.WorldGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class SideProxy {

	public SideProxy() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::processIMC);

		MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
	}

	private static void commonSetup(FMLCommonSetupEvent event) {
		OreGenerator.setupOreGen();
		WorldGenerator.setupWorldGen();
	}

	private static void enqueueIMC(final InterModEnqueueEvent event) {
	}

	private static void processIMC(final InterModProcessEvent event) {
	}

	private static void serverStarting(FMLServerStartingEvent event) {
	}

	public abstract World getClientWorld();

	public abstract PlayerEntity getClientPlayer();

	public static class Client extends SideProxy {

		public World getClientWorld() {
			return Minecraft.getInstance().world;
		}

		public PlayerEntity getClientPlayer() {
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

		public World getClientWorld() {
			throw new IllegalStateException("Only run on the client");
		}

		public PlayerEntity getClientPlayer() {
			throw new IllegalStateException("Only run on the client");
		}
	}

}
