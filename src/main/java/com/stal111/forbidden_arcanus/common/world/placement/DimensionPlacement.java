package com.stal111.forbidden_arcanus.common.world.placement;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;
import net.minecraft.server.level.ServerLevel;

import java.util.Random;
import java.util.stream.Stream;

public class DimensionPlacement extends FeatureDecorator<DimensionConfig> {
	public DimensionPlacement(Codec<DimensionConfig> codec) {
		super(codec);
	}

	public static DimensionPlacement create() {
		return new DimensionPlacement(DimensionConfig.CODEC);
	}

	@Override
	public Stream<BlockPos> getPositions(DecorationContext helper, Random rand, DimensionConfig config, BlockPos pos) {
		ServerLevel world = helper.level.getLevel();
		ResourceKey<Level> key = world.dimension();
		if (config.whitelist.contains(key) || config.whitelist.isEmpty() || !config.blacklist.contains(key)) {
			return Stream.of(pos);
		} else {
			return Stream.empty();
		}
	}
}
