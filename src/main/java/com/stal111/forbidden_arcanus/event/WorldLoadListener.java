package com.stal111.forbidden_arcanus.event;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.world.ModStructures;
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

  @SubscribeEvent
  public static void onWorldLoad(WorldEvent.Load event) {
    if (event.getWorld() instanceof ServerWorld) {
      ServerWorld serverWorld = (ServerWorld) event.getWorld();

      if (serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator && serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
        return;
      }

      if (GETCODEC_METHOD == null) {
        Method codec = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
        MethodHandles.Lookup l = MethodHandles.lookup();
        try {
          GETCODEC_METHOD = l.unreflect(codec);
        } catch (IllegalAccessException e) {
          ForbiddenArcanus.LOGGER.error("Unable to unreflect codec getter.", e);
          return;
        }
      }

      ResourceLocation chunkGen = null;
      try {
        //noinspection unchecked
        chunkGen = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invokeExact(serverWorld.getChunkProvider().generator));
      } catch (Throwable throwable) {
        ForbiddenArcanus.LOGGER.error("Unable to look up chunk provider's generator", throwable);
        return;
      }
      if (chunkGen != null && chunkGen.getNamespace().equals("terraforrged")) {
        return;
      }

      Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
      for (IValhelsiaStructure structure : ModStructures.MOD_STRUCTURES) {
        if (serverWorld.getDimensionKey().equals(World.OVERWORLD)) {
          tempMap.putIfAbsent(structure.getStructure(), DimensionStructuresSettings.field_236191_b_.get(structure.getStructure()));
        } else {
          tempMap.remove(structure.getStructure());
        }
      }

      serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
    }
  }
}
