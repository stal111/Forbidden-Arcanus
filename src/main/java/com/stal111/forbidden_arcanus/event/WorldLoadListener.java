package com.stal111.forbidden_arcanus.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModStructureFeatures;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;

import java.util.HashMap;
import java.util.Map;

/**
 * World Load Listener
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.event.WorldLoadListener
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-12
 */
@Mod.EventBusSubscriber
public class WorldLoadListener {

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {
		if (!(event.getWorld() instanceof ServerLevel level)) {
			return;
		}

		ChunkGenerator generator = level.getChunkSource().getGenerator();

		if (generator instanceof FlatLevelSource && level.dimension().equals(Level.OVERWORLD)) {
			return;
		}

		StructureSettings structureSettings = generator.getSettings();

		HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap = new HashMap<>();

		for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : level.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
			Biome biome = biomeEntry.getValue();
			Biome.BiomeCategory category = biome.getBiomeCategory();
			ResourceLocation name = biome.getRegistryName();

			if (name == null) {
				continue;
			}

			// Check Blacklist
			//TODO

			// Add Structures
			if (WorldGenConfig.NIPA_GENERATE.get()) {
				if (biome.getDownfall() != 0.0F && biome.getBaseTemperature() < 2.0F && category != Biome.BiomeCategory.BEACH && category != Biome.BiomeCategory.OCEAN && category != Biome.BiomeCategory.MUSHROOM) {
					associateBiomeToConfiguredStructure(structureToMultiMap, ModStructureFeatures.NIPA, biomeEntry.getKey());
				} else if (category != Biome.BiomeCategory.THEEND && category != Biome.BiomeCategory.NETHER) {
					associateBiomeToConfiguredStructure(structureToMultiMap, ModStructureFeatures.NIPA_ALWAYS_FLOATING, biomeEntry.getKey());
				}
			}

			ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
			structureSettings.configuredStructures.entrySet().stream().filter(entry -> !structureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

			structureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

			structureSettings.configuredStructures = tempStructureToMultiMap.build();

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureSettings.structureConfig());
			tempMap.putIfAbsent(ModStructures.NIPA.get(), StructureSettings.DEFAULTS.get(ModStructures.NIPA.get()));

			structureSettings.structureConfig = tempMap;
		}
	}

	private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> structureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
		structureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());

		HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = structureToMultiMap.get(configuredStructureFeature.feature);

		if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
			ForbiddenArcanus.LOGGER.error("""
							    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
							    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
							    The two conflicting ConfiguredStructures are: {}, {}
							    The biome that is attempting to be shared: {}
							""",
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
					biomeRegistryKey
			);
		} else {
			configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
		}
	}

	/**
	 * Function to modify supplier structure separation settings map based off of a provided IValhelsiaStructure and the provided dimension white & blacklists.
	 *
	 * @param modifiableTempMap The current temporary map. This map is modified in place.
	 * @param dimension         The `RegistryKey<World>` key for this world.
	 * @param struct            The instance of IValhelsiaStructure being considered.
	 * @param list              An instance of DimensionList which contains the whitelist and blacklist for this structure.
	 */
	private static void handleStructureBlocking(Map<StructureFeature<?>, StructureFeatureConfiguration> modifiableTempMap, ResourceKey<Level> dimension, IValhelsiaStructure struct, WorldGenConfig.DimensionList list) {
		if (list.allowed(dimension)) {
			modifiableTempMap.putIfAbsent(struct.getStructure(), StructureSettings.DEFAULTS.get(struct.getStructure()));
		} else {
			modifiableTempMap.remove(struct.getStructure());
		}
	}
}
