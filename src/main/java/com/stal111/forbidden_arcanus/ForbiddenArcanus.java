package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.common.inventory.input.HephaestusForgeInputs;
import com.stal111.forbidden_arcanus.common.item.group.ModItemGroup;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.core.config.Config;
import com.stal111.forbidden_arcanus.core.init.*;
import com.stal111.forbidden_arcanus.core.init.other.*;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
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
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator;
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

	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager.Builder(MOD_ID).addDefaultHelpers().addHelpers(new LootModifierRegistryHelper()).build();

	public static final Supplier<IForgeRegistry<ItemModifier>> ITEM_MODIFIER_REGISTRY = ModItemModifiers.MODIFIERS.makeRegistry(ItemModifier.class, () ->
			new RegistryBuilder<ItemModifier>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, obj, old) -> {}
			).setDefaultKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "null")));

	public static ForbiddenArcanus instance;

	public ForbiddenArcanus() {
		instance = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);

		ModEntities.ENTITY_TYPES.register(modEventBus);
		ModBlockEntities.TILE_ENTITIES.register(modEventBus);
		ModParticles.PARTICLE_TYPES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		//ModFeatures.PLACEMENTS.register(modEventBus);
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
			ModDispenseBehaviors.registerDispenseBehaviors();
			ModConfiguredFeatures.load();
		});

		NetworkHandler.init();

		CounterHelper.addCounter(CounterCreator.of(resourceLocation -> new SimpleCounter(resourceLocation, 0, false), new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer")));

		Consequences.registerConsequences();
		HephaestusForgeInputs.registerInputs();
	}

	@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {

		@SubscribeEvent
		public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
			ModSounds.register(event);
		}
	}
}
