package com.stal111.forbidden_arcanus.event;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.core.init.world.ModStructures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.valhelsia.valhelsia_core.common.world.IValhelsiaStructure;

import javax.annotation.Nullable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
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
	private static MethodHandle GETCODEC_METHOD = null;

	/**
	 * @return A boolean value to determine if the ChunkGenerator's getCodec method has bene properly unreflected into the MethodHandle. If false, `GETCODEC_METHOD` remains null.
	 */
	private static boolean getGetCodecMethod() {
		if (GETCODEC_METHOD == null) {
			// TODO: Beware that updating to mojmap using the standard gradle task *can and will* replace `codec` with the actual mojmap name (if one is found). This will not cause a crash in a development environment, but will crash normal, obfuscated clients.
			Method codec = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
			MethodHandles.Lookup l = MethodHandles.lookup();
			try {
				GETCODEC_METHOD = l.unreflect(codec);
			} catch (IllegalAccessException e) {
				ForbiddenArcanus.LOGGER.error("Unable to unreflect ChunkGenerator codec getter.", e);
				return false;
			}
		}

		return true;
	}

	/**
	 * A function to determine the ResourceLocation associated with the chunk generator. This is used in the WorldLoadEvent to determine if the chunk generator is Terraforged or not.
	 *
	 * @param serverWorld The current server world being loaded.
	 * @return A resource location if the GETCODEC_METHOD was properly loaded, or otherwise null, or if the lookup fails for whatever reason, also null.
	 */
	@Nullable
	private static ResourceLocation getChunkGenerator(ServerLevel serverWorld) {
		if (!getGetCodecMethod()) {
			return null;
		}

		ResourceLocation chunkGen;
		try {
			//noinspection unchecked
			chunkGen = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invokeExact(serverWorld.getChunkSource().generator));
		} catch (Throwable throwable) {
			ForbiddenArcanus.LOGGER.error("Unable to look up " + serverWorld + "'s chunk provider's generator resource location.", throwable);
			return null;
		}
		return chunkGen;
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerLevel) {
			ServerLevel serverWorld = (ServerLevel) event.getWorld();

			if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource && serverWorld.dimension().equals(Level.OVERWORLD)) {
				return;
			}

			// Shenanigans to make structures properly cope with Terraforged.
			// Using TG's accessor mixin sometimes fails, for whatever reason,
			// hence the choice to use the method handle.
			ResourceLocation chunkGen = getChunkGenerator(serverWorld);
			if (chunkGen != null && chunkGen.getNamespace().equals("terraforrged")) {
				return;
			}

			Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());

			// Converted to a specific structure-specific thing to allow for
			// broader application of structure whitelist/blacklists. Config
			// for whitelist/blacklists could potentially be merged into the
			// IValhelsiaStructure.
			handleStructureBlocking(tempMap, serverWorld.dimension(), ModStructures.NIPA.get(), WorldGenConfig.nipaList);

			serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
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
