package com.stal111.forbidden_arcanus.common.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.config.WorldGenConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

import java.util.Set;
import java.util.stream.Collectors;

public class DimensionConfig implements DecoratorConfiguration {
	public static final Codec<DimensionConfig> CODEC = RecordCodecBuilder.create((codec) -> codec.group(
					ResourceLocation.CODEC.listOf().fieldOf("whitelist").forGetter(o -> o.whitelist.stream().map(ResourceKey::location).collect(Collectors.toList())),
					ResourceLocation.CODEC.listOf().fieldOf("blacklist").forGetter(o -> o.blacklist.stream().map(ResourceKey::location).collect(Collectors.toList())
					))
			.apply(codec, (a, b) -> new DimensionConfig(a.stream().map(o -> ResourceKey.create(Registry.DIMENSION_REGISTRY, o)).collect(Collectors.toSet()), b.stream().map(o -> ResourceKey.create(Registry.DIMENSION_REGISTRY, o)).collect(Collectors.toSet()))));

	public final Set<ResourceKey<Level>> blacklist;
	public final Set<ResourceKey<Level>> whitelist;

	public DimensionConfig(WorldGenConfig.DimensionList list) {
		this.whitelist = list.getWhitelist();
		this.blacklist = list.getBlacklist();
	}

	protected DimensionConfig(Set<ResourceKey<Level>> whitelist, Set<ResourceKey<Level>> blacklist) {
		this.whitelist = whitelist;
		this.blacklist = blacklist;
	}
}