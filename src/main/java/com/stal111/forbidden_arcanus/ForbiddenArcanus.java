package com.stal111.forbidden_arcanus;

import com.stal111.forbidden_arcanus.client.ClientSetup;
import com.stal111.forbidden_arcanus.common.CommonSetup;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModEffects;
import com.stal111.forbidden_arcanus.core.init.ModEnchantments;
import com.stal111.forbidden_arcanus.core.init.ModRecipes;
import com.stal111.forbidden_arcanus.core.init.other.ModContainers;
import com.stal111.forbidden_arcanus.core.init.other.ModPOITypes;
import com.stal111.forbidden_arcanus.core.init.world.ModFeatures;
import com.stal111.forbidden_arcanus.core.init.world.ModFoliagePlacers;
import com.stal111.forbidden_arcanus.core.init.world.ModTreeDecorators;
import com.stal111.forbidden_arcanus.core.registry.ModRegistryCollector;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.valhelsia.valhelsia_core.api.common.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.ModDefinition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ForbiddenArcanus.MOD_ID)
public final class ForbiddenArcanus {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);


	public static final RegistryManager REGISTRY_MANAGER = new RegistryManager(new ModRegistryCollector(ForbiddenArcanus.MOD_ID));

	public ForbiddenArcanus() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		ModDefinition.of(ForbiddenArcanus.MOD_ID)
				.withRegistryManager(REGISTRY_MANAGER)
				.withEventHandler(new ModEventHandler(modEventBus))
				.clientSetup(ClientSetup::new)
				.create();

		ModBlockEntities.TILE_ENTITIES.register(modEventBus);
		ModEnchantments.ENCHANTMENTS.register(modEventBus);
		ModEffects.EFFECTS.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		ModRecipes.RECIPE_TYPES.register(modEventBus);
		ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
		ModContainers.CONTAINERS.register(modEventBus);
		ModPOITypes.POI_TYPES.register(modEventBus);

		ModFoliagePlacers.FOLIAGE_PLACERS.register(modEventBus);
		ModTreeDecorators.TREE_DECORATORS.register(modEventBus);

		modEventBus.addListener(CommonSetup::setup);
	}

//	@Override
//	protected void setup(final FMLCommonSetupEvent event) {
//		event.enqueueWork(() -> {
//			ModFlammables.registerFlammables();
//			ModDispenseBehaviors.registerDispenseBehaviors();
//		});
//
//		NetworkHandler.init();
//
//		CounterHelper.addCounter(CounterCreator.of(resourceLocation -> new SerializableCounter(resourceLocation, 0, false), new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer")));
//
//		Consequences.registerConsequences();
//	}
}
