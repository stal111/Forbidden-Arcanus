package com.stal111.forbidden_arcanus;


import com.stal111.forbidden_arcanus.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.common.container.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.config.Config;
import com.stal111.forbidden_arcanus.init.*;
import com.stal111.forbidden_arcanus.init.other.*;
import com.stal111.forbidden_arcanus.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.init.world.ModStructures;
import com.stal111.forbidden_arcanus.common.item.group.ModItemGroup;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.proxy.ClientProxy;
import com.stal111.forbidden_arcanus.proxy.IProxy;
import com.stal111.forbidden_arcanus.proxy.ServerProxy;
import com.stal111.forbidden_arcanus.recipe.AwkwardPotionBrewingRecipe;
import com.stal111.forbidden_arcanus.sound.ModSounds;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.common.helper.CounterHelper;
import net.valhelsia.valhelsia_core.core.registry.LootModifierRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod(ForbiddenArcanus.MOD_ID)
public class ForbiddenArcanus {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);
	public static final CreativeModeTab FORBIDDEN_ARCANUS = new ModItemGroup(ForbiddenArcanus.MOD_ID);

	public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager.Builder(MOD_ID).addDefaultHelpers().addHelpers(new LootModifierRegistryHelper()).build();

	public static final Supplier<IForgeRegistry<ItemModifier>> ITEM_MODIFIER_REGISTRY = ModItemModifiers.MODIFIERS.makeRegistry("item_modifiers", () ->
			new RegistryBuilder<ItemModifier>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, old) -> {}
			).setDefaultKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "null")));

	public static ForbiddenArcanus instance;

	public ForbiddenArcanus() {
		instance = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);

		ModEntities.ENTITY_TYPES.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModBlockEntities.TILE_ENTITIES.register(modEventBus);
		ModParticles.PARTICLE_TYPES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		ModFeatures.PLACEMENTS.register(modEventBus);
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		ModStructures.STRUCTURES.register(modEventBus);
		ModContainers.CONTAINERS.register(modEventBus);
		ModPOITypes.POI_TYPES.register(modEventBus);
		ModItemModifiers.MODIFIERS.register(modEventBus);

		REGISTRY_MANAGER.getBlockHelper().setDefaultGroup(FORBIDDEN_ARCANUS);

		REGISTRY_MANAGER.register(modEventBus);

		modEventBus.addListener(this::setup);
		modEventBus.addListener(CommonSetup::setup);

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);

		Config.loadConfig(Config.CLIENT_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-client.toml").toString());
		Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(ForbiddenArcanus.MOD_ID + "-common.toml").toString());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModFlammables.registerFlammables();
			ModDispenseBehaviors.registerDispenseBehaviours();
			ModConfiguredFeatures.load();
			ModStructures.setupStructures();
		});

		proxy.init();

		NetworkHandler.init();

		BrewingRecipeRegistry.addRecipe(new AwkwardPotionBrewingRecipe());

		CapabilityManager.INSTANCE.register(IAureal.class);

		CounterHelper.addCounter(new SimpleCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"), 0, false));

		Consequences.registerConsequences();
		HephaestusForgeInputs.registerInputs();
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
				}
			}
		}

		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			ModSounds.register(event);
		}
	}
}
