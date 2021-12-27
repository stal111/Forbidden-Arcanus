package com.stal111.forbidden_arcanus.event;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;

import java.lang.reflect.Method;
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


	private static Method GETCODEC_METHOD = null;

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel serverLevel) {
			if (serverLevel.getChunkSource().getGenerator() instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			// Shenanigans to make structures properly cope with Terraforged.
			// Using TG's accessor mixin sometimes fails, for whatever reason,
			// hence the choice to use the method handle.
			try {
				if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
				ResourceLocation resourceLocation = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverLevel.getChunkSource().generator));
				if(resourceLocation != null && resourceLocation.getNamespace().equals("terraforged")) {
					return;
				}
			}
			catch(Exception e){
				ForbiddenArcanus.LOGGER.error("Was unable to check if " + serverLevel.dimension().location() + " is using Terraforged's ChunkGenerator.");
			}

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverLevel.getChunkSource().generator.getSettings().structureConfig());

			// Converted to a specific structure-specific thing to allow for
			// broader application of structure whitelist/blacklists. Config
			// for whitelist/blacklists could potentially be merged into the
			// IValhelsiaStructure.
			handleStructureBlocking(tempMap, serverLevel.dimension(), ModStructures.NIPA.get(), WorldGenConfig.nipaList);

			serverLevel.getChunkSource().generator.getSettings().structureConfig = tempMap;
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
