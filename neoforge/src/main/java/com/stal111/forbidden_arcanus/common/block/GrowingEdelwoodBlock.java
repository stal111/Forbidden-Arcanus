package com.stal111.forbidden_arcanus.common.block;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.common.world.feature.BuiltInFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;

/**
 * Growing Edelwood Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.GrowingEdelwoodBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-23
 */
public class GrowingEdelwoodBlock extends VegetationBlock implements BonemealableBlock {

    public static final MapCodec<GrowingEdelwoodBlock> CODEC = simpleCodec(GrowingEdelwoodBlock::new);

    private static final float BONEMEAL_CHANCE = 0.45F;
    private static final int MAX_BRIGHTNESS = 9;

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public GrowingEdelwoodBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getMaxLocalRawBrightness(pos.above()) <= MAX_BRIGHTNESS && random.nextInt(7) == 0) {
            if (!level.isAreaLoaded(pos, 1)) {
                return;
            }
            this.growEdelwood(level, pos, state, random);
        }
    }

    public void growEdelwood(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).get(BuiltInFeatures.EDELWOOD).orElse(null);
        BlockGrowFeatureEvent event = EventHooks.fireBlockGrowFeature(level, random, pos, holder);

        if (event.isCanceled() || event.getFeature() == null) {
            return;
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);

        //TODO: use tree grower if possible

        if (!event.getFeature().value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
            level.setBlock(pos, state, 4);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return level.getRandom().nextFloat() < BONEMEAL_CHANCE;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        this.growEdelwood(level, pos, state, random);
    }
}
