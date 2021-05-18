package com.stal111.forbidden_arcanus.world.feature;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.world.feature.config.BigFungyssFeatureConfig;
import net.minecraft.block.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * Mega Fungyss Feature
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.world.feature.MegaFungyssFeature
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-04-13
 */
public class MegaFungyssFeature extends Feature<BigFungyssFeatureConfig> {

    public MegaFungyssFeature(Codec<BigFungyssFeatureConfig> codec) {
        super(codec);
    }

    private int getRandomHeight(Random random) {
        return random.nextInt(4) + 11;
    }

    private boolean canGenerate(IWorld world, ChunkGenerator generator, BlockPos pos, int height, BlockPos.Mutable mutable) {
        if (pos.getY() < 1 || pos.getY() + height + 1 >= generator.getMaxBuildHeight()) {
            return false;
        }
        Block block = world.getBlockState(pos.down()).getBlock();

        if (!Tags.Blocks.STONE.contains(block)) {
            return false;
        }

        for (int i = 0; i <= height; i++) {
            BlockState state = world.getBlockState(mutable.setAndOffset(pos, 0, i, 0));

            if (!state.isAir(world, pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean generate(@Nonnull ISeedReader reader, @Nonnull ChunkGenerator generator, @Nonnull Random rand, @Nonnull BlockPos pos, BigFungyssFeatureConfig config) {
        int height = this.getRandomHeight(rand);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        if (!this.canGenerate(reader, generator, pos, height, mutable)) {
            return false;
        }

        this.placeStem(reader, rand, pos, height, mutable, config);
        this.placeCap(reader, rand, pos, height, mutable, config);
        return true;
    }

    private void placeStem(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, BigFungyssFeatureConfig config) {
        for (int i = 0; i < height; i++) {
            if (world.getBlockState(mutable).canBeReplacedByLogs(world, mutable)) {
                this.placeStemBlock(world, config.stemProvider.getBlockState(random, pos), mutable, pos, 0, i, 0);
                this.placeStemBlock(world, config.stemProvider.getBlockState(random, pos), mutable, pos, 1, i, 0);
                this.placeStemBlock(world, config.stemProvider.getBlockState(random, pos), mutable, pos, 1, i, 1);
                this.placeStemBlock(world, config.stemProvider.getBlockState(random, pos), mutable, pos, 0, i, 1);
            }
        }
    }

    private void placeStemBlock(IWorld world, BlockState state, BlockPos.Mutable mutable, BlockPos pos, int xOffset, int yOffset, int zOffset) {
        mutable.setPos(pos);
        mutable.move(xOffset, yOffset, zOffset);
        this.setBlockState(world, mutable, state);
    }

    private void placeCap(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, BigFungyssFeatureConfig config) {
        for (int i = height - 3; i <= height; i++) {
            int distanceToStem = 2;

            for (int xOffset = -distanceToStem; xOffset <= distanceToStem + 1; xOffset++) {
                for (int zOffset = -distanceToStem; zOffset <= distanceToStem + 1; zOffset++) {
                    boolean flag1 = i >= height && xOffset != -distanceToStem && xOffset != distanceToStem + 1 && zOffset != -distanceToStem && zOffset != distanceToStem + 1;
                    boolean flag2 = i < height && (xOffset == -distanceToStem || xOffset == distanceToStem + 1 || zOffset == -distanceToStem || zOffset == distanceToStem + 1) && !((xOffset == -distanceToStem || xOffset == distanceToStem + 1) && (zOffset == -distanceToStem || zOffset == distanceToStem + 1));

                    if (flag1 || flag2) {
                        mutable.setAndOffset(pos, xOffset, i, zOffset);

                        boolean moveDown = (xOffset == -1 && zOffset == -1) || (xOffset == -1 && zOffset == 2) || (xOffset == 2 && zOffset == -1) || (xOffset == 2 && zOffset == 2);
                        if (moveDown) {
                            mutable.move(Direction.DOWN);
                        }
                        BlockState state = i == height && !moveDown ? config.capProvider.getBlockState(random, pos) : config.capProvider.getBlockState(random, pos).with(HugeMushroomBlock.WEST, xOffset < 0).with(HugeMushroomBlock.EAST, xOffset > 0).with(HugeMushroomBlock.NORTH, zOffset < 0).with(HugeMushroomBlock.SOUTH, zOffset > 0);
                        this.setBlockState(world, mutable, state);
                    }
                }
            }

            for (int xOffset = 0; xOffset <= 1; xOffset++) {
                for (int zOffset = 0; zOffset <= 1; zOffset++) {
                    for (Direction direction : Direction.values()) {
                        if (direction.getAxis() != Direction.Axis.Y) {
                            mutable.setAndOffset(pos, xOffset, height - 5, zOffset);
                            mutable.move(direction);
                            if (world.getBlockState(mutable).isAir()) {
                                this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos).with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(direction.getOpposite()), false));
                            }
                        }
                    }
                }
            }
        }
        int xOffset = random.nextInt(2);
        int zOffset = random.nextInt(2);

        if (config.variant == 0) {
            placeSmallCap(world, random, pos, height, mutable, xOffset, zOffset, config);
            placeSmallCap(world, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        } else {
            placeSmallFungyss(world, random, pos, height - 1, mutable, xOffset, zOffset, config);
            placeSmallFungyss(world, random, pos, height - 2, mutable, xOffset == 0 ? 1 : 0, zOffset == 0 ? 1 : 0, config);
        }
    }

    private void placeSmallCap(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);

        mutable.setAndOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos).with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(direction.getOpposite()), false));

        for (int i = 0; i <= 1; i++) {
            direction = direction == Direction.SOUTH ? Direction.EAST : Direction.byHorizontalIndex(direction.getHorizontalIndex() - 1);
            mutable.move(direction);

            this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos).with(SixWayBlock.FACING_TO_PROPERTY_MAP.get(direction.getOpposite().rotateY()), false));
        }
    }

    private void placeSmallFungyss(IWorld world, Random random, BlockPos pos, int height, BlockPos.Mutable mutable, int xOffset, int zOffset, BigFungyssFeatureConfig config) {
        Direction direction = getDirectionFromOffset(xOffset, zOffset);
        int stemHeight = world.getRandom().nextInt(2) + 2;

        mutable.setAndOffset(pos, xOffset, height - 8, zOffset);
        mutable.move(direction);

        if (stemHeight == 3) {
            mutable.move(Direction.DOWN);
        }

        this.setBlockState(world, mutable, config.stemProvider.getBlockState(random, pos).with(BlockStateProperties.AXIS, direction.getAxis()));

        mutable.move(direction);
        this.setBlockState(world, mutable, config.hyphaeProvider.getBlockState(random, pos).with(BlockStateProperties.AXIS, direction.getAxis()));

        for (int i = 0; i < stemHeight; i++) {
            mutable.move(Direction.UP);
            this.setBlockState(world, mutable, config.stemProvider.getBlockState(random, pos).with(BlockStateProperties.AXIS, Direction.Axis.Y));
        }

        pos = mutable.toImmutable();

        int distanceToStem = 1;
        for (int i = stemHeight; i <= stemHeight + 1; i++) {
            for (int xPos = -distanceToStem; xPos <= distanceToStem; xPos++) {
                for (int zPos = -distanceToStem; zPos <= distanceToStem; zPos++) {
                    if ((i < stemHeight + 1 && !(xPos == 0 && zPos == 0)) || !isCorner(xPos, zPos, distanceToStem)) {
                        mutable.setAndOffset(pos, xPos, i - stemHeight, zPos);
                        this.setBlockState(world, mutable, config.capProvider.getBlockState(random, pos));
                    }
                }
            }
        }
        this.setBlockState(world, pos.up(), config.capProvider.getBlockState(random, pos));
    }

    private Direction getDirectionFromOffset(int xOffset, int zOffset) {
        if (xOffset == 0 && zOffset == 0) {
            return Direction.NORTH;
        } else if (xOffset == 0 && zOffset == 1) {
            return Direction.WEST;
        } else if (xOffset == 1 && zOffset == 0) {
            return Direction.EAST;
        }
        return Direction.SOUTH;
    }

    private boolean isCorner(int xOffset, int zOffset, int distanceToStem) {
        return (xOffset == -distanceToStem || xOffset == distanceToStem) == (zOffset == -distanceToStem || zOffset == distanceToStem);
    }
}