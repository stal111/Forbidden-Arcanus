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
import com.stal111.forbidden_arcanus.core.init.world.*;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModCavePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModOrePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModTreePlacements;
import com.stal111.forbidden_arcanus.data.worldgen.placement.ModVegetationPlacements;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.valhelsia.valhelsia_core.common.capability.counter.CounterCreator;
import net.valhelsia.valhelsia_core.common.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.common.helper.CounterHelper;
import net.valhelsia.valhelsia_core.core.ValhelsiaMod;
import net.valhelsia.valhelsia_core.core.registry.RegistryManager;
import net.valhelsia.valhelsia_core.core.registry.helper.EntityRegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.RegistryHelper;
import net.valhelsia.valhelsia_core.core.registry.helper.block.BlockRegistryHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

@Mod(ForbiddenArcanus.MOD_ID)
public final class ForbiddenArcanus extends ValhelsiaMod {

	public static final String MOD_ID = "forbidden_arcanus";
	public static final Logger LOGGER = LogManager.getLogger(ForbiddenArcanus.MOD_ID);
	public static final CreativeModeTab FORBIDDEN_ARCANUS = new ModItemGroup(ForbiddenArcanus.MOD_ID);

	public static final RegistryManager REGISTRY_MANAGER = RegistryManager.builder(MOD_ID)
			.addHelper(ForgeRegistries.Keys.BLOCKS, new BlockRegistryHelper(FORBIDDEN_ARCANUS, ModBlocks::new))
			.addHelper(ForgeRegistries.Keys.ITEMS, new RegistryHelper<>(ModItems::new))
			.addHelper(ForgeRegistries.Keys.ENTITY_TYPES, new EntityRegistryHelper(ModEntities::new))
			.addHelper(Registry.STRUCTURE_TYPE_REGISTRY, new RegistryHelper<>(ModStructureTypes::new))
			.addHelper(Registry.STRUCTURE_REGISTRY, new RegistryHelper<>(ModStructures::new))
			.addHelper(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, new RegistryHelper<>(ModLootModifiers::new))
			.addHelper(Registry.STRUCTURE_PIECE_REGISTRY, new RegistryHelper<>(ModStructurePieces::new))
			.addHelper(Registry.STRUCTURE_SET_REGISTRY, new RegistryHelper<>(ModStructureSets::new))
			.addHelper(ForgeRegistries.Keys.SOUND_EVENTS, new RegistryHelper<>(ModSounds::new))
			.addHelper(Registry.CONFIGURED_FEATURE_REGISTRY, new RegistryHelper<>(ModConfiguredFeatures::new))
			.addHelper(Registry.PLACED_FEATURE_REGISTRY, new RegistryHelper<>(ModOrePlacements::new, ModTreePlacements::new, ModVegetationPlacements::new, ModCavePlacements::new))
			.addHelper(ForgeRegistries.Keys.PARTICLE_TYPES, new RegistryHelper<>(ModParticles::new))
			.addHelper(ForgeRegistries.Keys.MEMORY_MODULE_TYPES, new RegistryHelper<>(ModMemoryModules::new))
			.addHelper(ForgeRegistries.Keys.ACTIVITIES, new RegistryHelper<>(ModActivities::new))
			.create();

	public static final Supplier<IForgeRegistry<ItemModifier>> ITEM_MODIFIER_REGISTRY = ModItemModifiers.MODIFIERS.makeRegistry(() ->
			new RegistryBuilder<ItemModifier>().setMaxID(Integer.MAX_VALUE - 1).onAdd((owner, stage, id, key, obj, old) -> {}
			).setDefaultKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "null")));

	public static ForbiddenArcanus instance;

	public ForbiddenArcanus() {
		super(MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
		instance = this;

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
		ModItemModifiers.MODIFIERS.register(modEventBus);
		ModFoliagePlacers.FOLIAGE_PLACERS.register(modEventBus);
		ModTreeDecorators.TREE_DECORATORS.register(modEventBus);

		modEventBus.addListener(CommonSetup::setup);
	}

	@Override
	public RegistryManager getRegistryManager() {
		return REGISTRY_MANAGER;
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

		CounterHelper.addCounter(CounterCreator.of(resourceLocation -> new SimpleCounter(resourceLocation, 0, false), new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer")));

		Consequences.registerConsequences();
		HephaestusForgeInputs.registerInputs();
	}
}
