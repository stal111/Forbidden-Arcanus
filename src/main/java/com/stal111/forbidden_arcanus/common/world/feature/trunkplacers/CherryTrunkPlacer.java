package com.stal111.forbidden_arcanus.common.world.feature.trunkplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.ThinLogBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.world.ModTrunkPlacers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Cherry Trunk Placer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.world.feature.trunkplacers.CherryTrunkPlacer
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2022-03-26
 */
public class CherryTrunkPlacer extends TrunkPlacer {
    public static final Codec<CherryTrunkPlacer> CODEC = RecordCodecBuilder.create(instance -> {
        return trunkPlacerParts(instance).apply(instance, CherryTrunkPlacer::new);
    });

    public CherryTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Nonnull
    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.CHERRY_TRUNK_PLACER;
    }

    @Nonnull
    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull RandomSource random, int freeTreeHeight, @Nonnull BlockPos pos, @Nonnull TreeConfiguration config) {
        List<FoliagePlacer.FoliageAttachment> list = new ArrayList<>();
        List<Direction> branchDirections = new ArrayList<>();
        BlockPos.MutableBlockPos mutable = pos.mutable();

        int height = this.getTreeHeight(random);

        for (int i = 0; i < height; i++) {
            if ((i > 1 && random.nextBoolean()) || (i + 1 == height && branchDirections.isEmpty())) {
                Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

                if (branchDirections.contains(direction)) {
                    direction = direction.getOpposite();
                }

                if (!branchDirections.contains(direction)) {
                    branchDirections.add(direction);

                    Direction finalDirection = direction;

                    this.placeThinLog(level, blockSetter, random, mutable, config, state -> {
                        BooleanProperty property = ThinLogBlock.PROPERTY_BY_DIRECTION.get(finalDirection);

                        if (state.hasProperty(property)) {
                            return state.setValue(property, true);
                        }

                        return state;
                    });

                    list.add(this.placeBranch(level, blockSetter, random, freeTreeHeight, mutable.immutable(), direction, config));
                } else {
                    this.placeThinLog(level, blockSetter, random, mutable, config);
                }
            } else if (i == 0 && height >= 6) {
                placeLog(level, blockSetter, random, mutable, config);
            } else {
                this.placeThinLog(level, blockSetter, random, mutable, config);
            }

            mutable.move(Direction.UP);
        }

        list.add(new FoliagePlacer.FoliageAttachment(mutable, 0, false));

        return list;
    }

    private FoliagePlacer.FoliageAttachment placeBranch(@Nonnull LevelSimulatedReader level, @Nonnull BiConsumer<BlockPos, BlockState> blockSetter, @Nonnull RandomSource random, int freeTreeHeight, @Nonnull BlockPos pos, Direction direction, @Nonnull TreeConfiguration config) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        int length = 1 + random.nextInt(2);

        for (int i = 0; i < length; i++) {
            int finalI = i;

            this.placeThinLog(level, blockSetter, random, mutable.move(direction), config, (state) -> state
                    .setValue(RotatedPillarBlock.AXIS, direction.getAxis())
                    .setValue(ThinLogBlock.PROPERTY_BY_DIRECTION.get(ThinLogBlock.getRotatedDirection(Direction.UP, direction.getAxis())), finalI + 1 == length)
            );
        }

        boolean flag = random.nextBoolean();

        this.placeThinLog(level, blockSetter, random, mutable.move(Direction.UP), config, state -> state.setValue(ThinLogBlock.PROPERTY_BY_DIRECTION.get(direction), flag));

        if (flag) {
            this.placeThinLog(level, blockSetter, random, mutable.move(direction), config, (state) -> state
                    .setValue(RotatedPillarBlock.AXIS, direction.getAxis())
                    .setValue(ThinLogBlock.PROPERTY_BY_DIRECTION.get(ThinLogBlock.getRotatedDirection(Direction.UP, direction.getAxis())), true)
            );
            this.placeThinLog(level, blockSetter, random, mutable.move(Direction.UP), config);
        }

        return new FoliagePlacer.FoliageAttachment(mutable.move(Direction.UP), 0, false);
    }

    protected boolean placeThinLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config) {
        return placeThinLog(level, blockSetter, random, pos, config, Function.identity());
    }

    protected boolean placeThinLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> propertySetter) {
        if (TreeFeature.validTreePos(level, pos)) {

            blockSetter.accept(pos, propertySetter.apply(ModBlocks.THIN_CHERRY_LOG.get().defaultBlockState()));

            return true;
        }
        return false;
    }
}
