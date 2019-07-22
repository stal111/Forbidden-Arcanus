package com.stal111.forbidden_arcanus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.stal111.forbidden_arcanus.block.ModBlocks;
import com.stal111.forbidden_arcanus.block.tileentity.ModTileEntities;
import com.stal111.forbidden_arcanus.block.tileentity.container.ModContainers;
import com.stal111.forbidden_arcanus.config.Config;
import com.stal111.forbidden_arcanus.entity.ModEntities;
import com.stal111.forbidden_arcanus.item.ModItemGroup;
import com.stal111.forbidden_arcanus.item.ModItems;
import com.stal111.forbidden_arcanus.proxy.SideProxy;
import com.stal111.forbidden_arcanus.sound.ModSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Main {

	public static final String MOD_ID = "forbidden_arcanus";

	public static final Logger LOGGER = LogManager.getLogger(Main.MOD_ID);

	public static final ItemGroup FORBIDDEN_ARCANUS = new ModItemGroup(Main.MOD_ID);

	public static Main instance;

	public Main() {
		DistExecutor.runForDist(() -> () -> new SideProxy.Client(), () -> () -> new SideProxy.Server());
		instance = this;
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Main.MOD_ID + "-client.toml"));
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Main.MOD_ID + "-server.toml"));
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		ModBlocks.register(event);

	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		ModItems.register(event);
	}

	@SubscribeEvent
	public static void registerTileEntities(RegistryEvent.Register<TileEntityType<?>> event) {
		ModTileEntities.registerTileEntities(event);
	}
	
	@SubscribeEvent
	public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
		ModContainers.register(event);
	}

//	@SubscribeEvent
//	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
//		ModEntities.register(event);
//	}
//	
//	@SubscribeEvent
//	public static void registerPotions(RegistryEvent.Register<Potion> event) {
//		ModPotions.register(event);
//	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		ModSounds.register(event);
	}

}
