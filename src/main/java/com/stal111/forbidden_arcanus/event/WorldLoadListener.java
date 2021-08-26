package com.stal111.forbidden_arcanus.event;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.config.WorldGenConfig;
import com.stal111.forbidden_arcanus.init.world.ModStructures;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.valhelsia.valhelsia_core.world.IValhelsiaStructure;

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
			// TODO: Beware that updating to mojmap using the standard gradle task *can and will* replace `func_230347_a_` with the actual mojmap name (if one is found). This will not cause a crash in a development environment, but will crash normal, obfuscated clients.
			Method codec = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
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
	private static ResourceLocation getChunkGenerator(ServerWorld serverWorld) {
		if (!getGetCodecMethod()) {
			return null;
		}

		ResourceLocation chunkGen;
		try {
			//noinspection unchecked
			chunkGen = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invokeExact(serverWorld.getChunkProvider().generator));
		} catch (Throwable throwable) {
			ForbiddenArcanus.LOGGER.error("Unable to look up " + serverWorld + "'s chunk provider's generator resource location.", throwable);
			return null;
		}
		return chunkGen;
	}

	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load event) {
		if (event.getWorld() instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) event.getWorld();

			if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
				return;
			}

			// Shenanigans to make structures properly cope with Terraforged.
			// Using TG's accessor mixin sometimes fails, for whatever reason,
			// hence the choice to use the method handle.
			ResourceLocation chunkGen = getChunkGenerator(serverWorld);
			if (chunkGen != null && chunkGen.getNamespace().equals("terraforrged")) {
				return;
			}

			Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());

			// Converted to a specific structure-specific thing to allow for
			// broader application of structure whitelist/blacklists. Config
			// for whitelist/blacklists could potentially be merged into the
			// IValhelsiaStructure.
			handleStructureBlocking(tempMap, serverWorld.getDimensionKey(), ModStructures.NIPA.get(), WorldGenConfig.nipaList);

			serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
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
	private static void handleStructureBlocking(Map<Structure<?>, StructureSeparationSettings> modifiableTempMap, RegistryKey<World> dimension, IValhelsiaStructure struct, WorldGenConfig.DimensionList list) {
		if (list.allowed(dimension)) {
			modifiableTempMap.putIfAbsent(struct.getStructure(), DimensionStructuresSettings.field_236191_b_.get(struct.getStructure()));
		} else {
			modifiableTempMap.remove(struct.getStructure());
		}
	}
}
