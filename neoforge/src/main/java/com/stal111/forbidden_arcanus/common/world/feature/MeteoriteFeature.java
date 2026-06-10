package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.feature.config.MeteoriteConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class MeteoriteFeature extends Feature<MeteoriteConfiguration> {

    public MeteoriteFeature(Codec<MeteoriteConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeteoriteConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos rawOrigin = context.origin();
        RandomSource random = context.random();
        MeteoriteConfiguration config = context.config();

        int groundY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, rawOrigin.getX(), rawOrigin.getZ());
        BlockPos origin = new BlockPos(rawOrigin.getX(), groundY, rawOrigin.getZ());

        int craterRadius = config.craterRadius();
        int craterRadiusSq = craterRadius * craterRadius;

        int radius = config.radius();
        int radiusSq = radius * radius;

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        int centerCraterDepth = 0;
        if (craterRadius > 0) {
            centerCraterDepth = (int) (craterRadius * 0.8);
            centerCraterDepth += random.nextInt(3) - 1;
            if (centerCraterDepth < 0) {
                centerCraterDepth = 0;
            }
        }

        int meteorYOffset = Mth.ceil(centerCraterDepth * 0.5);

        // Crater
        int magmaRadius = radius + config.magmaRadiusOffset();

        if (craterRadius > 0) {
            int craterCenter = origin.getY();

            for (int dx = -craterRadius; dx <= craterRadius; dx++) {
                for (int dz = -craterRadius; dz <= craterRadius; dz++) {
                    int distSq = dx * dx + dz * dz;

                    if (distSq > craterRadiusSq) {
                        continue;
                    }

                    double distFromCenter = Math.sqrt(distSq);
                    double normalizedDist = distFromCenter / craterRadius;

                    int craterDepth = (int) (craterRadius * 0.6 * (1.0 - normalizedDist * normalizedDist));

                    craterDepth += random.nextInt(2) - 1;

                    if (normalizedDist <= 1.0 && craterDepth > 0) {
                        int bottomY = craterCenter - craterDepth;
                        int topY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, origin.getX() + dx, origin.getZ() + dz);

                        boolean isUnderwater = false;

                        for (int y = topY; y >= bottomY; y--) {
                            mutable.set(origin.getX() + dx, y, origin.getZ() + dz);
                            BlockState currentState = level.getBlockState(mutable);

                            if (!currentState.getFluidState().isEmpty()) {
                                isUnderwater = true;
                                continue;
                            }

                            if (!currentState.isAir()) {
                                BlockState stateToSet = isUnderwater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                                level.setBlock(mutable, stateToSet, 2);
                            }
                        }

                        double normalizedMagmaDist = distFromCenter / magmaRadius;

                        if (normalizedMagmaDist <= 1.0) {
                            double probability = 1.0 - (normalizedMagmaDist * normalizedMagmaDist);

                            if (random.nextFloat() < probability * config.magmaChance()) {
                                mutable.set(origin.getX() + dx, bottomY - 1, origin.getZ() + dz);
                                level.setBlock(mutable, Blocks.MAGMA_BLOCK.defaultBlockState(), 2);

                                if (!isUnderwater && random.nextFloat() < config.fireChance()) {
                                    mutable.set(origin.getX() + dx, bottomY, origin.getZ() + dz);
                                    level.setBlock(mutable, Blocks.FIRE.defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Meteor
        int meteorOriginY = origin.getY() - meteorYOffset;
        int innerRadiusSq = (radius - 2) * (radius - 2);

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    int distSq = dx * dx + dy * dy + dz * dz;

                    if (distSq > radiusSq) {
                        continue;
                    }

                    if (random.nextFloat() < config.coreRoughness() && distSq > innerRadiusSq) {
                        continue;
                    }

                    mutable.set(origin.getX() + dx, meteorOriginY + dy, origin.getZ() + dz);

                    level.setBlock(mutable, config.blockStateProvider().getState(level, random, mutable), 2);
                }
            }
        }

        return true;
    }
}
