package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.aureal.capability.AurealImpl;
import com.stal111.forbidden_arcanus.aureal.capability.AurealStorage;
import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.block.CandelabraBlock;
import com.stal111.forbidden_arcanus.block.ModStandingSignBlock;
import com.stal111.forbidden_arcanus.block.ModWallSignBlock;
import com.stal111.forbidden_arcanus.block.tileentity.container.ModContainers;
import com.stal111.forbidden_arcanus.capability.eternalStellaActive.EternalStellaActiveCapability;
import com.stal111.forbidden_arcanus.capability.flightTimeLeft.FlightTimeLeftCapability;
import com.stal111.forbidden_arcanus.capability.item.timer.ITimer;
import com.stal111.forbidden_arcanus.capability.item.timer.TimerImpl;
import com.stal111.forbidden_arcanus.capability.item.timer.TimerStorage;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.EntitySpawningBlockingCapability;
import com.stal111.forbidden_arcanus.config.Config;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import com.stal111.forbidden_arcanus.event.LootTableListener;
import com.stal111.forbidden_arcanus.gui.forbiddenmicon.ForbiddenmiconPageLoadListener;
import com.stal111.forbidden_arcanus.init.*;
import com.stal111.forbidden_arcanus.init.other.ModFlammables;
import com.stal111.forbidden_arcanus.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.init.world.ModStructures;
import com.stal111.forbidden_arcanus.item.ModItemGroup;
import com.stal111.forbidden_arcanus.item.block.WallFloorOrCeilingItem;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.proxy.ClientProxy;
import com.stal111.forbidden_arcanus.proxy.IProxy;
import com.stal111.forbidden_arcanus.proxy.ServerProxy;
import com.stal111.forbidden_arcanus.recipe.AwkwardPotionBrewingRecipe;
import com.stal111.forbidden_arcanus.setup.ClientSetup;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import com.stal111.forbidden_arcanus.util.Data;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.ModWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.registry.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForbiddenArcanus.MOD_ID)
public class ForbiddenArcanus {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);
	public static final ItemGroup FORBIDDEN_ARCANUS = new ModItemGroup(ForbiddenArcanus.MOD_ID);

	public static final Block EDELWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.EDELWOOD).setRegistryName(ModUtils.location("edelwood_sign"));
	public static final Block EDELWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(EDELWOOD_SIGN), ModWoodType.EDELWOOD).setRegistryName(ModUtils.location("edelwood_wall_sign"));
	public static final Block CHERRYWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.CHERRYWOOD).setRegistryName(ModUtils.location("cherrywood_sign"));
	public static final Block CHERRYWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(CHERRYWOOD_SIGN), ModWoodType.CHERRYWOOD).setRegistryName(ModUtils.location("cherrywood_wall_sign"));
	public static final Block MYSTERYWOOD_SIGN = new ModStandingSignBlock(Block.Properties.from(Blocks.OAK_SIGN), ModWoodType.MYSTERYWOOD).setRegistryName(ModUtils.location("mysterywood_sign"));
	public static final Block MYSTERYWOOD_WALL_SIGN = new ModWallSignBlock(Block.Properties.from(Blocks.OAK_WALL_SIGN).lootFrom(MYSTERYWOOD_SIGN), ModWoodType.MYSTERYWOOD).setRegistryName(ModUtils.location("mysterywood_wall_sign"));

	public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
	private static final Data DATA = new Data();

	public static final ForbiddenmiconPageLoadListener PAGE_LOADER = new ForbiddenmiconPageLoadListener();

	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager.Builder(MOD_ID).addDefaultHelpers().build();

	public static ForbiddenArcanus instance;

	public ForbiddenArcanus() {
		instance = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);

		ModEntities.ENTITY_TYPES.register(modEventBus);
		NewModBlocks.BLOCKS.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModTileEntities.TILE_ENTITIES.register(modEventBus);
		ModParticles.PARTICLE_TYPES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		ModStructures.STRUCTURES.register(modEventBus);

		modEventBus.addGenericListener(GlobalLootModifierSerializer.class, LootTableListener::registerGlobalModifiers);

		Data.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		DATA.subscribeEvents(FMLJavaModLoadingContext.get().getModEventBus());

		REGISTRY_MANAGER.getBlockHelper().setDefaultGroup(FORBIDDEN_ARCANUS);

		REGISTRY_MANAGER.register(modEventBus);

		modEventBus.addListener(this::setup);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        
        Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-client.toml").toString());
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-common.toml").toString());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		DeferredWorkQueue.runLater(() -> {
			ModFlammables.registerFlammables();
		});

		proxy.init();

		NetworkHandler.init();

		ModWoodType.register(ModWoodType.EDELWOOD);
		ModWoodType.register(ModWoodType.CHERRYWOOD);
		ModWoodType.register(ModWoodType.MYSTERYWOOD);

		ModUtils.addStrippable(ModBlocks.CHERRYWOOD_LOG.getBlock(), ModBlocks.STRIPPED_CHERRYWOOD_LOG.getBlock());
		ModUtils.addStrippable(ModBlocks.CHERRYWOOD.getBlock(), ModBlocks.STRIPPED_CHERRYWOOD.getBlock());
		ModUtils.addStrippable(ModBlocks.MYSTERYWOOD_LOG.getBlock(), ModBlocks.STRIPPED_MYSTERYWOOD_LOG.getBlock());
		ModUtils.addStrippable(ModBlocks.MYSTERYWOOD.getBlock(), ModBlocks.STRIPPED_MYSTERYWOOD.getBlock());

		BrewingRecipeRegistry.addRecipe(new AwkwardPotionBrewingRecipe());

		FlightTimeLeftCapability.register();
		EternalStellaActiveCapability.register();
		EntitySpawningBlockingCapability.register();

		CapabilityManager.INSTANCE.register(IAureal.class, new AurealStorage(), AurealImpl::new);
		CapabilityManager.INSTANCE.register(ITimer.class, new TimerStorage(), TimerImpl::new);

		Consequences.registerConsequences();

		GlobalEntityTypeAttributes.put((EntityType<? extends LivingEntity>) ModEntities.PIXIE.get(), PixieEntity.registerAttributes().create());
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
	}
}
