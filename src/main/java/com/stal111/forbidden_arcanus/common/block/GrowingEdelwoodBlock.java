package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nonnull;

/**
 * Growing Edelwood Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.GrowingEdelwoodBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-12-23
 */
public class GrowingEdelwoodBlock extends BushBlock implements BonemealableBlock {

    private static final float BONEMEAL_CHANCE = 0.45F;
    private static final int REQUIRED_BRIGHTNESS = 9;

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public GrowingEdelwoodBlock(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (level.getMaxLocalRawBrightness(pos.above()) >= REQUIRED_BRIGHTNESS && random.nextInt(7) == 0) {
            if (!level.isAreaLoaded(pos, 1)) {
                return;
            }
            this.growEdelwood(level, pos, state, random);
        }
    }

    public void growEdelwood(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(ModConfiguredFeatures.EDELWOOD).orElse(null);
        SaplingGrowTreeEvent event = ForgeEventFactory.blockGrowFeature(level, random, pos, holder);

        if (event.getResult().equals(Event.Result.DENY) || event.getFeature() == null) {
            return;
        }

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);

        if (!event.getFeature().get().place(level, level.getChunkSource().getGenerator(), random, pos)) {
            level.setBlock(pos, state, 4);
        }
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull LevelReader level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, @Nonnull RandomSource random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return level.getRandom().nextFloat() < BONEMEAL_CHANCE;
    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull RandomSource random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        this.growEdelwood(level, pos, state, random);
    }
}
