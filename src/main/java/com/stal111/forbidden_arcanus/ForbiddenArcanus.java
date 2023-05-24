package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.common.network.NetworkHandler;
import com.stal111.forbidden_arcanus.core.config.Config;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModEffects;
import com.stal111.forbidden_arcanus.core.init.ModEnchantments;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import com.stal111.forbidden_arcanus.core.init.other.ModContainers;
import com.stal111.forbidden_arcanus.core.init.other.ModDispenseBehaviors;
import com.stal111.forbidden_arcanus.core.init.other.ModFlammables;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.core.init.world.ModFoliagePlacers;
import com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators;
import com.stal111.forbidden_arcanus.core.registry.ModRegistryCollector;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator;
import net.valhelsia.valhelsia_core.common.helper.CounterHelper;
import net.valhelsia.valhelsia_core.common.util.counter.SerializableCounter;
import net.valhelsia.valhelsia_core.core.ValhelsiaMod;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForbiddenArcanus.MOD_ID)
public final class ForbiddenArcanus extends ValhelsiaMod {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);


	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModRegistryCollector(ForbiddenArcanus.MOD_ID), null);

	public static ForbiddenArcanus INSTANCE;

	public ForbiddenArcanus() {
		super(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus(), ForbiddenArcanus.REGISTRY_MANAGER);
		INSTANCE = this;

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientSetup::new);

		ModBlockEntities.TILE_ENTITIES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		//ModFeatures.PLACEMENTS.register(modEventBus);
		ModRecipes.RECIPE_TYPES.register(modEventBus);
		ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
		ModContainers.CONTAINERS.register(modEventBus);
		ModPOITypes.POI_TYPES.register(modEventBus);

		ModFoliagePlacers.FOLIAGE_PLACERS.register(modEventBus);
		ModTreeDecorators.TREE_DECORATORS.register(modEventBus);

		modEventBus.addListener(CommonSetup::setup);
	}

	@Override
	public EventHandler buildEventHandler() {
		return new ModEventHandler();
	}

	@Override
	protected void registerConfigs() {
		this.registerClientConfig(Config.CLIENT_CONFIG);
		this.registerCommonConfig(Config.COMMON_CONFIG);
	}

	@Override
	protected void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModFlammables.registerFlammables();
			ModDispenseBehaviors.registerDispenseBehaviors();
		});

		NetworkHandler.init();

		CounterHelper.addCounter(CounterCreator.of(resourceLocation -> new SerializableCounter(resourceLocation, 0, false), new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer")));

		Consequences.registerConsequences();
	}
}
