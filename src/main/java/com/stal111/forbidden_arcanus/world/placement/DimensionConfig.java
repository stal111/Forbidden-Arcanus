package com.stal111.forbidden_arcanus.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.placement.IPlacementConfig;

import java.util.Set;
import java.util.stream.Collectors;

public class DimensionConfig implements IPlacementConfig {
  public static final Codec<DimensionConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
      ResourceLocation.CODEC.listOf().fieldOf("whitelist").forGetter(o -> o.whitelist.stream().map(RegistryKey::getLocation).collect(Collectors.toList())),
      ResourceLocation.CODEC.listOf().fieldOf("blacklist").forGetter(o -> o.blacklist.stream().map(RegistryKey::getLocation).collect(Collectors.toList())
      ))
      .apply(codec, (a, b) -> new DimensionConfig(a.stream().map(o -> RegistryKey.getOrCreateKey(Registry.WORLD_KEY, o)).collect(Collectors.toSet()), b.stream().map(o -> RegistryKey.getOrCreateKey(Registry.WORLD_KEY, o)).collect(Collectors.toSet()))));

  public final Set<RegistryKey<World>> blacklist;
  public final Set<RegistryKey<World>> whitelist;

  public DimensionConfig(Set<RegistryKey<World>> whitelist, Set<RegistryKey<World>> blacklist) {
    this.whitelist = whitelist;
    this.blacklist = blacklist;
  }
}