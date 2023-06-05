package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 2023-05-29
 */
public class UpwindBlock extends Block {

    private static final Predicate<BlockState> IS_VALID_SUPPORT = state -> state.is(ModBlocks.WHIRLWIND.get()) || state.is(ModBlocks.UPWIND.get());
    public static final int UPWIND_HEIGHT = 10;

    public UpwindBlock(Properties properties) {
        super(properties);
    }

    public static void createUpwindAbove(LevelAccessor level, BlockPos pos) {
        for (int i = 0; i < UPWIND_HEIGHT; i++) {
            level.setBlock(pos.above(i + 1), ModBlocks.UPWIND.get().defaultBlockState(), 2);
        }
    }

    private static boolean canExistIn(BlockState state) {
        return state.is(ModBlocks.UPWIND.get()) || state.isAir();
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public @NotNull BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (direction == Direction.DOWN && !state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        return IS_VALID_SUPPORT.test(level.getBlockState(pos.below()));
    }

    @Override
    public void entityInside(@NotNull BlockState state, Level level, BlockPos pos, @NotNull Entity entity) {
        BlockState aboveState = level.getBlockState(pos.above());

        if (aboveState.isAir()) {
            entity.onAboveBubbleCol(false);

            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 70));
            }
        } else {
            entity.onInsideBubbleColumn(false);
        }
    }
    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, RandomSource random) {
        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();

        level.addAlwaysVisibleParticle(ParticleTypes.EFFECT, d0 + 0.5D, d1, d2 + 0.5D, 0.0D, 0.1D, 0.0D);
        level.addAlwaysVisibleParticle(ParticleTypes.EFFECT, d0 + (double) random.nextFloat(), d1 + (double) random.nextFloat(), d2 + (double) random.nextFloat(), 0.0D, 0.1D, 0.0D);

        if (random.nextInt(200) == 0) {
            level.playLocalSound(d0, d1, d2, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }
    }
}
