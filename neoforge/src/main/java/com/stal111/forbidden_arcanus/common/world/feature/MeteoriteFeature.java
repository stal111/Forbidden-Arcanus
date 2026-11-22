package com.stal111.forbidden_arcanus.common.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.world.feature.config.MeteoriteConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.function.Predicate;

public class MeteoriteFeature extends Feature<MeteoriteConfiguration> {

    private static final double CRATER_DEPTH_RATIO = 0.6;

    public MeteoriteFeature(Codec<MeteoriteConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<MeteoriteConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos rawOrigin = context.origin();
        RandomSource random = context.random();
        MeteoriteConfiguration config = context.config();

        int craterRadius = config.craterRadius();
        int groundY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, rawOrigin.getX(), rawOrigin.getZ());

        if (groundY <= level.getMinY() + craterRadius + 4) {
            return false;
        }

        BlockPos origin = new BlockPos(rawOrigin.getX(), groundY, rawOrigin.getZ());
        Predicate<BlockState> replaceable = Feature.isReplaceable(BlockTags.FEATURES_CANNOT_REPLACE);

        int centerCraterDepth = 0;
        if (craterRadius > 0) {
            centerCraterDepth = Math.max(0, (int) (craterRadius * CRATER_DEPTH_RATIO) + random.nextInt(3) - 1);
            this.carveCrater(level, origin, random, config, replaceable);
        }

        int meteorYOffset = Mth.ceil(centerCraterDepth * 0.5);
        this.placeMeteoriteCore(level, origin.getX(), origin.getY() - meteorYOffset, origin.getZ(), random, config, replaceable);

        return true;
    }

    private void carveCrater(
            WorldGenLevel level,
            BlockPos origin,
            RandomSource random,
            MeteoriteConfiguration config,
            Predicate<BlockState> replaceable
    ) {
        int craterRadius = config.craterRadius();
        int craterRadiusSq = craterRadius * craterRadius;
        int magmaRadius = config.radius() + config.magmaRadiusOffset();
        int craterCenter = origin.getY();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dx = -craterRadius; dx <= craterRadius; dx++) {
            for (int dz = -craterRadius; dz <= craterRadius; dz++) {
                int distSq = dx * dx + dz * dz;
                if (distSq > craterRadiusSq) {
                    continue;
                }

                double distFromCenter = Math.sqrt(distSq);
                double normalizedDist = distFromCenter / craterRadius;
                int craterDepth = (int) (craterRadius * CRATER_DEPTH_RATIO * (1.0 - normalizedDist * normalizedDist));
                craterDepth += random.nextInt(2) - 1;

                if (craterDepth <= 0) {
                    continue;
                }

                int x = origin.getX() + dx;
                int z = origin.getZ() + dz;
                int columnGroundY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z);
                int clampedColumnY = Mth.clamp(columnGroundY, craterCenter - craterRadius, craterCenter + craterRadius);
                int topY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);

                // Blend from center Y to clamped local surface Y toward the rim to avoid cliff-like cuts on slopes
                double heightBlend = normalizedDist * normalizedDist;
                int referenceY = (int) Math.round(Mth.lerp(heightBlend, craterCenter, clampedColumnY));
                int bottomY = Math.max(level.getMinY() + 1, referenceY - craterDepth);

                if (topY < bottomY) {
                    continue;
                }

                boolean isUnderwater = false;
                for (int y = topY; y >= bottomY; y--) {
                    mutable.set(x, y, z);
                    BlockState currentState = level.getBlockState(mutable);

                    if (currentState.getFluidState().is(FluidTags.WATER)) {
                        isUnderwater = true;

                        if (!currentState.is(Blocks.WATER)) {
                            this.safeSetBlock(level, mutable, Blocks.WATER.defaultBlockState(), replaceable);
                        }
                    } else if (!currentState.isAir()) {
                        BlockState stateToSet = isUnderwater ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();

                        this.safeSetBlock(level, mutable, stateToSet, replaceable);
                    }
                }

                this.decorateCraterFloor(level, mutable, x, bottomY, z, distFromCenter, magmaRadius, isUnderwater, random, config, replaceable);
            }
        }
    }

    private void decorateCraterFloor(
            WorldGenLevel level,
            BlockPos.MutableBlockPos mutable,
            int x,
            int bottomY,
            int z,
            double distFromCenter,
            int magmaRadius,
            boolean isUnderwater,
            RandomSource random,
            MeteoriteConfiguration config,
            Predicate<BlockState> replaceable
    ) {
        mutable.set(x, bottomY - 1, z);
        BlockState floorState = level.getBlockState(mutable);

        if (floorState.isAir() || !floorState.getFluidState().isEmpty() || !floorState.isSolidRender() || !replaceable.test(floorState)) {
            return;
        }

        if (magmaRadius > 0) {
            double normalizedMagmaDist = distFromCenter / magmaRadius;

            if (normalizedMagmaDist <= 1.0) {
                double probability = 1.0 - (normalizedMagmaDist * normalizedMagmaDist);

                if (random.nextFloat() < probability * config.magmaChance()) {
                    this.safeSetBlock(level, mutable, Blocks.MAGMA_BLOCK.defaultBlockState(), replaceable);

                    mutable.set(x, bottomY, z);
                    if (!isUnderwater && level.getBlockState(mutable).isAir() && random.nextFloat() < config.fireChance()) {
                        this.safeSetBlock(level, mutable, Blocks.FIRE.defaultBlockState(), replaceable);
                    }
                }
            }
        }
    }

    private void placeMeteoriteCore(
            WorldGenLevel level,
            int centerX,
            int centerY,
            int centerZ,
            RandomSource random,
            MeteoriteConfiguration config,
            Predicate<BlockState> replaceable
    ) {
        int radius = config.radius();
        int radiusSq = radius * radius;
        int innerRadius = Math.max(0, radius - 1);
        int innerRadiusSq = innerRadius * innerRadius;

        double scaleX = 0.85 + random.nextDouble() * 0.3;
        double scaleY = 0.85 + random.nextDouble() * 0.3;
        double scaleZ = 0.85 + random.nextDouble() * 0.3;

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                int y = centerY + dy;

                if (y < level.getMinY() || y >= level.getMaxY()) {
                    continue;
                }

                for (int dz = -radius; dz <= radius; dz++) {
                    double weightedDistSq = (dx * dx) * scaleX + (dy * dy) * scaleY + (dz * dz) * scaleZ;

                    if (weightedDistSq > radiusSq) {
                        continue;
                    }

                    if (random.nextFloat() < config.coreRoughness() && weightedDistSq > innerRadiusSq) {
                        continue;
                    }

                    mutable.set(centerX + dx, y, centerZ + dz);
                    this.safeSetBlock(level, mutable, config.blockStateProvider().getState(level, random, mutable), replaceable);
                }
            }
        }
    }
}
