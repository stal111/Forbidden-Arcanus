package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.block.CandelabraBlock;
import com.stal111.forbidden_arcanus.block.ModStandingSignBlock;
import com.stal111.forbidden_arcanus.block.ModWallSignBlock;
import com.stal111.forbidden_arcanus.block.tileentity.container.ModContainers;
import com.stal111.forbidden_arcanus.capability.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.config.Config;
import com.stal111.forbidden_arcanus.event.LootTableListener;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconPageLoadListener;
import com.stal111.forbidden_arcanus.init.*;
import com.stal111.forbidden_arcanus.item.ModItemGroup;
import com.stal111.forbidden_arcanus.item.block.WallFloorOrCeilingItem;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.proxy.ClientProxy;
import com.stal111.forbidden_arcanus.proxy.IProxy;
import com.stal111.forbidden_arcanus.proxy.ServerProxy;
import com.stal111.forbidden_arcanus.recipe.AwkwardPotionBrewingRecipe;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.ModWoodType;
import com.stal111.forbidden_arcanus.world.gen.OreGenerator;
import com.stal111.forbidden_arcanus.world.gen.WorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MOD_ID)
public class Main {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(Main.MOD_ID);
	public static final ItemGroup FORBIDDEN_ARCANUS = new ModItemGroup(Main.MOD_ID);

	public static final Block EDELWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.EDELWOOD).setRegistryName(ModUtils.location("edelwood_sign"));
	public static final Block EDELWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(EDELWOOD_SIGN), ModWoodType.EDELWOOD).setRegistryName(ModUtils.location("edelwood_wall_sign"));
	public static final Block CHERRYWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.CHERRYWOOD).setRegistryName(ModUtils.location("cherrywood_sign"));
	public static final Block CHERRYWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(CHERRYWOOD_SIGN), ModWoodType.CHERRYWOOD).setRegistryName(ModUtils.location("cherrywood_wall_sign"));
	public static final Block MYSTERYWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.MYSTERYWOOD).setRegistryName(ModUtils.location("mysterywood_sign"));
	public static final Block MYSTERYWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(MYSTERYWOOD_SIGN), ModWoodType.MYSTERYWOOD).setRegistryName(ModUtils.location("mysterywood_wall_sign"));

	public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	private static final Data DATA = new Data();

	public static final ForbiddenmiconPageLoadListener PAGE_LOADER = new ForbiddenmiconPageLoadListener();

	public static Main instance;

	public Main() {
		instance = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModEntities.ENTITY_TYPES.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModTileEntities.TILE_ENTITIES.register(modEventBus);
		ModParticles.PARTICLE_TYPES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);

		modEventBus.addGenericListener(GlobalLootModifierSerializer.class, LootTableListener::registerGlobalModifiers);

		Data.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		DATA.subscribeEvents(FMLJavaModLoadingContext.get().getModEventBus());

		modEventBus.addListener(this::setup);
		modEventBus.addListener(this::serverAboutToStart);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
        
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Main.MOD_ID + "-client.toml").toString());
        Config.loadConfig(Config.SERVER_CONFIG, FMLPaths.CONFIGDIR.get().resolve(Main.MOD_ID + "-server.toml").toString());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		proxy.init();

		NetworkHandler.registerMessages();

		ModWoodType.register(ModWoodType.EDELWOOD);
		ModWoodType.register(ModWoodType.CHERRYWOOD);
		ModWoodType.register(ModWoodType.MYSTERYWOOD);

		OreGenerator.setupOreGen();
		WorldGenerator.setupWorldGen();
		ModUtils.addStrippable(ModBlocks.CHERRYWOOD_LOG.getBlock(), ModBlocks.STRIPPED_CHERRYWOOD_LOG.getBlock());
		ModUtils.addStrippable(ModBlocks.CHERRYWOOD.getBlock(), ModBlocks.STRIPPED_CHERRYWOOD.getBlock());
		ModUtils.addStrippable(ModBlocks.MYSTERYWOOD_LOG.getBlock(), ModBlocks.STRIPPED_MYSTERYWOOD_LOG.getBlock());
		ModUtils.addStrippable(ModBlocks.MYSTERYWOOD.getBlock(), ModBlocks.STRIPPED_MYSTERYWOOD.getBlock());

		BrewingRecipeRegistry.addRecipe(new AwkwardPotionBrewingRecipe());

		FlightTimeLeftCapability.register();
	}

	@SubscribeEvent
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		event.getServer().resourceManager.addReloadListener(PAGE_LOADER);
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			for (ModBlocks block : ModBlocks.values()) {
				event.getRegistry().register(block.getBlock());
			}
		}

		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			for (ModBlocks block : ModBlocks.values()) {
				if (block.hasItem()) {
					if (block.hasSpecialItem()) {
						event.getRegistry().register(block.getItem());
					} else {
						BlockItem item = new BlockItem(block.getBlock(), ModItems.properties());
						item.setRegistryName(ModUtils.location(block.getName()));
						event.getRegistry().register(item);
					}
				} else if (block.getBlock() instanceof CandelabraBlock) {
					BlockItem item = new WallFloorOrCeilingItem(block.getBlock(), ForgeRegistries.BLOCKS.getValue(ModUtils.location("wall_" + block.getBlock().getRegistryName().getPath())), ForgeRegistries.BLOCKS.getValue(ModUtils.location("hanging_" + block.getBlock().getRegistryName().getPath())), ModItems.properties());
					item.setRegistryName(ModUtils.location(block.getName()));
					event.getRegistry().register(item);
				}
			}
		}

		@SubscribeEvent
		public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
			ModContainers.register(event);
		}

//
//	@SubscribeEvent
//	public static void registerPotions(RegistryEvent.Register<Potion> event) {
//		ModPotions.register(event);
//	}

		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			ModSounds.register(event);
		}

		@SubscribeEvent
		public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
			for (ModFeatures feature : ModFeatures.values()) {
				event.getRegistry().register(feature.getFeature());
			}
		}

		@SubscribeEvent
		public static void registerRecipeSerializers(RegistryEvent.Register<IRecipeSerializer<?>> event) {
			for (ModRecipeSerializers recipe : ModRecipeSerializers.values()) {
				event.getRegistry().register(recipe.getRecipe());
			}
		}
	}
}
